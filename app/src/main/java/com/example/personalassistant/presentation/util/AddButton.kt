package com.example.personalassistant.presentation.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun AddButton (title: String = "Add",  onClick: ()-> Unit = {}) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colors.surface
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Button")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, color = Color.White, fontSize = 20.sp)
    }
}