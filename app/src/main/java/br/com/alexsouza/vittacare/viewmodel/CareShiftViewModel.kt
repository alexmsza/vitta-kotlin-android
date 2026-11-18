package br.com.alexsouza.vittacare.viewmodel

import br.com.alexsouza.vittacare.model.CareShift
import br.com.alexsouza.vittacare.model.ShiftStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CareShiftUiState(
    val shifts: List<CareShift> = defaultShifts(),
    val selectedFilter: ShiftStatus? = null
) {
    val visibleShifts: List<CareShift>
        get() = selectedFilter?.let { filter ->
            shifts.filter { it.status == filter }
        } ?: shifts

    val openCount: Int
        get() = shifts.count { it.status == ShiftStatus.OPEN }

    val acceptedCount: Int
        get() = shifts.count { it.status == ShiftStatus.ACCEPTED }
}

class CareShiftViewModel {
    private val _uiState = MutableStateFlow(CareShiftUiState())
    val uiState: StateFlow<CareShiftUiState> = _uiState.asStateFlow()

    fun setFilter(status: ShiftStatus?) {
        _uiState.update { it.copy(selectedFilter = status) }
    }

    fun advanceShiftStatus(shiftId: String) {
        _uiState.update { state ->
            val updated = state.shifts.map { shift ->
                if (shift.id != shiftId) {
                    shift
                } else {
                    val nextStatus = when (shift.status) {
                        ShiftStatus.OPEN -> ShiftStatus.ACCEPTED
                        ShiftStatus.ACCEPTED -> ShiftStatus.COMPLETED
                        ShiftStatus.COMPLETED -> ShiftStatus.COMPLETED
                    }
                    shift.copy(status = nextStatus)
                }
            }
            state.copy(shifts = updated)
        }
    }

    fun addShift(
        patientName: String,
        neighborhood: String,
        schedule: String,
        hourlyRateBrl: Int,
        careNotes: String
    ) {
        val cleanName = patientName.trim()
        val cleanNeighborhood = neighborhood.trim()
        val cleanSchedule = schedule.trim()
        if (cleanName.isEmpty() || cleanNeighborhood.isEmpty() || cleanSchedule.isEmpty() || hourlyRateBrl <= 0) {
            return
        }

        val newShift = CareShift(
            id = "shift-${System.currentTimeMillis()}",
            patientName = cleanName,
            neighborhood = cleanNeighborhood,
            schedule = cleanSchedule,
            hourlyRateBrl = hourlyRateBrl,
            careNotes = careNotes.trim().ifEmpty { "Acompanhamento domiciliar padrão" }
        )

        _uiState.update { state ->
            state.copy(shifts = listOf(newShift) + state.shifts)
        }
    }
}

private fun defaultShifts(): List<CareShift> = listOf(
    CareShift(
        id = "1",
        patientName = "Dona Maria Helena",
        neighborhood = "Casa Forte, Recife",
        schedule = "Hoje · 19h às 07h",
        hourlyRateBrl = 45,
        careNotes = "Aferição de pressão e administração de medicação oral."
    ),
    CareShift(
        id = "2",
        patientName = "Sr. Antônio Carlos",
        neighborhood = "Boa Viagem, Recife",
        schedule = "Amanhã · 08h às 14h",
        hourlyRateBrl = 50,
        careNotes = "Auxílio de mobilidade e acompanhamento pós-operatório.",
        status = ShiftStatus.ACCEPTED
    ),
    CareShift(
        id = "3",
        patientName = "Dona Lúcia Ferreira",
        neighborhood = "Graças, Recife",
        schedule = "Ontem · 14h às 20h",
        hourlyRateBrl = 42,
        careNotes = "Higienização e registro de sinais vitais.",
        status = ShiftStatus.COMPLETED
    )
)
