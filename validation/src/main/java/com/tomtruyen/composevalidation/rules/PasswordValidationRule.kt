package com.tomtruyen.composevalidation.rules

import android.content.Context
import com.tomtruyen.composevalidation.TextValidationRule

class PasswordValidationRule(
    override val errorMessage: (context: Context) -> String = {
        "Password must contain at least 1 uppercase, 1 lowercase, 1 digit and 6 characters long"
    },
) : TextValidationRule {
    companion object {
        private const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$"
    }

    override val validationRule: (String) -> Boolean = { it.matches(Regex(PASSWORD_REGEX)) }
}