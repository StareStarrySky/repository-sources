package xyz.starestarrysky.library.util.html.annotation

@MustBeDocumented
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class BizTemplate(
    val value: String = ".",
    val path: String = "",
    val title: String = "",
    val contentType: String = ""
)
