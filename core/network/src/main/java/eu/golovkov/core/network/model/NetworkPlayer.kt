package eu.golovkov.core.network.model

import eu.golovkov.core.model.data.Player
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPlayer(
    @SerialName("first_name")
    val firstName: String = "",
    @SerialName("height_feet")
    val heightFeet: Float?,
    @SerialName("height_inches")
    val heightInches: Float?,
    val id: Int,
    @SerialName("last_name")
    val lastName: String = "",
    val position: String = "",
    val team: NetworkTeam = NetworkTeam(),
    @SerialName("weight_pounds")
    val weightPounds: Float?,
)

fun NetworkPlayer.asPlayer() = Player(
    firstName = firstName,
    heightFeet = heightFeet ?: 0f,
    heightInches = heightInches ?: 0f,
    id = id,
    lastName = lastName,
    position = position,
    team = team.asTeam(),
    weightPounds = weightPounds ?: 0f
)