package space.ui.wengines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import space.models.api.wengines.WEngineResponseItem
import space.repository.HomeRepository

class WEngineViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _weaponsLiveData = MutableLiveData<List<WEngineResponseItem>>()
    val weaponsLiveData: LiveData<List<WEngineResponseItem>> get() = _weaponsLiveData
    var _weapons = listOf<WEngineResponseItem>()

    init {
        fetchWepons()
    }
    private fun fetchWepons() {
        viewModelScope.launch {
            try {
                val newItem = withContext(Dispatchers.IO) {
                    repository.fetchWEngines()
                }
                _weaponsLiveData.postValue(newItem.body()?.toList())
                _weapons = newItem.body()!!.toList()
                Log.e("element", newItem.toString())
            } catch (e: Exception) {
                // Handle exceptions, if any
                Log.e("getDetail", "Error fetching details: ${e.message}")
            }
        }
    }

}