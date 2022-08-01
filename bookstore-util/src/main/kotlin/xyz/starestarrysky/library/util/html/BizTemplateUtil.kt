package xyz.starestarrysky.library.util.html

import org.apache.commons.lang3.StringUtils
import org.springframework.core.annotation.Order
import xyz.starestarrysky.library.util.html.annotation.BizTemplate
import xyz.starestarrysky.library.util.html.mode.BaseBizTemplate
import java.lang.reflect.InvocationTargetException
import java.util.*

class BizTemplateUtil {
    companion object {
        fun <T : BaseBizTemplate> getExt(source: T): String {
            val bizTemplate = getBizTemplate(source)
            return if (StringUtils.isBlank(bizTemplate.contentType))
                bizTemplate.value.substring(bizTemplate.value.indexOf('.'))
            else
                bizTemplate.contentType
        }

        fun <T : BaseBizTemplate> getFileName(source: T): String {
            return getBizTemplate(source).title
        }

        fun <T : BaseBizTemplate> getTemplatePath(source: T): String {
            return getBizTemplate(source).path
        }

        fun <T : BaseBizTemplate> getTemplateName(source: T): String {
            return getBizTemplate(source).value
        }

        fun <T : BaseBizTemplate> getBizTemplate(source: T): BizTemplate {
            val sourceAnnotation = source.javaClass.getAnnotation(BizTemplate::class.java)
            if (sourceAnnotation == null || StringUtils.isBlank(sourceAnnotation.value)) {
                throw NullPointerException("未指定模板文件")
            }
            return sourceAnnotation
        }

        fun <T : BaseBizTemplate, U : BizTemplateAbstract<*>> template(uClazz: Class<U>, source: T): ByteArray? {
            return template(uClazz, source, getTemplatePath(source), getTemplateName(source))
        }

        fun <T : BaseBizTemplate, U : BizTemplateAbstract<*>> template(
            uClazz: Class<U>,
            source: T,
            templateName: String
        ): ByteArray? {
            return template(uClazz, source, null, templateName)
        }

        fun <T : BaseBizTemplate, U : BizTemplateAbstract<*>> template(
            uClazz: Class<U>,
            source: T,
            templatePath: String?,
            templateName: String
        ): ByteArray? {
            val declaredMethods = uClazz.declaredMethods
            Arrays.sort(uClazz.declaredMethods, Comparator { o1, o2 ->
                val o1Annotation = o1.getAnnotation(Order::class.java)
                val o2Annotation = o2.getAnnotation(Order::class.java)
                if (o1Annotation != null && o2Annotation != null) {
                    val ord1: Int = o1Annotation.value
                    val ord2: Int = o2Annotation.value
                    return@Comparator ord1.compareTo(ord2)
                }
                0
            })

            val method0Res = try {
                declaredMethods[1].invoke(uClazz.getDeclaredConstructor().newInstance(), templatePath, templateName)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(uClazz.canonicalName + "@" + declaredMethods[0].name)
            } catch (e: InstantiationException) {
                throw RuntimeException(uClazz.canonicalName + "@" + declaredMethods[0].name)
            } catch (e: InvocationTargetException) {
                throw RuntimeException(uClazz.canonicalName + "@" + declaredMethods[0].name)
            }

            return if (method0Res == null) {
                null
            } else try {
                declaredMethods[3].invoke(uClazz.getDeclaredConstructor().newInstance(), source, method0Res) as ByteArray
            } catch (e: IllegalAccessException) {
                throw RuntimeException(uClazz.canonicalName + "@" + declaredMethods[1].name)
            } catch (e: InstantiationException) {
                throw RuntimeException(uClazz.canonicalName + "@" + declaredMethods[1].name)
            } catch (e: InvocationTargetException) {
                throw RuntimeException(uClazz.canonicalName + "@" + declaredMethods[1].name)
            }

        }
    }
}
