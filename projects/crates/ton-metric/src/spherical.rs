use ton_types::Vec3;

/// 球坐标 (r, θ, φ)，θ 为极角、φ 为方位角。
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct SphericalCoords {
    pub r: f64,
    pub theta: f64,
    pub phi: f64,
}

impl SphericalCoords {
    pub fn from_cartesian(p: Vec3) -> Self {
        let r = p.length();
        if r <= f64::EPSILON {
            return Self { r: 0.0, theta: 0.0, phi: 0.0 };
        }
        let theta = (p.z / r).acos();
        let phi = p.y.atan2(p.x);
        Self { r, theta, phi }
    }

    pub fn to_cartesian(self) -> Vec3 {
        let sin_t = self.theta.sin();
        Vec3::new(
            self.r * sin_t * self.phi.cos(),
            self.r * sin_t * self.phi.sin(),
            self.r * self.theta.cos(),
        )
    }
}
