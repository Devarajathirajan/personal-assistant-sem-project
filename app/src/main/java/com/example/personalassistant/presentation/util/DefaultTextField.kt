package com.example.personalassistant.presentation.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.personalassistant.ui.theme.Shapes
import com.example.personalassistant.ui.theme.textFieldBackground

@Composable
fun DefaultTextField(
    label: String, value: String, onValueChange: (String)-> Unit,
    isError: Boolean, modifier: Modifier = Modifier, columnModifier: Modifier = Modifier,
    focusManager: FocusManager, imeAction: ImeAction = ImeAction.Next, enabled: Boolean = true
) {
    Column(modifier = columnModifier.padding(vertical = 8.dp)) {
        Text(text = label, modifier = Modifier.padding(vertical = 4.dp))
        TextField(
            value = value, onValueChange = onValueChange,
            isError = isError,
            modifier = modifier,
            shape = Shapes.large,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = textFieldBackground,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next)},
                onDone = {focusManager.clearFocus()}
            ),
            enabled = enabled
        )
    }
}