//! 零测地线积分。
#![warn(missing_docs)]

mod orbit;
mod ray;
mod trace;

pub use orbit::OrbitState;
pub use ray::RayHit;
pub use trace::{trace_schwarzschild, TraceConfig};
