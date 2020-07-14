use ton_types::BlackHole;

use crate::metric::Metric;

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Schwarzschild {
    pub bh: BlackHole,
}

impl Schwarzschild {
    pub fn new(bh: BlackHole) -> Self {
        Self { bh }
    }

    fn f(&self, r: f64) -> f64 {
        1.0 - 2.0 * self.bh.mass / r
    }
}

impl Metric for Schwarzschild {
    fn black_hole(&self) -> BlackHole {
        self.bh
    }

    fn line_element(&self, r: f64, theta: f64) -> [f64; 4] {
        let f = self.f(r);
        let sin_t = theta.sin();
        [-f, 1.0 / f, r * r, r * r * sin_t * sin_t]
    }
}
