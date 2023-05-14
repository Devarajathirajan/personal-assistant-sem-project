package com.example.personalassistant.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.personalassistant.ui.theme.*

@Composable
@Preview
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(id = com.example.personalassistant.R.string.app_name),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f, false)
                )
                Text(
                    text = "INR ${uiState.balance}", fontSize = 24.sp,
                    modifier = Modifier
                        .weight(1f, false),
                    fontWeight = FontWeight.Bold
                )
            }
            Row() {
                BalanceSheetCard("Income", uiState.income, cardColor0, Modifier.fillMaxWidth(0.5f))
                BalanceSheetCard("Expense", uiState.expense, cardColor1, Modifier.fillMaxWidth())
            }
            Text(
                text = "Today", fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Bold
            )
            Row {
                TodayCard(
                    title = "Milestones",
                    value = "${uiState.today_milestones}",
                    cardColor = cardColor0,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                TodayCard(
                    title = "Completed",
                    value = "${uiState.completed_milestones}",
                    cardColor = cardColor3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row {
                TodayCard(
                    title = "Meetings",
                    value = "${uiState.today_meetings}",
                    cardColor = cardColor4,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                TodayCard(
                    title = "This Week",
                    value = "${uiState.this_week_meetings}",
                    cardColor = cardColor1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
fun BalanceSheetCard(
    title: String,
    value: String,
    cardColor: Color,
    modifier: Modifier = Modifier
) {
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

@Composable
fun TodayCard(
    title: String,
    value: String,
    cardColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = cardColor,
        modifier = Modifier.padding(4.dp),
        shape = Shapes.large
    ) {
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title, fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = value, fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}