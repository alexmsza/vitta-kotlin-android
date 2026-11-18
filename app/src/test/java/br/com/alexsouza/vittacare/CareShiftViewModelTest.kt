package br.com.alexsouza.vittacare

import br.com.alexsouza.vittacare.model.ShiftStatus
import br.com.alexsouza.vittacare.viewmodel.CareShiftViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CareShiftViewModelTest {

    @Test
    fun `advanceShiftStatus transitions OPEN to ACCEPTED and COMPLETED`() {
        val viewModel = CareShiftViewModel()
        val firstShiftId = viewModel.uiState.value.shifts.first().id

        viewModel.advanceShiftStatus(firstShiftId)
        assertEquals(
            ShiftStatus.ACCEPTED,
            viewModel.uiState.value.shifts.first { it.id == firstShiftId }.status
        )

        viewModel.advanceShiftStatus(firstShiftId)
        assertEquals(
            ShiftStatus.COMPLETED,
            viewModel.uiState.value.shifts.first { it.id == firstShiftId }.status
        )
    }

    @Test
    fun `addShift prepends valid shift and ignores invalid input`() {
        val viewModel = CareShiftViewModel()
        val initialSize = viewModel.uiState.value.shifts.size

        viewModel.addShift("", "Centro", "10h", 50, "")
        assertEquals(initialSize, viewModel.uiState.value.shifts.size)

        viewModel.addShift("Sr. Pedro", "Espinheiro", "14h às 20h", 55, "Acompanhamento")
        assertEquals(initialSize + 1, viewModel.uiState.value.shifts.size)
        assertEquals("Sr. Pedro", viewModel.uiState.value.shifts.first().patientName)
    }
}
