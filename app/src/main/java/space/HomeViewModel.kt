package space

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

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {
    private val _characterDetailsLiveData = MutableLiveData<List<CharacterDetailResponse>>()
    val characterDetailsLiveData: LiveData<List<CharacterDetailResponse>> get() = _characterDetailsLiveData

    init {
        fetchData()
    }
    private fun fetchData() {
        viewModelScope.launch {
            try {
                val newItem2 = withContext(Dispatchers.IO) {
                    repository.fetchCharacterDetails()
                }
                _characterDetailsLiveData.postValue(newItem2.body()?.toList())
            } catch (e: Exception) {
                // Handle exceptions, if any
                Log.e("getDetail", "Error fetching details: ${e.message}")
            }
        }
    }

}