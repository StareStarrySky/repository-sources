package xyz.starestarrysky.library.base.notice

class BizMessage(var code: String, var message: String) {
    companion object {
        const val DEFAULT_SUCCESS_CODE = "10000"
        const val DEFAULT_SUCCESS_MESSAGE = "成功"

        val SUCCESS = BizMessage(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE)
    }
}
