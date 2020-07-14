use ton_types::Rgb;

pub fn temperature_to_rgb(temp_k: f64) -> Rgb {
    if temp_k <= 0.0 {
        return Rgb::BLACK;
    }
    let t = temp_k * 0.01;
    let (mut r, mut g, mut b) = (0.0, 0.0, 0.0);
    if t <= 66.0 {
        r = 255.0;
        g = 99.4708025861 * t.ln() - 161.1195681661;
        b = if t <= 19.0 {
            0.0
        } else {
            138.5177312231 * (t - 10.0).ln() - 305.0447927307
        };
    } else {
        r = 329.698727446 * (t - 60.0).powf(-0.1332047592);
        g = 288.1221695283 * (t - 60.0).powf(-0.0755148492);
        b = 255.0;
    }
    Rgb::new(r / 255.0, g / 255.0, b / 255.0).clamp01()
}
