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

package com.squaredem.composecalendar

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.squaredem.composecalendar.model.*
import java.time.LocalDate

/**
 * Display an alert dialog to pick a single date.
 */
@Composable
fun ComposeCalendar(
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.MIN,
    maxDate: LocalDate = LocalDate.MAX,
    onDone: (millis: LocalDate) -> Unit,
    onDismiss: () -> Unit,
    contentConfig: CalendarContentConfig = CalendarDefaults.defaultContentConfig(),
    calendarColors: CalendarColors = CalendarDefaults.defaultColors(),
    titleFormatter: (LocalDate?) -> String = DefaultTitleFormatters.singleDate(),
) {
    val selectedDate = remember { mutableStateOf(startDate) }
    var mode by remember {
        mutableStateOf(
            CalendarMode.Single(
                selectedDate = startDate,
                minDate = minDate,
                maxDate = maxDate,
                titleFormatter = titleFormatter,
            )
        )
    }

    AlertDialog(
        containerColor = calendarColors.containerColor,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDone(selectedDate.value)
            }) {
                Text(
                    text = stringResource(id = android.R.string.ok),
                    color = calendarColors.monthChevron
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = android.R.string.cancel),
                    color = calendarColors.monthChevron
                )
            }
        },
        text = {
            SingleDatePicker(
                mode = mode,
                onChanged = {
                    mode = it
                    selectedDate.value = it.selectedDate ?: it.startDate
                },
                contentConfig = contentConfig,
                calendarColors = calendarColors,
            )
        }
    )
}
