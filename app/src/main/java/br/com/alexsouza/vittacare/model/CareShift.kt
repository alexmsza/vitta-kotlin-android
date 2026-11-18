package br.com.alexsouza.vittacare.model

enum class ShiftStatus(val label: String) {
    OPEN("Disponível"),
    ACCEPTED("Aceito"),
    COMPLETED("Concluído")
}

data class CareShift(
    val id: String,
    val patientName: String,
    val neighborhood: String,
    val schedule: String,
    val hourlyRateBrl: Int,
    val careNotes: String,
    val status: ShiftStatus = ShiftStatus.OPEN
)
