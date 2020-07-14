use ton_metric::SphericalCoords;
use ton_types::{BlackHole, TonError, TonResult, Vec3};

use crate::orbit::{rk4_step, OrbitState};
use crate::ray::RayHit;

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct TraceConfig {
    pub max_steps: u32,
    pub dphi: f64,
    pub disk_radius: Option<f64>,
    pub escape_radius: f64,
}

impl Default for TraceConfig {
    fn default() -> Self {
        Self {
            max_steps: 4096,
            dphi: 0.02,
            disk_radius: None,
            escape_radius: 1.0e4,
        }
    }
}

pub fn trace_schwarzschild(
    bh: BlackHole,
    origin: Vec3,
    direction: Vec3,
    config: TraceConfig,
) -> TonResult<RayHit> {
    let mass = bh.mass;
    let rs = bh.event_horizon_radius();
    let start = SphericalCoords::from_cartesian(origin);
    if start.r <= rs {
        return Err(TonError::Captured);
    }
    let dir = direction.normalize();
    let r0 = start.r;
    let phi0 = start.phi;
    let cart_step = dir.scale(0.01);
    let end_sph = SphericalCoords::from_cartesian(origin.add(cart_step));
    let dr = end_sph.r - r0;
    let dphi_init = end_sph.phi - phi0;
    let du_dphi = if r0.abs() > f64::EPSILON {
        -dr / (r0 * r0) / dphi_init.max(1.0e-6)
    } else {
        0.0
    };
    let mut state = OrbitState::new(r0, du_dphi, phi0);
    for _ in 0..config.max_steps {
        let r = state.radius();
        if r <= rs {
            return Ok(RayHit::Captured {
                position: spherical_to_cartesian(r, state.phi),
            });
        }
        if let Some(r_disk) = config.disk_radius {
            if r <= r_disk && r > rs {
                return Ok(RayHit::Disk {
                    position: spherical_to_cartesian(r, state.phi),
                    radius: r,
                });
            }
        }
        if r >= config.escape_radius {
            return Ok(RayHit::Escaped {
                direction: Vec3::new(state.phi.cos(), state.phi.sin(), 0.0).normalize(),
            });
        }
        state = rk4_step(mass, state, config.dphi);
        if state.u <= 0.0 || !state.u.is_finite() {
            return Err(TonError::Escaped);
        }
    }
    Err(TonError::Domain("max steps exceeded".into()))
}

fn spherical_to_cartesian(r: f64, phi: f64) -> Vec3 {
    SphericalCoords {
        r,
        theta: std::f64::consts::FRAC_PI_2,
        phi,
    }
    .to_cartesian()
}
