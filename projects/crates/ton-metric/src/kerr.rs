use ton_types::BlackHole;

use crate::metric::Metric;

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Kerr {
    pub bh: BlackHole,
}

impl Kerr {
    pub fn new(bh: BlackHole) -> Self {
        Self { bh }
    }

    fn sigma(&self, r: f64) -> f64 {
        let a = self.bh.spin;
        r * r + a * a
    }

    fn delta(&self, r: f64) -> f64 {
        let m = self.bh.mass;
        let a = self.bh.spin;
        r * r - 2.0 * m * r + a * a
    }
}

impl Metric for Kerr {
    fn black_hole(&self) -> BlackHole {
        self.bh
    }

    fn line_element(&self, r: f64, theta: f64) -> [f64; 4] {
        let _ = theta;
        let m = self.bh.mass;
        let a = self.bh.spin;
        let sigma = self.sigma(r);
        let delta = self.delta(r);
        let omega = 2.0 * m * a * r / sigma;
        let g_tt = -(1.0 - 2.0 * m * r / sigma);
        let g_rr = sigma / delta;
        let g_theta_theta = sigma;
        let g_phi_phi = (r * r + a * a + 2.0 * m * a * a * r / sigma) * (1.0 - omega * omega * g_tt.abs());
        [g_tt, g_rr, g_theta_theta, g_phi_phi]
    }
}
