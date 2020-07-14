use thiserror::Error;

/// 黑洞模拟统一错误。
#[derive(Debug, Error)]
pub enum TonError {
    /// 数值域非法（如 r ≤ 0）。
    #[error("domain error: {0}")]
    Domain(String),
    /// 光线落入事件视界。
    #[error("ray captured by event horizon")]
    Captured,
    /// 光线逃逸至无穷远。
    #[error("ray escaped")]
    Escaped,
}

/// 便捷结果别名。
pub type TonResult<T> = Result<T, TonError>;
