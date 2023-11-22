package eu.golovkov.core.model.data

data class Player(
    val firstName: String,
    val heightFeet: Int,
    val heightInches: Int,
    val id: Int,
    val lastName: String,
    val position: String,
    val team: Team,
    val weightPounds: Int
)