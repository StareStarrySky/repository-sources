package xyz.starestarrysky.library.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateUtil {
    companion object {
        const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
        const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
        const val DEFAULT_DATE_FORMAT_CHINESE = "yyyy年MM月dd日"
        const val DEFAULT_DATE_TIME_FORMAT_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒"

        @Throws(ParseException::class)
        fun parse(date: String, format: String): Date? {
            val sdf = SimpleDateFormat(format)
            return sdf.parse(date)
        }

        fun parse(date: Date, format: String): String? {
            val df = SimpleDateFormat(format)
            return df.format(date)
        }

        fun parse(millis: Long): Date? {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            return calendar.time
        }

        @Throws(ParseException::class)
        fun parse(date: String): Date? {
            return parse(date, DEFAULT_DATE_TIME_FORMAT)
        }

        fun parse(date: Date): String? {
            return parse(date, DEFAULT_DATE_TIME_FORMAT)
        }

        fun getNow(format: String): String? {
            return parse(Calendar.getInstance().time, format)
        }

        fun getNowDate(): String? {
            return getNow(DEFAULT_DATE_FORMAT)
        }

        fun getNowTime(): String? {
            return getNow(DEFAULT_DATE_TIME_FORMAT)
        }

        fun getCurYear(): String? {
            return getNow("yyyy")
        }

        fun getCurMonth(): String? {
            return getNow("MM")
        }

        fun getCurDay(): String? {
            return getNow("dd")
        }

        /**
         * 当前年的第一天
         */
        fun getFirstDayOfYear(format: String): String? {
            val df = SimpleDateFormat(format)
            val currCalendar = Calendar.getInstance()
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar[Calendar.YEAR] = currCalendar[Calendar.YEAR]
            return df.format(calendar.time)
        }

        /**
         * 当前月的第一天
         */
        fun getFirstDayOfMonth(format: String): String? {
            val df = SimpleDateFormat(format)
            val calendar = Calendar.getInstance()
            calendar[calendar[Calendar.YEAR], calendar[Calendar.MONTH]] = 1
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            return df.format(calendar.time)
        }

        /**
         * 当前周的第一天
         */
        fun getFirstDayOfWeek(format: String): String? {
            val df = SimpleDateFormat(format)
            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.MONDAY
            calendar[Calendar.DAY_OF_WEEK] = calendar.firstDayOfWeek
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            return df.format(calendar.time)
        }

        /**
         * 两日期相差月份个数
         */
        @Throws(ParseException::class)
        fun getMonthSpace(start: String, end: String): Int {
            val calendar = Calendar.getInstance()
            calendar.time = parse(end, DEFAULT_DATE_FORMAT)
            val year1 = calendar[Calendar.YEAR]
            val month1 = calendar[Calendar.MONTH]
            calendar.time = parse(start, DEFAULT_DATE_FORMAT)
            val year2 = calendar[Calendar.YEAR]
            val month2 = calendar[Calendar.MONTH]
            return if (year1 == year2) month1 - month2 else 12 * (year1 - year2) + month1 - month2
        }

        /**
         * 日期对应星期几
         */
        @Throws(ParseException::class)
        fun nowWeek(pTime: String): Int {
            val calendar = Calendar.getInstance()
            calendar.time = parse(pTime, DEFAULT_DATE_FORMAT)
            return if (calendar[Calendar.DAY_OF_WEEK] == 1) 7 else calendar[Calendar.DAY_OF_WEEK] - 1
        }

        /**
         * 相隔
         */
        fun stringBetween(start: Date, end: Date): String {
            val span = end.time - start.time
            val day = (span / (24 * 60 * 60 * 1000)).toInt()
            val hh = (span % (24 * 60 * 60 * 1000) / (60 * 60 * 1000)).toInt()
            val mm = (span % (60 * 60 * 1000) / (60 * 1000)).toInt()
            val ss = (span % (60 * 1000) / 1000).toInt()
            return (if (day > 0) day.toString() + "天" else "") + (if (hh > 0) hh.toString() + "小时" else "") + (if (mm > 0) mm.toString() + "分" else "") + ss + "秒"
        }

        /**
         * 两个日期之间相差的天数
         */
        @Throws(ParseException::class)
        fun daysBetween(start: String, end: String): Int {
            val calendar = Calendar.getInstance()
            calendar.time = parse(start, DEFAULT_DATE_FORMAT)
            val time1 = calendar.timeInMillis
            calendar.time = parse(end, DEFAULT_DATE_FORMAT)
            val time2 = calendar.timeInMillis
            val betweenDays = (time2 - time1) / (1000 * 3600 * 24)
            return betweenDays.toInt()
        }
    }
}
