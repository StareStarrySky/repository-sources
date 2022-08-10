package xyz.starestarrysky.library.util.excel.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.io.Serializable

class Cell : Serializable {
    companion object {
        private const val serialVersionUID = 205627746613285903L
    }

    @JacksonXmlProperty(localName = "Data")
    var data: Data? = null

    @JacksonXmlProperty(localName = "Index", isAttribute = true)
    var index: String? = null

    @JacksonXmlProperty(localName = "StyleID", isAttribute = true)
    var styleID: String? = null
}
