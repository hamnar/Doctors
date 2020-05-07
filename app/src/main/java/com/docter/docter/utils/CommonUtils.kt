/*
 * Created by M1028379 on 18/12/2019
 * Copyright (c) CARTUS B2B 2019 .
 * All rights reserved.
 * Last modified 12/17/19 3:05 PM
 *
 * CHANGE LOG
 * —------------------------------------------------------
 * ID     Bug ID   Author Name     Date         Description
 * —------------------------------------------------------
 */

package com.cartus.broker2broker.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.core.app.ActivityCompat

/*
*  Common class includes reusable methods
* */
object CommonUtils {

    /*
    * Function to check if connected to internet
    * */
    fun checkIfConnectedToInternet(context: Context?): Boolean {
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.activeNetwork
            } else {
                TODO("VERSION.SDK_INT < M")
            }
            return activeNetwork != null
        }
        return false
    }
//    fun onrequestPermission(context: Context?,String permission) {
//        ActivityCompat.requestPermissions(this, permission, PERMISSION_REQUEST_CODE)
//    }

}