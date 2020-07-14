use ton_types::{Rgb, Vec3};

pub fn sky_color(direction: Vec3) -> Rgb {
    let t = (direction.y * 0.5 + 0.5).clamp(0.0, 1.0);
    let base = Rgb::new(0.02 * t, 0.03 * t, 0.08 + 0.12 * t);
    let h = (direction.x * 127.1 + direction.y * 311.7 + direction.z * 74.7).sin() * 43758.5453;
    if (h - h.floor()) > 0.995 {
        base.add(Rgb::WHITE.scale(0.8))
    } else {
        base
    }
}
