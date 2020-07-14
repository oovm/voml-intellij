use ton_disk::{temperature_to_rgb, AccretionDisk};
use ton_geodesic::{trace_schwarzschild, RayHit, TraceConfig};
use ton_types::{BlackHole, Camera, Rgb, Vec3};

use crate::framebuffer::Framebuffer;
use crate::sky::sky_color;

#[derive(Debug, Clone)]
pub struct RenderConfig {
    pub width: u32,
    pub height: u32,
    pub black_hole: BlackHole,
    pub camera: Camera,
    pub disk: Option<AccretionDisk>,
    pub trace: TraceConfig,
}

pub struct Renderer {
    config: RenderConfig,
}

impl Renderer {
    pub fn new(config: RenderConfig) -> Self {
        Self { config }
    }

    pub fn render(&self) -> Framebuffer {
        let w = self.config.width;
        let h = self.config.height;
        let mut fb = Framebuffer::new(w, h);
        let cam = self.config.camera;
        let bh = self.config.black_hole;
        let trace_cfg = TraceConfig {
            disk_radius: self.config.disk.map(|d| d.config.outer_radius),
            ..self.config.trace
        };
        for y in 0..h {
            for x in 0..w {
                let u = (x as f64 / w as f64) * 2.0 - 1.0;
                let v = 1.0 - (y as f64 / h as f64) * 2.0;
                let aspect = w as f64 / h as f64;
                let dir = cam.ray_direction(u * aspect, v);
                let color = match trace_schwarzschild(bh, cam.position, dir, trace_cfg) {
                    Ok(RayHit::Captured { .. }) => Rgb::BLACK,
                    Ok(RayHit::Escaped { direction }) => sky_color(direction),
                    Ok(RayHit::Disk { radius, .. }) => self
                        .config
                        .disk
                        .map(|disk| temperature_to_rgb(disk.effective_temperature(radius)))
                        .unwrap_or(Rgb::new(1.0, 0.6, 0.2)),
                    Err(_) => sky_color(dir),
                };
                fb.set(x, y, color);
            }
        }
        fb
    }
}

pub fn default_camera(bh: BlackHole) -> Camera {
    let m = bh.mass;
    let dist = 25.0 * m;
    Camera::look_at(
        Vec3::new(dist, 0.0, 5.0 * m),
        Vec3::ZERO,
        Vec3::new(0.0, 0.0, 1.0),
        std::f64::consts::FRAC_PI_4,
    )
}
