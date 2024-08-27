package space.models.api.character

import space.models.api.characterdetail.CardImage

data class AgentResponseItem(
    val element: String,
    val rarity: String,
    val faction: String,
    val style: String,
    val name: String,
    val cardImage: CardImage,
    val image: String,
    val slug: String,
)