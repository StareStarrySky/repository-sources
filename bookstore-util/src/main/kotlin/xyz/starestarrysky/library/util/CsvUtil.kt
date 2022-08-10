package xyz.starestarrysky.library.util

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import java.io.IOException

class CsvUtil {
    companion object {
        private val CSV_MAPPER = CsvMapper()

        fun getMapperInstance(): CsvMapper {
            return CSV_MAPPER
        }

        fun <T> csv2List(src: ByteArray, t: Class<T>, withHeader: Boolean): List<T> {
            return try {
                val objectReader = CSV_MAPPER.readerFor(t)

                if (withHeader) {
                    objectReader.with(CsvSchema.emptySchema().withHeader())
                }

                val it: MappingIterator<T> = objectReader.readValues(src)

                it.readAll()
            } catch (e: IOException) {
                e.printStackTrace()
                ArrayList(0)
            }
        }
    }
}
