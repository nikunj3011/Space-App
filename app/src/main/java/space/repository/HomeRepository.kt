package space.repository

import space.api.SpaceService

class HomeRepository(
    private val apiService: SpaceService
) {
    suspend fun fetchBangboos() = apiService.getBangboos()
    suspend fun fetchCharacters() = apiService.getCharacters()
    suspend fun fetchWEngines() = apiService.getWEngines()
    suspend fun fetchCharacterDetails(id: String) = apiService.getCharactersDetail(id)
    suspend fun fetchCharacterDetails() = apiService.getCharactersDetails()
}