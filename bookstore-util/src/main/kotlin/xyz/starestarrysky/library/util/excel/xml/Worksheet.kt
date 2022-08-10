package xyz.starestarrysky.library.util.excel.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.io.Serializable

class Worksheet : Serializable {
    companion object {
        private const val serialVersionUID = -4913945130399346882L
    }

    @JacksonXmlProperty(localName = "Table")
    var table: Table? = null

    @JacksonXmlProperty(localName = "Name", isAttribute = true)
    var name: String? = null
}
