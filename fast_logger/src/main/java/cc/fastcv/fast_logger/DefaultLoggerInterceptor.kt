package cc.fastcv.fast_logger

internal class DefaultLoggerInterceptor : AbsLogInterceptChain() {

    override fun intercept(priority: Int, tag: String, logMsg: String?, throwable: Throwable?) {
        next?.intercept(priority, tag, logMsg, throwable)
    }
}