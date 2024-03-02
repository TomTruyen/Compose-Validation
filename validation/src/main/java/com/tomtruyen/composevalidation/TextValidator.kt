package com.tomtruyen.composevalidation

import android.content.Context

@Suppress("UNUSED_EXPRESSION")
open class TextValidator private constructor(
    private var text: String? = null,
    rulesBuilder: TextValidator.() -> Unit
) {

    companion object {
        fun withRules(vararg rules: TextValidationRule): TextValidator {
            return TextValidator(
                rulesBuilder = {
                    rules.forEach {
                        add(it)
                    }
                }
            )
        }
    }

    /**
     * The rules that will be used to validate the view.
     */
    protected val rules: MutableList<TextValidationRule> = mutableListOf()

    init {
        rulesBuilder()
    }

    /**
     * This implementation of the validate function will validate the view based on the rules that are added to this validator.
     */
    private fun validate(context: Context): ValidationResult {
        val localText = text ?: return ValidationResult.Invalid(setOf())
        val validationResult = rules.foldRight(ValidationResult.Valid as ValidationResult) { rule, acc ->
            val isValid = rule.validationRule(localText)
            val result = if (isValid) ValidationResult.Valid else ValidationResult.Invalid(
                setOf(
                    rule.errorMessage(context)
                )
            )
            return@foldRight acc combine result
        }
        return validationResult
    }

    fun validate(context: Context, text: String?): ValidationResult {
        setText(text)
        return validate(context)
    }

    private fun setText(text: String?) {
        this.text = text
    }

    /**
     * Adds a rule to the validator.
     * @param rule the rule that will be added
     */
    fun add(rule: TextValidationRule) {
        this.rules.add(rule)
    }

    /**
     * Adds a list of rules to the validator.
     * @param rules the rules that will be added
     */
    fun add(rules: List<TextValidationRule>) {
        this.rules.addAll(rules)
    }

    /**
     * Removes a rule from the validator.
     * @param rule the rule that will be removed
     */
    fun remove(rule: TextValidationRule) {
        this.rules.remove(rule)
    }

    /**
     * Removes a list of rules from the validator.
     * @param rules the rules that will be removed
     */
    fun remove(rules: List<TextValidationRule>) {
        this.rules.removeAll(rules)
    }

    /**
     * Removes all rules from the validator.
     */
    fun clear() {
        this.rules.clear()
    }

}
