package com.example.slideandroiddevchallenge.view.extensions

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout


fun TextInputLayout.setError(hasError: Boolean, errorText: String? = "") {
    if (hasError) {
        this.error = errorText
    } else {
        this.error = null
        this.isErrorEnabled = false
    }
}

fun TextInputLayout.isValid(pattern: Regex, errorText: String? = ""): Boolean {
    this.setError(!this.editText?.text.toString().trim().matches(pattern), errorText)
    return this.editText?.text.toString().trim().matches(pattern)
}

fun EditText.checkEmptyEditText(): Boolean {
    return this.text.isEmpty()
}
