package space.models.api.wengines

data class WEngineResponseItem(
    val additional_info: String,
    val attributes: List<Attribute>,
    val categories: List<Category>,
    val desc: String,
    val icon_url: String,
    val name: String,
    val title: String
)