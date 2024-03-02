# Compose Validation

## Usage

### UIState
```kotlin

data class MyUIState(
    // This is the value of the input
    val requiredInput: String? = null,
    
    // This is the last result of the validation and will be used to display a possible error state
    var requiredInputValidationResult: MutableState<ValidationResult?> = mutableStateOf(null)
) {
    // This is a validator with the rules on which you want to validate
    private val validator = TextValidator.withRules(RequiredRule()) // Takes a vararg of rules so you can add as many as you want
    
    // This is a method that will be called in the LaunchEffect of the composable when the dependency changes (in this case "requiredInput") 
    // Has to be called in the LaunchedEffect, since we need the context to validate the input
    fun validateRequiredInput(context: Context) {
        if(requiredInput == null) return // If the input is null, we don't want to validate it
        
        // Perform the validation and update the result
        // .value is used to update the MutableState
        requiredInputValidationResult.value = validator.validate(context, requiredInput)
    } 
}

```

### Composable

#### Handle validating the inputs
```kotlin

val context = LocalContext.current

LaunchedEffect(uiState.requiredInput) {
    uiState.validateRequiredInput(context)
}

```

#### Displaying the errorMessage (e.g.: in a TextField)
```kotlin
MyTextField(
    error = uiState.requiredInputValidationResult.errorMessage() // Will be your error message if the validation fails
)
```

#### Validating all inputs (e.g: for a button enabled state)
```kotlin
val areInputsValid by remember {
    derivedStateOf {
        uiState.requiredInputValidationResult.isValid() == true
        // More validation checks can be added
        // e.g.: && uiState.otherInputValidationResult?.isValid() == true && ...
    }
}

Button(
    enabled = areInputsValid
)
```

### Building your own validation rules
```kotlin
class MyCustomRule(
    override val errorMessage: (context: Context) -> String = { context -> context.getString(R.string.error_message) }
): TextValidationRule {
    override val validationRule: (String) -> Boolean = { 
        // Your validation logic
        // e.g.: input.length > 5
        
        // Return true if the input is valid
    }   
}
```