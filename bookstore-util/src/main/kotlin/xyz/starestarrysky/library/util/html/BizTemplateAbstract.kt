package xyz.starestarrysky.library.util.html

import org.springframework.core.annotation.Order
import xyz.starestarrysky.library.util.html.mode.BaseBizTemplate

abstract class BizTemplateAbstract<P> {
    @Order(0)
    abstract fun prepare(templatePath: String, templateName: String): P
    @Order(1)
    abstract fun <T : BaseBizTemplate> template(source: T, name: P): ByteArray
}
