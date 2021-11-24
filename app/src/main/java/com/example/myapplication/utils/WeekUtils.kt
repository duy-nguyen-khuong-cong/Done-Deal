package com.example.myapplication.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class WeekUtils {
    companion object{   //Make stuff static
        lateinit var selectedDate: LocalDate

        public fun daysInWeekArray(date: LocalDate?): MutableList<Int> {

            val days = mutableListOf<Int>()
            var current: LocalDate? = mondayForDate(selectedDate)
            var endDate: LocalDate? = current?.plusWeeks(1)

            while(current?.isBefore(endDate) == true){
                days.add(current.dayOfMonth);
                current = current.plusDays(1)
            }

            return days
        }

        private fun mondayForDate(current: LocalDate): LocalDate? {
            var current: LocalDate = current
            var oneWeekAgo: LocalDate = current.minusWeeks(1)
            while(current.isAfter(oneWeekAgo)){
                if(current.dayOfWeek == DayOfWeek.MONDAY)
                    return current;
                current = current.minusDays(1)
            }
            return null;
        }
        public fun weekMonthFromDate(date: LocalDate): String{
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM - ww").withLocale(
                Locale.getDefault())
            return date.format(dateFormat)
        }
        public fun monthYearFromDate(date: LocalDate): String{
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
            return date.format(dateFormat)
        }

        public fun formattedDate(date: LocalDate): String {
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            return date.format(dateFormat)
        }
        public fun formattedTime(time: LocalTime): String {
            val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
            return time.format(dateFormat)
        }
    }
}