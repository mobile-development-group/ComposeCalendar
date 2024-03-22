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

import java.time.LocalDate

sealed class CalendarMode {
    abstract val minDate: LocalDate
    abstract val maxDate: LocalDate

    val startDate: LocalDate
        get() = when (this) {
            is Range -> selection?.startDate ?: LocalDate.now()
            is Single -> selectedDate ?: LocalDate.now()
        }

    fun onSelectedDay(localDate: LocalDate): CalendarMode {
        return when (this) {
            is Range -> {
                onDayClicked(localDate)
            }

            is Single -> {
                copy(
                    selectedDate = localDate,
                )
            }
        }
    }

    data class Single(
        override val minDate: LocalDate,
        override val maxDate: LocalDate,
        val selectedDate: LocalDate? = null,
        val titleFormatter: (LocalDate?) -> String = DefaultTitleFormatters.singleDate(),
    ) : CalendarMode()

    data class Range(
        override val minDate: LocalDate,
        override val maxDate: LocalDate,
        val selection: DateRangeSelection? = null,
        val titleFormatter: (DateRangeSelection?) -> String = DefaultTitleFormatters.dateRange(),
        val selectionMode: ForcedSelectMode = ForcedSelectMode.StartDate,
    ) : CalendarMode()
}

internal fun CalendarMode.Range.rangeSelected(): Boolean =
    selection?.endDate != null && selection.endDate != selection.startDate

internal fun CalendarMode.Range.onDayClicked(day: LocalDate): CalendarMode.Range = when {
    selection == null -> {
        copy(
            selection = DateRangeSelection(day),
            selectionMode = ForcedSelectMode.EndDate,
        )
    }

    selectionMode == ForcedSelectMode.StartDate -> {
        when {
            // BO: Single date selected, and the new date is after selected start ->
            // Continue single selection on the selected day.
            !rangeSelected() && day.isAfter(selection.startDate) -> copy(
                selection = DateRangeSelection(day),
                selectionMode = ForcedSelectMode.EndDate
            )

            // BO: Single date selected, and the new date is before selected start ->
            // Date range from the selected date to the previous start date.
            !rangeSelected() && day.isBefore(selection.startDate) -> copy(
                selection = DateRangeSelection(day, startDate),
                selectionMode = ForcedSelectMode.EndDate
            )

            // BO: Single date selected, and the new date is the same as start ->
            // Clear selection.
            !rangeSelected() && day == selection.startDate -> copy(
                selection = null,
                selectionMode = ForcedSelectMode.StartDate
            )

            // BO: Range selected, and the date selected is the start date ->
            // Deselect start date.
            rangeSelected() && day == selection.startDate -> copy(
                selection = selection.endDate?.let { DateRangeSelection(it) },
                selectionMode = ForcedSelectMode.EndDate
            )

            // BO: Range selected, and the date selected is before end date ->
            // Make a range from selection to end date.
            rangeSelected() && day.isBefore(selection.endDate) -> copy(
                selection = selection.copy(startDate = day),
                selectionMode = ForcedSelectMode.EndDate
            )

            // BO: Range selected, and the new date is the end date ->
            // Deselect end date.
            rangeSelected() && day == selection.endDate -> copy(
                selection = selection.copy(startDate = day, endDate = null),
                selectionMode = ForcedSelectMode.EndDate
            )

            else -> copy(
                selection = DateRangeSelection(startDate = day),
                selectionMode = ForcedSelectMode.EndDate
            )
        }
    }

    selectionMode == ForcedSelectMode.EndDate -> {
        when {
            // BO: Date selected is before the start date ->
            // Make single selection with the selected day.
            day.isBefore(selection.startDate) -> copy(
                selection = selection.copy(
                    startDate = day,
                    endDate = selection.endDate ?: selection.startDate,
                )
            )

            // BO: Single selection, and the new date is after start date ->
            // Make range from old start to selected day.
            !rangeSelected() && day.isAfter(selection.startDate) -> copy(
                selection = selection.copy(endDate = day)
            )

            // BO: Single selection, and the new date is the same as start ->
            // Clear selection.
            !rangeSelected() && day == selection.startDate -> copy(
                selection = null,
                selectionMode = ForcedSelectMode.StartDate,
            )

            // BO: Range selection, and the new date is the start date ->
            // Clear start selection.
            rangeSelected() && day == selection.startDate -> copy(
                selection = selection.endDate?.let { DateRangeSelection(it) }
            )

            // BO: Range selection, and the new date is the end date ->
            // Clear end selection.
            rangeSelected() && day == selection.endDate -> copy(
                selection = selection.copy(endDate = null),
            )

            else -> copy(
                selection = selection.copy(endDate = day)
            )
        }
    }

    else -> this
}

internal fun CalendarMode.highlightedTypeForDay(day: LocalDate): HighlightedType? = when (this) {
    is CalendarMode.Range -> {
        when {
            selection == null -> null
            selection.endDate == null -> null
            selection.startDate == selection.endDate -> null
            day == selection.startDate -> HighlightedType.End
            day == selection.endDate -> HighlightedType.Start
            day > selection.startDate && day < selection.endDate -> HighlightedType.Full
            else -> null
        }
    }

    is CalendarMode.Single -> null
}

internal fun CalendarMode.hasSelectionIndicator(day: LocalDate): Boolean = when (this) {
    is CalendarMode.Range -> day == selection?.startDate || day == selection?.endDate
    is CalendarMode.Single -> selectedDate == day
}

data class DateRangeSelection(
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
)
