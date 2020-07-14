use ton_disk::{AccretionDisk, DiskConfig};
use ton_geodesic::TraceConfig;
use ton_render::{default_camera, Framebuffer, RenderConfig, Renderer};
use ton_types::{BlackHole, Camera};

#[derive(Debug, Clone)]
pub struct Scene {
    pub black_hole: BlackHole,
    pub disk: Option<AccretionDisk>,
    pub camera: Camera,
    pub width: u32,
    pub height: u32,
    pub trace: TraceConfig,
}

#[derive(Debug, Clone)]
pub struct SceneBuilder {
    black_hole: BlackHole,
    disk: Option<AccretionDisk>,
    camera: Option<Camera>,
    width: u32,
    height: u32,
    trace: TraceConfig,
}

impl SceneBuilder {
    pub fn new(black_hole: BlackHole) -> Self {
        Self {
            black_hole,
            disk: None,
            camera: None,
            width: 640,
            height: 360,
            trace: TraceConfig::default(),
        }
    }

    pub fn with_default_disk(mut self) -> Self {
        self.disk = Some(AccretionDisk::new(
            self.black_hole,
            DiskConfig::schwarzschild_default(self.black_hole),
        ));
        self
    }

    pub fn with_camera(mut self, camera: Camera) -> Self {
        self.camera = Some(camera);
        self
    }

    pub fn with_resolution(mut self, width: u32, height: u32) -> Self {
        self.width = width;
        self.height = height;
        self
    }

    pub fn build(self) -> Scene {
        Scene {
            black_hole: self.black_hole,
            disk: self.disk,
            camera: self.camera.unwrap_or_else(|| default_camera(self.black_hole)),
            width: self.width,
            height: self.height,
            trace: self.trace,
        }
    }
}

impl Scene {
    pub fn builder(black_hole: BlackHole) -> SceneBuilder {
        SceneBuilder::new(black_hole)
    }

    pub fn render_frame(&self) -> Framebuffer {
        Renderer::new(RenderConfig {
            width: self.width,
            height: self.height,
            black_hole: self.black_hole,
            camera: self.camera,
            disk: self.disk,
            trace: self.trace,
        })
        .render()
    }
}
