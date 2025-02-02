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

package com.squaredem.composecalendar.utils

import com.squaredem.composecalendar.model.WeekDaysMode
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.OffsetTime
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

internal fun WeekDaysMode.getText(day: DayOfWeek): String = when (this) {
    WeekDaysMode.SingleLetter -> day.getDisplayName(
        TextStyle.NARROW,
        Locale.getDefault()
    )

    WeekDaysMode.DoubleLetter -> {
        day.getDisplayName(
            TextStyle.SHORT_STANDALONE,
            Locale.getDefault()
        ).substring(0, 2)
    }
}

internal fun LocalDate.formatWithFormatter(
    formatter: SimpleDateFormat,
    time: OffsetTime = OffsetTime.now(),
) = atTime(time).toInstant().let { formatter.format(Date.from(it)) }
