use serde::{Deserialize, Serialize};

/// 黑洞参数（几何单位，G = c = 1）。
#[derive(Debug, Clone, Copy, PartialEq, Serialize, Deserialize)]
pub struct BlackHole {
    /// 质量 M。
    pub mass: f64,
    /// 克尔自旋参数 a = J / (Mc)，|a| < M。
    pub spin: f64,
}

impl BlackHole {
    /// 史瓦西黑洞（无自旋）。
    pub fn schwarzschild(mass: f64) -> Self {
        Self { mass, spin: 0.0 }
    }

    /// TON 618 量级参考（约 6.6×10¹⁰ 太阳质量，取对数刻度演示用）。
    pub fn ton618_scale() -> Self {
        Self::schwarzschild(6.6e10)
    }

    /// 事件视界半径（史瓦西：2M；克尔赤道近似取 r₊）。
    pub fn event_horizon_radius(self) -> f64 {
        if self.spin.abs() < f64::EPSILON {
            2.0 * self.mass
        } else {
            let m = self.mass;
            let a = self.spin.clamp(-m, m);
            m + (m * m - a * a).sqrt()
        }
    }

    /// 光子球半径（史瓦西：3M）。
    pub fn photon_sphere_radius(self) -> f64 {
        if self.spin.abs() < f64::EPSILON {
            3.0 * self.mass
        } else {
            // 赤道平面近似。
            3.0 * self.mass
        }
    }
}
