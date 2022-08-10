package xyz.starestarrysky.library.base.notice

class BizException : RuntimeException {
    companion object {
        private const val serialVersionUID = -5926269769235712823L

        const val DEFAULT_ERROR_HTTP_STATUS = 500
        const val DEFAULT_ERROR_CODE = "9999"
        const val DEFAULT_ERROR_MESSAGE = "未知错误"

        val UNKNOW_ERROR = BizException()

        fun builder(): BusExceptionBuilder {
            return BusExceptionBuilder()
        }

        class BusExceptionBuilder {
            private var httpStatus = DEFAULT_ERROR_HTTP_STATUS
            private var code = DEFAULT_ERROR_CODE
            private var message: String? = DEFAULT_ERROR_MESSAGE

            fun message(message: String?): BusExceptionBuilder {
                this.message = message
                return this
            }

            fun code(code: String): BusExceptionBuilder {
                this.code = code
                return this
            }

            fun httpStatus(status: Int): BusExceptionBuilder {
                this.httpStatus = status
                return this
            }

            fun build(): BizException {
                return BizException(message, code, httpStatus)
            }
        }
    }

    var httpStatus = DEFAULT_ERROR_HTTP_STATUS
    var code = DEFAULT_ERROR_CODE
    override var message = DEFAULT_ERROR_MESSAGE

    constructor() : super(DEFAULT_ERROR_MESSAGE)

    constructor(message: String?) : super(message)

    constructor(throwable: Throwable) : super(throwable)

    constructor(message: String?, throwable: Throwable) : super(message, throwable)

    constructor(message: String?, code: String) : super(message) {
        this.code = code
    }

    constructor(message: String?, code: String, throwable: Throwable) : super(message, throwable) {
        this.code = code
    }

    constructor(message: String?, code: String, httpStatus: Int) : super(message) {
        this.code = code
        this.httpStatus = httpStatus
    }
}
