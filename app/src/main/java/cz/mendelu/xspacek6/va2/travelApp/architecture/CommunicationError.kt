package cz.mendelu.xspacek6.va2.travelApp.architecture

data class CommunicationError(
    val code: Int,
    var message: String?,
    var secondaryMessage: String? = null)