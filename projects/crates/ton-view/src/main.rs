//! 黑洞模拟器 CLI：渲染单帧 PNG。
use std::env;
use std::path::PathBuf;

use image::{ImageBuffer, Rgba};
use ton_sim::Scene;
use ton_types::BlackHole;

fn main() {
    let args: Vec<String> = env::args().collect();
    let out = args
        .get(1)
        .map(PathBuf::from)
        .unwrap_or_else(|| PathBuf::from("blackhole.png"));

    let width = parse_u32_arg(&args, "--width", 800);
    let height = parse_u32_arg(&args, "--height", 450);
    let mass = parse_f64_arg(&args, "--mass", 1.0);

    let bh = BlackHole::schwarzschild(mass);
    let scene = Scene::builder(bh)
        .with_default_disk()
        .with_resolution(width, height)
        .build();

    eprintln!(
        "TON view: M={:.3}, r_s={:.3}, rendering {}x{} → {}",
        bh.mass,
        bh.event_horizon_radius(),
        width,
        height,
        out.display()
    );

    let fb = scene.render_frame();
    let rgba = fb.to_rgba8();
    let img: ImageBuffer<Rgba<u8>, Vec<u8>> =
        ImageBuffer::from_raw(width, height, rgba).expect("buffer size mismatch");
    img.save(&out).expect("failed to write PNG");

    eprintln!("done.");
}

fn parse_u32_arg(args: &[String], flag: &str, default: u32) -> u32 {
    args.iter()
        .position(|a| a == flag)
        .and_then(|i| args.get(i + 1))
        .and_then(|s| s.parse().ok())
        .unwrap_or(default)
}

fn parse_f64_arg(args: &[String], flag: &str, default: f64) -> f64 {
    args.iter()
        .position(|a| a == flag)
        .and_then(|i| args.get(i + 1))
        .and_then(|s| s.parse().ok())
        .unwrap_or(default)
}
