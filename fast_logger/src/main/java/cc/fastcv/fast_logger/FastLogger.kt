package cc.fastcv.fast_logger

import android.content.Context
import android.util.Log

object FastLogger {

    private var initialized = false

    private var intercepts: AbsLogInterceptChain = DefaultLoggerInterceptor()

    var logEnable = true

    var logSave2File = false

    var tagPrefix = ""

    fun initLogger(context: Context) {
        if (initialized) {
            initialized = true
            return
        }

        val fileInterceptor = Logger2FileInterceptor(context)

        val printInterceptor = LoggerPrintInterceptor()
        printInterceptor.next = fileInterceptor

        val decorateInterceptor = LoggerDecorateInterceptor()
        decorateInterceptor.next = printInterceptor

        intercepts.next = decorateInterceptor
    }

    fun d(tag: String, message: String) {
        log(Log.DEBUG, message, tag)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        log(Log.ERROR, message, tag, throwable = throwable)
    }

    fun w(tag: String, message: String) {
        log(Log.WARN, message, tag)
    }

    fun i(tag: String, message: String) {
        log(Log.INFO, message, tag)
    }

    fun setInterceptor(interceptor: AbsLogInterceptChain) {
        intercepts = interceptor
    }

    @Synchronized
    private fun log(
        priority: Int,
        message: String,
        tag: String,
        throwable: Throwable? = null
    ) {
        if (logEnable) {
            intercepts.intercept(priority, tag, message, throwable)
        }
    }
}