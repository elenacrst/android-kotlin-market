package com.example.productdelivery.ui.products

import androidx.lifecycle.*
import com.example.productdelivery.data.DeliveryRepository
import com.example.productdelivery.data.ErrorCode
import com.example.productdelivery.data.Result
import com.example.productdelivery.data.api.NoNetworkConnectionException
import com.example.productdelivery.data.model.Product
import com.example.productdelivery.util.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class ProductViewModel @Inject constructor(private val repository: DeliveryRepository) :
    ViewModel() {

    private var _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>>
        get() = _products
    private var _productsEvent: MutableLiveData<Event<Result<*>>> =
        MutableLiveData(Event(Result.None))
    val productsEvent: LiveData<Event<Result<*>>>
        get() = _productsEvent

    init {
        clearData()
    }

    fun getProductsByCategory(categoryId: Long) {
        viewModelScope.launch {
            _productsEvent.value = Event(Result.Loading)

            var result: Result<*> = Result.None
            val time = measureTimeMillis {


                result = try {
                    repository.getCategoryProducts(categoryId)
                } catch (e: NoNetworkConnectionException) {
                    e.printStackTrace()
                    Result.Error(code = ErrorCode.NO_DATA_CONNECTION.code)
                }
                if (result is Result.Success) {
                    var values: List<Product> = ArrayList()
                    val data = (result as Result.Success).data as List<*>
                    for (item in data.filterIsInstance<Product>()) {
                        values = values.plus(item)
                    }
                    _products.value = values
                }

            }

            Timber.d("Get products duration $time")
            _productsEvent.value = Event(result)
        }
    }

    private fun clearData() {
        _productsEvent.value = Event(Result.None)
        _products.value = null
    }
}
