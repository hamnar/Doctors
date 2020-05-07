/*
 * Created by M1028379 on 18/12/2019
 * Copyright (c) CARTUS B2B 2019 .
 * All rights reserved.
 * Last modified 12/17/19 8:02 PM
 *
 * CHANGE LOG
 * —------------------------------------------------------
 * ID     Bug ID   Author Name     Date         Description
 * —------------------------------------------------------
 */

package com.docter.docter.configuration

/*
* Class to define all the required constants and reusable values
* */
class AppConfig {
    companion object {
        const val IS_FIRST_TIME_USER = "first_user"
        /* SplashActivity Constants*/
        const val SPLASH_DELAY: Long = 4000 //4 seconds
        const val SPLASH_NETWORK_DELAY: Long = 2000 * 10 //20 seconds
        const val SPLASH_DUMMY_DELAY: Long = 6000 //6 seconds
        const val WELCOME_DELAY: Long = 1000 * 10 //10 seconds

        /* Tutorial Flow Constants */
        const val IS_TUTORIAL_SHOWN = "tutorial_shown"

        /* Login Flow Constants */
        const val IS_LOGGED_IN = "log_in_status"
        // Submit Agr  home and list checkbox
        const val GOOGLEAPI = "AIzaSyC7Av3bD6yBdLbPpwBvou6bsp9gjFLFZZA"

        // Network response codes */
        const val NETWORK_SUCCESS = 200
        const val NETWORK_SUCCESS_CREATED = 201
        const val NETWORK_ERROR = 400
        const val UNAUTHORIZED_ERROR = 401
        const val FORBIDDEN_ERROR = 403
        const val SERVER_ERROR = 500
        const val NO_DATA_ERROR = 404

    }

}