package com.docter.docter.utils

import android.app.Activity
import android.widget.Toast


fun Activity.toast(message: CharSequence, duration: Int) {
    Toast.makeText(this, message, duration).show()
}