package eu.golovkov.core.ui

import eu.golovkov.core.model.data.Player
import eu.golovkov.core.model.data.Team

object MockData {
    val player = Player(
        firstName = "Lebron",
        heightFeet = 6f,
        heightInches = 8f,
        id = 1,
        lastName = "James",
        position = "SF",
        weightPounds = 250f,
        team = Team(
            abbreviation = "LAL",
            city = "Los Angeles",
            conference = "West",
            division = "Pacific",
            fullName = "Los Angeles Lakers",
            id = 1,
            name = "Lakers"
        )
    )
}