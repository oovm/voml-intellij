use ton_types::Vec3;

#[derive(Debug, Clone, Copy, PartialEq)]
pub enum RayHit {
    Captured { position: Vec3 },
    Escaped { direction: Vec3 },
    Disk { position: Vec3, radius: f64 },
}
