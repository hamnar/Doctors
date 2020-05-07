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

package com.docter.docter.main

import android.app.Application

class MainApplication : Application() {
    companion object {
        lateinit var appContext: MainApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}