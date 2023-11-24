package eu.golovkov.core.network.model

import eu.golovkov.core.model.data.Player
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPlayersResponse(
    @SerialName("data")
    val data: List<Data>,
    @SerialName("meta")
    val meta: Meta
)

@Serializable
data class Data(
    val id: Int,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("height_feet")
    val heightFeet: Float? = null,
    @SerialName("height_inches")
    val heightInches: Float? = null,
    @SerialName("last_name")
    val lastName: String,
    val position: String,
    @SerialName("weight_pounds")
    val weightPounds: Float? = null,
)

fun Data.asPlayer() = Player(
    firstName = firstName,
    heightFeet = heightFeet?.toInt() ?: 0,
    heightInches = heightInches?.toInt() ?: 0,
    id = id,
    lastName = lastName,
    position = position,
    team = eu.golovkov.core.model.data.Team(
        id = 0,
        abbreviation = "",
        city = "",
        conference = "",
        division = "",
        fullName = "",
        name = ""
    ),
    weightPounds = weightPounds?.toInt() ?: 0
)

@Serializable
data class Meta(
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("next_page")
    val nextPage: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total_count")
    val totalCount: Int
)

@Serializable
data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @SerialName("full_name")
    val fullName: String,
    val name: String
)

fun Team.asTeam() = eu.golovkov.core.model.data.Team(
    id = id,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name
)