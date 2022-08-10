package xyz.starestarrysky.library.util.excel.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import java.io.Serializable

class Data : Serializable {
    companion object {
        private const val serialVersionUID = -8406135344884204397L
    }

    @JacksonXmlText
    var value: String? = null

    @JacksonXmlProperty(localName = "Type", isAttribute = true)
    var type: String? = null
}
