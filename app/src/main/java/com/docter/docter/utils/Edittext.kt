package com.docter.docter.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.docter.docter.main.MainApplication
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialAutoCompleteTextView

fun EditText.onChange() {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            (s.toString())
        }
    })
}

fun MaterialAutoCompleteTextView.onChange(editFirstnameLayout: TextInputLayout) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            (s.toString())
        }
    })
    fun View.showSnackbar(snackbarText: String, timeLength: Int) {
        Toast.makeText(MainApplication.appContext, snackbarText, timeLength).show()
    }

}
