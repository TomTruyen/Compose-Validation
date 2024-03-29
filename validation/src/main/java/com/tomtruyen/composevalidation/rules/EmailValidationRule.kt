package com.tomtruyen.composevalidation.rules

import android.content.Context
import com.tomtruyen.composevalidation.TextValidationRule

class EmailValidationRule(
    override val errorMessage: (context: Context) -> String = {
        "Invalid email"
    },
) : TextValidationRule {
    companion object {
        private const val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$"
    }

    override val validationRule: (String) -> Boolean = { it.matches(Regex(EMAIL_REGEX)) }
}
