package xyz.starestarrysky.library.base.notice

import java.io.Serializable

class BizMessage(var code: String, var message: String) : Serializable {
    companion object {
        private const val serialVersionUID = -5795495733149388224L

        const val DEFAULT_SUCCESS_CODE = "10000"
        const val DEFAULT_SUCCESS_MESSAGE = "成功"

        val SUCCESS = BizMessage(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE)
    }
}
