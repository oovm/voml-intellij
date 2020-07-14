//! 吸积盘辐射模型。
#![warn(missing_docs)]

mod disk;
mod spectrum;

pub use disk::{AccretionDisk, DiskConfig};
pub use spectrum::temperature_to_rgb;
