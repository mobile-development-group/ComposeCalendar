/*
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

package com.squaredem.composecalendar.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate

object CalendarDefaults {
    fun defaultContentConfig(
        showSelectedDateTitle: Boolean = true,
        extraButtonHelper: ExtraButtonHelperType = ExtraButtonHelperType.MonthChevrons,
        calendarDayOption: ((LocalDate) -> DayOption)? = null,
        weekdaysMode: WeekDaysMode = WeekDaysMode.SingleLetter,
        calendarYearPickerFormat: String = "MMMM yyyy",
        currentPagerDate: LocalDate? = null,
        hasDividers: Boolean = false,
        selectorBackgroundRadius: Dp = 12.dp,
        maxWidth: Dp = 448.dp,
        weekStartDay: DayOfWeek = DayOfWeek.MONDAY,
        todayTitle: String = "Today",
    ) = CalendarContentConfig(
        showSelectedDateTitle = showSelectedDateTitle,
        extraButtonHelper = extraButtonHelper,
        calendarDayOption = calendarDayOption,
        weekDaysMode = weekdaysMode,
        calendarYearPickerFormat = calendarYearPickerFormat,
        currentPagerDate = currentPagerDate,
        hasDividers = hasDividers,
        selectorBackgroundRadius = selectorBackgroundRadius,
        maxWidth = maxWidth,
        weekStartDay = weekStartDay,
        todayTitle = todayTitle,
    )

    @Composable
    fun defaultColors(
        containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        monthChevron: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        defaultText: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledText: Color = MaterialTheme.colorScheme.primary,
        selectedDayBackground: Color = MaterialTheme.colorScheme.primary,
        selectedDayText: Color = MaterialTheme.colorScheme.onPrimary,
        currentDayHighlight: Color = MaterialTheme.colorScheme.primary,
        inRangeDayBackground: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        inRangeDayText: Color = MaterialTheme.colorScheme.onPrimary,
        todayButtonText: Color = MaterialTheme.colorScheme.primary,
        yearPickerTitleHighlight: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        yearPickerText: Color = MaterialTheme.colorScheme.primary,
        yearPickerSelectedText: Color = MaterialTheme.colorScheme.onPrimary,
        yearPickerSelectedBackground: Color = MaterialTheme.colorScheme.primary,
        yearPickerCurrentYearHighlight: Color = MaterialTheme.colorScheme.primary,
        dayOfWeek: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        headerText: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        dividerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
    ) = CalendarColors(
        containerColor = containerColor,
        monthChevron = monthChevron,
        defaultText = defaultText,
        disabledText = disabledText,
        selectedDayBackground = selectedDayBackground,
        selectedDayText = selectedDayText,
        currentDayHighlight = currentDayHighlight,
        inRangeDayBackground = inRangeDayBackground,
        inRangeDayText = inRangeDayText,
        todayButtonText = todayButtonText,
        yearPickerTitleHighlight = yearPickerTitleHighlight,
        yearPickerText = yearPickerText,
        yearPickerSelectedText = yearPickerSelectedText,
        yearPickerSelectedBackground = yearPickerSelectedBackground,
        yearPickerCurrentYearHighlight = yearPickerCurrentYearHighlight,
        dayOfWeek = dayOfWeek,
        headerText = headerText,
        dividerColor = dividerColor,
    )
}
