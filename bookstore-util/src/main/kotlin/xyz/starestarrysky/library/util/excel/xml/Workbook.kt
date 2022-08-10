package xyz.starestarrysky.library.util.excel.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.io.Serializable

@JacksonXmlRootElement(localName = "Workbook")
class Workbook : Serializable {
    companion object {
        private const val serialVersionUID = -4436100566209811887L
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Worksheet")
    var worksheets: List<Worksheet?>? = null
}
