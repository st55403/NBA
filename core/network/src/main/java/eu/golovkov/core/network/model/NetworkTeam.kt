package eu.golovkov.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTeam(
    val abbreviation: String = "",
    val city: String = "",
    val conference: String = "",
    val division: String = "",
    @SerialName("full_name")
    val fullName: String = "",
    val id: Int = 0,
    val name: String = ""
)