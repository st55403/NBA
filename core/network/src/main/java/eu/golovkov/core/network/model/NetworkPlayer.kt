package eu.golovkov.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPlayer(
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("height_feet")
    val heightFeet: Int = 0,
    @SerialName("height_inches")
    val heightInches: Int = 0,
    val id: Int = 0,
    @SerialName("last_name")
    val lastName: String = "",
    val position: String = "",
    val team: NetworkTeam = NetworkTeam(),
    @SerialName("weight_pounds")
    val weightPounds: Int = 0
)