package space.ui.planet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import space.models.api.character.PlanetResponseItem
import space.repository.HomeRepository

class PlanetViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _charactersLiveData = MutableLiveData<List<PlanetResponseItem>>()
    val charactersLiveData: LiveData<List<PlanetResponseItem>> get() = _charactersLiveData

    init {
        fetchEchoes()
    }
    private fun fetchEchoes() {
        viewModelScope.launch {
            try {
                val newItem = withContext(Dispatchers.IO) {
                    repository.fetchCharacters()
                }
                _charactersLiveData.postValue(newItem.body()?.filter { it.isPlanet }?.toList())

            } catch (e: Exception) {
                // Handle exceptions, if any
                Log.e("getDetail", "Error fetching details: ${e.message}")
            }
        }
    }

}