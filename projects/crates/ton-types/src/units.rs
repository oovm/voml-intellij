/// 太阳质量（几何单位，G = c = 1）。
pub const SOLAR_MASS: f64 = 1.0;

/// 将太阳质量数转换为几何单位质量。
pub fn solar_mass_to_geom(solar_masses: f64) -> f64 {
    solar_masses * SOLAR_MASS
}

/// 几何单位长度换算为太阳半径倍数（M 为几何质量）。
pub fn geom_to_solar_radii(r: f64, mass: f64) -> f64 {
    // 史瓦西半径 r_s = 2M；太阳半径 ≈ 695700 km，此处仅作相对尺度展示。
    let rs = 2.0 * mass;
    r / rs
}
