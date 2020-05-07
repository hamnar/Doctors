package com.docter.docter.lisener

import com.docter.docter.responseModel.StateWiseBase


interface StateWiseListener {
    fun onStateWiseSuccess(response: StateWiseBase?)
    fun onStateWiseFailure()
}

