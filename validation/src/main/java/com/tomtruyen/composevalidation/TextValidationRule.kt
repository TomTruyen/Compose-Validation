package com.tomtruyen.composevalidation

import android.content.Context

interface TextValidationRule {
    val errorMessage: (context : Context) -> String
    val validationRule: (String) -> Boolean
}
