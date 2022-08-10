package xyz.starestarrysky.library.util.excel.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.io.Serializable

class Column : Serializable {
    companion object {
        private const val serialVersionUID = -8985031857562107538L
    }

    @JacksonXmlProperty(localName = "AutoFitWidth", isAttribute = true)
    var autoFitWidth: String? = null

    @JacksonXmlProperty(localName = "Width", isAttribute = true)
    var width: String? = null
}
