package com.example.productdelivery.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.example.productdelivery.data.local.LocalInformation
import com.example.productdelivery.util.updateLocaleConfig
import com.example.productdelivery.util.wrap
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private var currentDialog: DialogFragment? = null
    private var lastAlertDialog: AlertDialog? = null

    /*init {
        //update locale language
        updateLocaleConfig(this)
    }*/

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        updateLocaleConfig(this)
    }



    fun showDialog(nextDialog: DialogFragment) {
        if (isFinishing) {
            return
        }
        if (currentDialog != null) {
            currentDialog!!.dismiss()
            lastAlertDialog?.dismiss()
        }
        nextDialog.show(supportFragmentManager, DIALOG_TAG)
        currentDialog = nextDialog
    }

    companion object {
        const val DIALOG_TAG = "dialog tag"
    }
}
