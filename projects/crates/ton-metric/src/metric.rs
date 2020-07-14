use ton_types::BlackHole;

/// 静态或稳态时空度规接口。
pub trait Metric {
    /// 黑洞参数。
    fn black_hole(&self) -> BlackHole;

    /// 线元 g_μν 在 (t, r, θ, φ) 处的对角近似分量。
    /// 返回 [g_tt, g_rr, g_θθ, g_φφ]。
    fn line_element(&self, r: f64, theta: f64) -> [f64; 4];

    /// 时间分量 g_tt。
    fn g_tt(&self, r: f64, theta: f64) -> f64 {
        self.line_element(r, theta)[0]
    }

    /// 径向分量 g_rr。
    fn g_rr(&self, r: f64, theta: f64) -> f64 {
        self.line_element(r, theta)[1]
    }
}
