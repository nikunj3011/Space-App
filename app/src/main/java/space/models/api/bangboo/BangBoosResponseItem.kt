package space.models.api.bangboo

data class BangBoosResponseItem(
    val bio: String,
    val categories: List<Category>,
    val name: String,
    val nick_name: String,
    val skills: List<Skill>,
    val stats: Stats,
    val title: String
)