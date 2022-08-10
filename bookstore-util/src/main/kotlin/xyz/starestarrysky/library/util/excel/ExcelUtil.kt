package xyz.starestarrysky.library.util.excel

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import xyz.starestarrysky.library.util.excel.xml.Workbook
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException

class ExcelUtil {
    class Xml {
        companion object {
            private val XML_MAPPER = XmlMapper()

            fun getMapperInstance(): XmlMapper {
                return XML_MAPPER
            }

            fun xml2Obj(src: ByteArray): Workbook? {
                return try {
                    val reader = XMLInputFactory.newFactory()
                        .createXMLStreamReader(ByteArrayInputStream(src), StandardCharsets.UTF_8.displayName())
                    XML_MAPPER.readValue(reader, Workbook::class.java)
                } catch (e: XMLStreamException) {
                    e.printStackTrace()
                    null
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}
