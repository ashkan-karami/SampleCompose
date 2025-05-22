package com.ashkan.samplecompose.ui.screen.login

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashkan.samplecompose.ui.theme.SairaFontFamily
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme

@Composable
internal fun LoginRoute(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state: LoginState by viewModel.stateValue.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

//    LaunchedEffect(state) {
//        if (state.navigateToHome) {
//            onNavigateToHome()
//        }
//    }

    if (state.navigateToHome) {
        onNavigateToHome()
    }

    LoginScreen(
        modifier = modifier,
        uiState = state,
        onAction = {
            when(it){
                LoginAction.SubmitLogin -> {
                    keyboardController?.hide()
                    viewModel.onAction(it)
                }
                else -> {
                    viewModel.onAction(it)
                }
            }
        }
    )
}

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginState,
    onAction: (LoginAction) -> Unit
) {
    val passwordFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LoginHeader(modifier = modifier)
        Spacer(modifier = modifier.height(32.dp))
        if (uiState.loginFailure.isNullOrBlank().not()) {
            LoginFailureMessage(message = uiState.loginFailure)
        }
        EmailTextField(
            modifier = modifier,
            uiState = uiState,
            onAction = {
                when(it){
                    LoginAction.MoveFocusToPassword -> {
                        passwordFocusRequester.requestFocus()
                    }
                    else -> onAction(it)
                }
            }
        )
        PasswordTextField(
            modifier = modifier,
            focusRequester = passwordFocusRequester,
            uiState = uiState,
            onAction = onAction
        )
        TermAndConditions(modifier = modifier)
        LoginButton(
            modifier = modifier,
            uiState = uiState,
            onAction = onAction
        )
    }
}

@Composable
private fun LoginHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 128.dp)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.testTag(LOGIN_HEADER_TITLE)
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            modifier = modifier.testTag(LOGIN_DESCRIPTION_TAG),
            text = "Please enter your Email and Password.",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun LoginFailureMessage(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.testTag(LOGIN_HEADER_TITLE)
        )
    }
}

@Composable
private fun EmailTextField(
    modifier: Modifier = Modifier,
    uiState: LoginState,
    onAction: (LoginAction) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        leadingIcon = {
            Icon(
                modifier = modifier,
                imageVector = Icons.Rounded.Email,
                contentDescription = EMAIL_TEXT_FIELD_ICON_TAG,
                tint = MaterialTheme.colorScheme.primary,
            )
        },

        placeholder = {
            Text(
                modifier = modifier.alpha(0.5F),
                text = "Email address..",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = SairaFontFamily
            )
        },
        trailingIcon = {
            if (uiState.emailAddress.isNotEmpty()) {
                IconButton(
                    modifier = modifier.testTag(EMAIL_TEXT_FIELD_CLEAR_TAG),
                    onClick = {
                        onAction(LoginAction.EmailChanged(""))
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = EMAIL_TEXT_FIELD_CLEAR_TAG,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
        onValueChange = {
            if ("\n" !in it) onAction(LoginAction.EmailChanged(it))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onAction(LoginAction.MoveFocusToPassword)
                    true
                } else {
                    false
                }
            }
            .testTag(EMAIL_TEXT_FIELD_TAG),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = RoundedCornerShape(12.dp),
        value = uiState.emailAddress,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onAction(LoginAction.MoveFocusToPassword)
            },
        ),
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.primary
        ),
        maxLines = 1,
        singleLine = true,
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun PasswordTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    uiState: LoginState,
    onAction: (LoginAction) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        leadingIcon = {
            Icon(
                modifier = modifier,
                imageVector = Icons.Rounded.Lock,
                contentDescription = PASS_TEXT_FIELD_ICON_TAG,
                tint = MaterialTheme.colorScheme.primary,
            )
        },

        placeholder = {
            Text(
                modifier = modifier.alpha(0.5F),
                text = "Password..",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = SairaFontFamily
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (uiState.password.isNotEmpty()) {
                val icon = if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff

                Row {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    IconButton(
                        modifier = modifier.testTag(PASS_TEXT_FIELD_CLEAR_TAG),
                        onClick = {
                            onAction(LoginAction.PasswordChanged(""))
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = PASS_TEXT_FIELD_CLEAR_TAG,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        },
        onValueChange = {
            if ("\n" !in it) onAction(LoginAction.PasswordChanged(it))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    if (uiState.isEmailValid && uiState.isPasswordValid &&
                        !uiState.isLoading) {
                        onAction(LoginAction.SubmitLogin)
                    }
                    true
                } else {
                    false
                }
            }
            .testTag(PASS_TEXT_FIELD_TAG),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = RoundedCornerShape(12.dp),
        value = uiState.password,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (uiState.isEmailValid && uiState.isPasswordValid && !uiState.isLoading) {
                    onAction(LoginAction.SubmitLogin)
                }
            },
        ),
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontFamily = SairaFontFamily,
            color = MaterialTheme.colorScheme.primary
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
private fun TermAndConditions(
    modifier: Modifier = Modifier,
) {
    Text(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 12.sp,
        fontFamily = SairaFontFamily,
        text = buildAnnotatedString {
            append("By joining, you agree with  ")
            withStyle(
                style = SpanStyle(
                    if (isSystemInDarkTheme()) Color.White else Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    //textDecoration = TextDecoration.Underline
                )
            ) {
                append("Terms & Conditions")
            }
        },
        modifier = modifier
            .padding(horizontal = 20.dp)
            .clickable { /* TODO to be performed later */ }
            .testTag(LOGIN_TERM_AND_CONDITION_TAG)
    )
}

@Composable
private fun LoginButton(
    modifier: Modifier = Modifier,
    uiState: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .size(width = 160.dp, height = 45.dp)
                .clickable(
                    enabled = uiState.isEmailValid && uiState.isPasswordValid && !uiState.isLoading,
                    onClick = {
                        onAction(LoginAction.SubmitLogin)
                    })
                .alpha(if (uiState.isEmailValid && uiState.isPasswordValid) 1F else 0.5F)
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(12.dp),
                )
                .testTag(LOGIN_BUTTON_TAG),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(24.dp)
                        .testTag(LOGIN_BUTTON_PROGRESS_TAG),
                    color = MaterialTheme.colorScheme.surface,
                    strokeWidth = 3.dp
                )
            } else {
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 16.sp,
                    fontFamily = SairaFontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.testTag(LOGIN_BUTTON_TEXT_TAG)
                )
            }
        }
    }
}

@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(
                uiState = LoginState(
                    emailAddress = "email@test",
                    isEmailValid = true,
                    password = "123456",
                    isPasswordValid = true,
                ),
                onAction = {}
            )
        }
    }
}


@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Night",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenLoadingPreview() {
    SampleComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(
                uiState = LoginState(
                    emailAddress = "email@test",
                    isEmailValid = true,
                    password = "123456",
                    isPasswordValid = true,
                    isLoading = true
                ),
                onAction = {}
            )
        }
    }
}

internal const val LOGIN_HEADER_TITLE = "title"
internal const val LOGIN_DESCRIPTION_TAG = "loginDescriptionTag"
internal const val EMAIL_TEXT_FIELD_TAG = "emailTextField"
internal const val EMAIL_TEXT_FIELD_ICON_TAG = "emailTextFieldIcon"
internal const val EMAIL_TEXT_FIELD_CLEAR_TAG = "emailTextFieldClear"
internal const val PASS_TEXT_FIELD_ICON_TAG = "passTextFieldIcon"
internal const val PASS_TEXT_FIELD_CLEAR_TAG = "passTextFieldClear"
internal const val PASS_TEXT_FIELD_TAG = "passTextField"
internal const val LOGIN_BUTTON_TAG = "loginButton"
internal const val LOGIN_BUTTON_TEXT_TAG = "loginButtonText"
internal const val LOGIN_TERM_AND_CONDITION_TAG = "loginTerm&Condition"
internal const val LOGIN_BUTTON_PROGRESS_TAG = "loginButtonProgress"