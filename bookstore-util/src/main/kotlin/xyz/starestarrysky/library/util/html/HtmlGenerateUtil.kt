package xyz.starestarrysky.library.util.html

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import org.apache.commons.lang3.StringUtils
import org.springframework.core.annotation.Order
import xyz.starestarrysky.library.util.html.mode.BaseBizTemplate
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class HtmlGenerateUtil : BizTemplateAbstract<String>() {
    companion object {
        private val configuration: Configuration = Configuration(Configuration.VERSION_2_3_31)

        init {
            configuration.setClassForTemplateLoading(HtmlGenerateUtil::class.java, "/static/ftl/")
            configuration.defaultEncoding = StandardCharsets.UTF_8.displayName()
            configuration.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        }
    }

    @Order(0)
    override fun prepare(templatePath: String, templateName: String): String {
        return if (StringUtils.isNotBlank(templatePath)) templatePath + templateName else templateName
    }

    @Order(1)
    override fun <T : BaseBizTemplate> template(source: T, name: String): ByteArray {
        return ByteArrayOutputStream().use { baos ->
            OutputStreamWriter(baos).use {
                val template = configuration.getTemplate(name)
                template.process(source, it)
            }
            baos.toByteArray()
        }
    }
}
