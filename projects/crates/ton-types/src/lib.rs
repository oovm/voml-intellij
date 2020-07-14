//! 黑洞模拟共享类型（栈底，无物理算法）。
#![warn(missing_docs)]

mod black_hole;
mod camera;
mod color;
mod error;
mod units;
mod vec3;

pub use black_hole::BlackHole;
pub use camera::Camera;
pub use color::Rgb;
pub use error::{TonError, TonResult};
pub use units::{geom_to_solar_radii, solar_mass_to_geom};
pub use vec3::Vec3;
