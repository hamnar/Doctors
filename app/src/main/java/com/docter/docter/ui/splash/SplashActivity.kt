/*
 * Created by M1028379 on 18/12/2019
 * Copyright (c) CARTUS B2B 2019 .
 * All rights reserved.
 * Last modified 12/18/19 11:47 AM
 *
 * CHANGE LOG
 * —------------------------------------------------------
 * ID     Bug ID   Author Name     Date         Description
 * —------------------------------------------------------
 */

package com.docter.docter.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.docter.docter.HomeView
import com.docter.docter.viewmodel.SplashViewModel
import com.docter.docter.R
import com.docter.docter.configuration.AppConfig

/*
*  Class responsible to handle splashbg screen login and navigation to other screens
* */
class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var handler: Handler

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        handler = Handler()

        Handler().postDelayed({
            // This method will be executed once the timer is over
//            checkNetworkConnectivity()
            goToHomeScreen()
        }, AppConfig.SPLASH_DELAY)

    }

    /*
    * Function to check if connected to internet
    * */
//    private fun checkNetworkConnectivity() {
//
//        if (CommonUtils.checkIfConnectedToInternet(applicationContext)) {
//            progressBar.visibility = View.VISIBLE
//            handler.postDelayed({
//                progressBar.visibility = View.GONE
//                //finish()
//                navigateToOtherScreens()
//            }, AppConfig.SPLASH_DUMMY_DELAY)
//
//        } else {
//            progressBar.visibility = View.GONE
//            showErrorToastMessage()
//        }
//    }

    /*
    *  Navigation to Home screen
    * */
    private fun goToHomeScreen() {
        finish()
        startActivity(Intent(this, HomeView::class.java))
    }

    /*
    *  Navigation to Welcome screen
    * */
//    private fun goToWelcomeScreen() {
//        finish()
//        startActivity(Intent(this, WelcomeActivity::class.java))
//    }

}