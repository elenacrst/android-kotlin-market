package com.example.productdelivery.ui

import android.app.Dialog
import android.view.Window
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingResource
import com.example.productdelivery.R
import com.example.productdelivery.data.ErrorCode
import com.example.productdelivery.util.Event
import com.example.productdelivery.util.toast
import dagger.android.support.DaggerFragment
import timber.log.Timber


open class BaseFragment : Fragment() {
    var isKeyboardShowing = false
    private var currentAlertDialog: AlertDialog? = null
    private var wasProgress = false

    fun handleError(resultError: com.example.productdelivery.data.Result.Error): Boolean {
        return when (resultError.code) {
            ErrorCode.NO_DATA_CONNECTION.code -> {
                requireActivity().toast(getString(R.string.no_data_connection))
                true
            }
            else -> false
        }
    }

    protected fun createLoadingObserver(
        progressListener: () -> Unit = {},
        successListener: (com.example.productdelivery.data.Result<*>?) -> Unit = { },
        errorListener: () -> Unit = { }
    ): Observer<Event<com.example.productdelivery.data.Result<*>>> {
        return Observer { result ->
            when (val value = result.getContentIfNotHandled()) {
                is com.example.productdelivery.data.Result.Success -> {
                    successListener(value)
                }
                is com.example.productdelivery.data.Result.Loading -> progressListener()
                is com.example.productdelivery.data.Result.Error -> {
                    val resultError = result.peekContent() as com.example.productdelivery.data.Result.Error
                    val resultHandled = handleError(resultError)

                    if (!resultHandled) {
                        requireActivity().toast(message = (result.peekContent() as
                                com.example.productdelivery.data.Result.Error).message.toString())
                        errorListener()
                    }
                }
                else -> Timber.d("Nothing to do here")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        currentAlertDialog?.dismiss()
    }
}
