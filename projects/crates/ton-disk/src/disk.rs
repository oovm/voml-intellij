use ton_types::BlackHole;

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct DiskConfig {
    pub inner_radius: f64,
    pub outer_radius: f64,
    pub accretion_rate: f64,
}

impl DiskConfig {
    pub fn schwarzschild_default(bh: BlackHole) -> Self {
        let m = bh.mass;
        Self {
            inner_radius: 6.0 * m,
            outer_radius: 30.0 * m,
            accretion_rate: 1.0,
        }
    }
}

#[derive(Debug, Clone, Copy, PartialEq)]
pub struct AccretionDisk {
    pub bh: BlackHole,
    pub config: DiskConfig,
}

impl AccretionDisk {
    pub fn new(bh: BlackHole, config: DiskConfig) -> Self {
        Self { bh, config }
    }

    pub fn effective_temperature(self, r: f64) -> f64 {
        let cfg = self.config;
        if r < cfg.inner_radius || r > cfg.outer_radius {
            return 0.0;
        }
        let m = self.bh.mass;
        let norm = cfg.accretion_rate / (m * m);
        norm * (cfg.inner_radius / r).powf(0.75) * 5000.0
    }
}
