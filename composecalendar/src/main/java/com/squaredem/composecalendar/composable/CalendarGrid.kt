/*
 * Copyright 2022 Matteo Miceli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squaredem.composecalendar.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.squaredem.composecalendar.daterange.DateRange
import com.squaredem.composecalendar.daterange.DateRangeStep
import com.squaredem.composecalendar.daterange.rangeTo
import com.squaredem.composecalendar.model.CalendarMode
import com.squaredem.composecalendar.model.Config
import com.squaredem.composecalendar.model.DateWrapper
import com.squaredem.composecalendar.model.DayOption
import com.squaredem.composecalendar.model.hasSelectionIndicator
import com.squaredem.composecalendar.model.highlightedTypeForDay
import com.squaredem.composecalendar.utils.LogCompositions
import com.squaredem.composecalendar.utils.calculateWeekdayDistance
import java.time.LocalDate

@Composable
internal fun CalendarGrid(
    pagerDate: LocalDate,
    dateRange: DateRange,
    mode: CalendarMode,
    onSelected: (CalendarMode) -> Unit,
    calendarDayOption: ((LocalDate) -> DayOption)?,
    showCurrentMonthOnly: Boolean
) {
    LogCompositions("CalendarGrid")

    val gridSpacing = 4.dp
    val today = LocalDate.now()
    val firstWeekDayOfMonth = pagerDate.dayOfWeek
    val pagerMonth = pagerDate.month
    val gridStartDay = pagerDate.minusDays(
        calculateWeekdayDistance(
            firstWeekday = Config.weekStartDay,
            secondWeekday = firstWeekDayOfMonth,
        ).toLong()
    )
    val gridEndDay = gridStartDay.plusDays(41)

    val dates = (gridStartDay.rangeTo(gridEndDay) step DateRangeStep.Day()).map {
        val isCurrentMonth = it.month == pagerMonth
        val isCurrentDay = it == today
        val isInDateRange = it in dateRange

        DateWrapper(
            localDate = it,
            isSelectedDay = mode.hasSelectionIndicator(it),
            highlightedType = mode.highlightedTypeForDay(it),
            isCurrentDay = isCurrentDay,
            isCurrentMonth = isCurrentMonth,
            isInDateRange = isInDateRange,
            showCurrentMonthOnly = showCurrentMonthOnly
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(gridSpacing),
    ) {
        items(
            items = dates,
            key = { it.localDate },
            contentType = { "day" },
        ) {
            CalendarDay(
                date = it,
                onSelected = { day -> onSelected(mode.onSelectedDay(day)) },
                dayOption = calendarDayOption?.invoke(it.localDate) ?: DayOption.Default,
            )
        }
    }
}
