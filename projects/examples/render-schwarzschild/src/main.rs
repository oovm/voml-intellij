use image::Rgba;
use ton_sim::Scene;
use ton_types::BlackHole;

fn main() {
    let bh = BlackHole::schwarzschild(1.0);
    let scene = Scene::builder(bh)
        .with_default_disk()
        .with_resolution(320, 180)
        .build();
    let fb = scene.render_frame();
    let img: image::ImageBuffer<Rgba<u8>, Vec<u8>> =
        image::ImageBuffer::from_raw(320, 180, fb.to_rgba8()).expect("buffer");
    img.save("schwarzschild.png").expect("write png");
    println!("wrote schwarzschild.png");
}
