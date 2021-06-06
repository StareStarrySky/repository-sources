package xyz.starestarrysky.library.annotation

import org.springframework.beans.factory.annotation.Qualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.TYPE,
    AnnotationTarget.ANNOTATION_CLASS
)
@Qualifier
annotation class BeanName(val value: String = "", val type: String = "")
