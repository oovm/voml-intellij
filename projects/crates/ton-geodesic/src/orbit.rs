/// 赤道平面轨道状态：u = 1/r，φ 为仿射参数。
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct OrbitState {
    pub u: f64,
    pub du_dphi: f64,
    pub phi: f64,
}

impl OrbitState {
    pub fn new(r: f64, du_dphi: f64, phi: f64) -> Self {
        Self { u: 1.0 / r, du_dphi, phi }
    }

    pub fn radius(self) -> f64 {
        if self.u.abs() < f64::EPSILON {
            f64::INFINITY
        } else {
            1.0 / self.u
        }
    }
}

pub fn schwarzschild_orbit_rhs(mass: f64, u: f64) -> f64 {
    3.0 * mass * u * u - u
}

pub fn rk4_step(mass: f64, state: OrbitState, dphi: f64) -> OrbitState {
    let phi0 = state.phi;
    let mut u = state.u;
    let mut du = state.du_dphi;
    let accel = |uu: f64| schwarzschild_orbit_rhs(mass, uu);
    let k1_du = accel(u);
    let k1_u = du;
    let u2 = u + 0.5 * dphi * k1_u;
    let du2 = du + 0.5 * dphi * k1_du;
    let k2_du = accel(u2);
    let k2_u = du2;
    let u3 = u + 0.5 * dphi * k2_u;
    let du3 = du + 0.5 * dphi * k2_du;
    let k3_du = accel(u3);
    let k3_u = du3;
    let u4 = u + dphi * k3_u;
    let du4 = du + dphi * k3_du;
    let k4_du = accel(u4);
    let k4_u = du4;
    u += dphi / 6.0 * (k1_u + 2.0 * k2_u + 2.0 * k3_u + k4_u);
    du += dphi / 6.0 * (k1_du + 2.0 * k2_du + 2.0 * k3_du + k4_du);
    OrbitState { u, du_dphi: du, phi: phi0 + dphi }
}
