package space.models.api.character

data class Skill(
    val children: List<Children>,
    val icon_url: String,
    val title: String
)