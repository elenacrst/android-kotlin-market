package com.example.productdelivery

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.productdelivery.data.local.ILocalInformation
import com.example.productdelivery.data.local.LocalInformation
import com.example.productdelivery.databinding.ActivityMainBinding
import com.example.productdelivery.di.DaggerDeliveryComponent
import com.example.productdelivery.ui.BaseActivity
import com.example.productdelivery.ui.LanguageDialog
import com.example.productdelivery.util.wrap
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import timber.log.Timber
import javax.inject.Inject

class MainActivity  : BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    //@Inject
  //  lateinit var localPreferences: ILocalInformation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.lifecycleOwner = this
        setSupportActionBar(mainBinding.tbMain)

       DeliveryApplication.deliveryApplicationContext.appComponent.inject(this)

        navController = findNavController(R.id.main_nav_host_fragment)
    }

    override fun attachBaseContext(newBase: Context?) {
        //update locale again to change the strings got from application context
        //this only works for all androids other than android 7 :(
        val context = wrap(newBase!!, DeliveryApplication.deliveryApplicationContext.localDataSource.getLanguage())
        super.attachBaseContext(context)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main_activity, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_language){
            val dialog = LanguageDialog
                .createInstance(DeliveryApplication.deliveryApplicationContext.localDataSource.getLanguage())
                .apply {
                    setPositiveAction {
                        updateLanguage(it)
                    }
                }
            showDialog(dialog)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateLanguage(selectedLanguage: String) {
        if (selectedLanguage != DeliveryApplication.deliveryApplicationContext.localDataSource.getLanguage()) {
            //save language to shared prefs
            DeliveryApplication.deliveryApplicationContext.localDataSource.saveLanguage(selectedLanguage)

            //let application know the language in prefs has changed
            DeliveryApplication.deliveryApplicationContext.updateLocale()

            refreshActivity()
        }
    }

    private fun refreshActivity() {
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}
