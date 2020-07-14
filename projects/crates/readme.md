# crates

黑洞模拟 Rust 算法栈（`ton-*`，命名取自超大质量黑洞 TON 618）。

| crate                                    | 说明                           |
|------------------------------------------|--------------------------------|
| [`ton-types`](ton-types/readme.md)       | 几何单位、向量、相机、黑洞参数 |
| [`ton-metric`](ton-metric/readme.md)     | 史瓦西 / 克尔度规              |
| [`ton-geodesic`](ton-geodesic/readme.md) | 零测地线积分与光线命中         |
| [`ton-disk`](ton-disk/readme.md)         | 吸积盘温度与黑体色             |
| [`ton-render`](ton-render/readme.md)     | CPU 光线追踪帧缓冲             |
| [`ton-sim`](ton-simlate/readme.md)       | 场景编排                       |
| [`ton-view`](ton-view/readme.md)         | CLI 可执行（`ton-view`）       |

```bash
cargo check --workspace
cargo run -p ton-view -- blackhole.png --width 800 --height 450
cargo run -p ton-example-render-schwarzschild
```
