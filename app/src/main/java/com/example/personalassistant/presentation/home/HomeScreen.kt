package com.example.personalassistant.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.personalassistant.ui.theme.Shapes
import com.example.personalassistant.ui.theme.cardColor0
import com.example.personalassistant.ui.theme.cardColor1

@Composable
@Preview
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getBalanceCheck()
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colors.background)
        ) {
            Text(
                text = "INR ${uiState.balance}", fontSize = 32.sp,
                modifier = Modifier.padding(vertical = 16.dp),
                fontWeight = FontWeight.Bold
            )

            Row() {
                BalanceSheetCard("Income", uiState.income, cardColor0, Modifier.fillMaxWidth(0.5f))
                BalanceSheetCard("Expense", uiState.expense, cardColor1, Modifier.fillMaxWidth())
            }
        }
    }

}

@Composable
fun BalanceSheetCard(title: String, value: String, cardColor: Color, modifier: Modifier = Modifier) {
    Card(
        backgroundColor = cardColor,
        modifier = Modifier.padding(4.dp),
        shape = Shapes.large
    ) {
        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            Text(
                text = title, fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row {
                Text(text = "INR ", fontSize = 24.sp)
                Text(
                    text = value, fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}