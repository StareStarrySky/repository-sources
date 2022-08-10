package xyz.starestarrysky.library.util.excel.xml

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.io.Serializable

class Table : Serializable {
    companion object {
        private const val serialVersionUID = 7050779763014509876L
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Column")
    var columns: List<Column?>? = null

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Row")
    var rows: List<Row?>? = null

    @JacksonXmlProperty(localName = "ExpandedColumnCount", isAttribute = true)
    var expandedColumnCount: String? = null

    @JacksonXmlProperty(localName = "ExpandedRowCount", isAttribute = true)
    var expandedRowCount: String? = null

    @JacksonXmlProperty(localName = "FullColumns", isAttribute = true)
    var fullColumns: String? = null

    @JacksonXmlProperty(localName = "FullRows", isAttribute = true)
    var fullRows: String? = null

    @JacksonXmlProperty(localName = "DefaultColumnWidth", isAttribute = true)
    var defaultColumnWidth: String? = null

    @JacksonXmlProperty(localName = "DefaultRowHeight", isAttribute = true)
    var defaultRowHeight: String? = null
}
