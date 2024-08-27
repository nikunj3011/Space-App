package space.repository

import space.api.ZZZGuidesService

class HomeRepository(
    private val apiService: ZZZGuidesService
) {
    suspend fun fetchBangboos() = apiService.getBangboos()
    suspend fun fetchCharacters() = apiService.getCharacters()
    suspend fun fetchWEngines() = apiService.getWEngines()
    suspend fun fetchCharacterDetails(id: String) = apiService.getCharactersDetail(id)
    suspend fun fetchCharacterDetails() = apiService.getCharactersDetails()
}