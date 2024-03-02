package com.tomtruyen.composevalidation.rules

import android.content.Context
import com.tomtruyen.composevalidation.TextValidationRule

class RequiredValidationRule(
    override val errorMessage: (context: Context) -> String = {
        "This field is required"
    }
): TextValidationRule {
    override val validationRule: (String) -> Boolean = { it.isNotEmpty() && it.isNotBlank() }
}
