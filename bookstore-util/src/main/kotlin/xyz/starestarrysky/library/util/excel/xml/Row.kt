package xyz.starestarrysky.library.util.excel.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.io.Serializable

class Row : Serializable {
    companion object {
        private const val serialVersionUID = -632867751810226744L
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Cell")
    var cells: List<Cell?>? = null

    @JacksonXmlProperty(localName = "AutoFitHeight", isAttribute = true)
    var autoFitHeight: String? = null

    @JacksonXmlProperty(localName = "Height", isAttribute = true)
    var height: String? = null

    @JacksonXmlProperty(localName = "StyleID", isAttribute = true)
    var styleID: String? = null

    @JacksonXmlProperty(localName = "Index", isAttribute = true)
    var index: String? = null
}
