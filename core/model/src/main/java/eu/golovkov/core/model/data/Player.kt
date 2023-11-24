package eu.golovkov.core.model.data

data class Player(
    val firstName: String,
    val heightFeet: Float,
    val heightInches: Float,
    val id: Int,
    val lastName: String,
    val position: String,
    val team: Team,
    val weightPounds: Float
)