package space.models.api.characterdetail

data class Build(
    val engines: List<Engine>,
    val main_4: List<Main4>,
    val main_5: List<Main4>,
    val main_6: List<Main6>,
    val sets: Any,
    val substats: String
)