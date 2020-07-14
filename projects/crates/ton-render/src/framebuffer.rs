use ton_types::Rgb;

#[derive(Debug, Clone)]
pub struct Framebuffer {
    pub width: u32,
    pub height: u32,
    pub pixels: Vec<Rgb>,
}

impl Framebuffer {
    pub fn new(width: u32, height: u32) -> Self {
        let len = (width as usize) * (height as usize);
        Self { width, height, pixels: vec![Rgb::BLACK; len] }
    }

    pub fn set(&mut self, x: u32, y: u32, color: Rgb) {
        if x < self.width && y < self.height {
            self.pixels[y as usize * self.width as usize + x as usize] = color;
        }
    }

    pub fn to_rgba8(&self) -> Vec<u8> {
        let mut out = Vec::with_capacity(self.pixels.len() * 4);
        for px in &self.pixels {
            let [r, g, b] = px.to_u8();
            out.extend_from_slice(&[r, g, b, 255]);
        }
        out
    }
}
