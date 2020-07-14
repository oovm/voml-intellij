//! CPU 光线追踪渲染。
#![warn(missing_docs)]

mod framebuffer;
mod renderer;
mod sky;

pub use framebuffer::Framebuffer;
pub use renderer::{default_camera, RenderConfig, Renderer};
pub use sky::sky_color;
