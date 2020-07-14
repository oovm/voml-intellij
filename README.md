# Blackhole / TON

超大质量黑洞数值模拟与可视化（几何单位 G = c = 1）。crate 前缀 **ton-** 取自已知最大黑洞 **TON 618**。

## 快速开始

```bash
cargo check --workspace
cargo run -p ton-view -- blackhole.png --width 800 --height 450 --mass 1.0
```

## Crate 分层

```text
ton-types          ← 共享类型（栈底）
ton-metric         ← 史瓦西 / 克尔度规
ton-geodesic       ← 零测地线积分
ton-disk           ← 吸积盘辐射
ton-render         ← CPU 光线追踪
ton-sim            ← 场景编排
ton-view           ← CLI 二进制
```

| crate          | 职责                       |
|----------------|----------------------------|
| `ton-types`    | 质量、自旋、相机、RGB      |
| `ton-metric`   | 线元求值                   |
| `ton-geodesic` | 光线追踪（史瓦西赤道平面） |
| `ton-disk`     | Novikov-Thorne 薄盘近似    |
| `ton-render`   | 逐像素着色 + 星空背景      |
| `ton-sim`      | `Scene` 单帧渲染           |
| `ton-view`     | 命令行输出 PNG             |

## 仓库布局

```text
blackhole/
  projects/
    crates/      # ton-* Rust 算法
    examples/    # 可运行示例
    packages/    # 前端 / CLI（可选，后续接 WebGPU）
```

设计文档与路线图见工作区 `规划设计/` 与 `决策和进度表/`（不进本仓 README 外链）。

## 示例

```bash
cargo run -p ton-example-render-schwarzschild
# → schwarzschild.png
```
