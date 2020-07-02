package com.example.productdelivery.ui.categories

import androidx.lifecycle.*
import com.example.productdelivery.data.DeliveryRepository
import com.example.productdelivery.data.ErrorCode
import com.example.productdelivery.data.Result
import com.example.productdelivery.data.api.NoNetworkConnectionException
import com.example.productdelivery.data.model.Category
import com.example.productdelivery.util.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class CategoryViewModel @Inject constructor(private val repository: DeliveryRepository) :
    ViewModel() {

    private var _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>>
        get() = _categories
    private var _categoriesEvent: MutableLiveData<Event<Result<*>>> =
        MutableLiveData(Event(Result.None))
    val categoriesEvent: LiveData<Event<Result<*>>>
        get() = _categoriesEvent

    init {
        clearData()
    }


    fun getCategories() {
        viewModelScope.launch {
            _categoriesEvent.value = Event(Result.Loading)

            var result: Result<*> = Result.None
            val time = measureTimeMillis {


                result = try {
                    repository.getCategories()
                } catch (e: NoNetworkConnectionException) {
                    e.printStackTrace()
                    Result.Error(code = ErrorCode.NO_DATA_CONNECTION.code)
                }
                if (result is Result.Success) {
                    var values: List<Category> = ArrayList()
                    val data = (result as Result.Success).data as List<*>
                    for (item in data.filterIsInstance<Category>()) {
                        values = values.plus(item)
                    }
                    _categories.value = values
                }

            }

            Timber.d("Get categories duration $time")
            _categoriesEvent.value = Event(result)
        }
    }

    private fun clearData() {
        _categoriesEvent.value = Event(Result.None)
        _categories.value = null
    }
}
