package space.ui.characterdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import space.models.api.characterdetail.CharacterDetailResponse
import space.repository.HomeRepository

class CharacterDetailsViewModel(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _characterDetailsLiveData = MutableLiveData<CharacterDetailResponse>()
    val characterDetailsLiveData: LiveData<CharacterDetailResponse> get() = _characterDetailsLiveData
    var id: String = ""

    init {
        fetchCharacterDetails()
    }
    private fun fetchCharacterDetails() {
        viewModelScope.launch {
            try {
                val newItem = withContext(Dispatchers.IO) {
                    repository.fetchCharacterDetails(id)
                }
                _characterDetailsLiveData.postValue(newItem.body())
            } catch (e: Exception) {
                // Handle exceptions, if any
                Log.e("getDetail", "Error fetching details: ${e.message}")
            }
        }
    }

}