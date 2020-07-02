package com.example.productdelivery.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import com.example.productdelivery.util.updateLocaleConfig

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private var currentDialog: DialogFragment? = null
    private var lastAlertDialog: AlertDialog? = null


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
