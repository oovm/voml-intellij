use crate::Vec3;

/// 针孔相机（右手系，看向 -Z 或自定义）。
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Camera {
    pub position: Vec3,
    pub forward: Vec3,
    pub up: Vec3,
    pub right: Vec3,
    /// 垂直视场（弧度）。
    pub fov_y: f64,
}

impl Camera {
    /// 由位置、目标点与上方向构造相机。
    pub fn look_at(position: Vec3, target: Vec3, up: Vec3, fov_y: f64) -> Self {
        let forward = target.sub(position).normalize();
        let right = forward.cross(up).normalize();
        let up = right.cross(forward).normalize();
        Self {
            position,
            forward,
            up,
            right,
            fov_y,
        }
    }

    /// 归一化设备坐标 (-1..1) 对应的世界空间射线方向。
    pub fn ray_direction(self, u: f64, v: f64) -> Vec3 {
        let aspect_scale = (self.fov_y * 0.5).tan();
        let dir = self.forward
            .add(self.right.scale(u * aspect_scale))
            .add(self.up.scale(v * aspect_scale));
        dir.normalize()
    }
}
