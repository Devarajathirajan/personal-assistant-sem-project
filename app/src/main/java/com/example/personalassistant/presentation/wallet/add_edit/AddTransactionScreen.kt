package com.example.personalassistant.presentation.wallet.add_edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.personalassistant.presentation.util.DefaultTextField
import com.example.personalassistant.ui.theme.cardColor0
import com.example.personalassistant.ui.theme.cardColor2
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val radioOptions = listOf("Income", "Expense")
    val dummyCategoryList = listOf(
        "Freelance", "Snacks", "Food", "Gift", "Fees", "Car", "Bike"
    )
    //Date and Time picker req
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val focusManager = LocalFocusManager.current

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val dayZero = if (selectedDayOfMonth < 10) "0" else ""
            val monthZero = if (selectedMonth + 1 < 10) "0" else ""
            viewModel.updateDate(
                "$dayZero$selectedDayOfMonth/$monthZero${selectedMonth + 1}/$selectedYear"
            )
        }, year, month, dayOfMonth
    )

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            val hourZero = if (selectedHour < 10) "0" else ""
            val minuteZero = if (selectedMinute < 10) "0" else ""
            viewModel.updateTime("$hourZero$selectedHour:$minuteZero$selectedMinute")
        }, hour, minute, false
    )



    LaunchedEffect(key1 = true) {
        viewModel.evenFlow.collectLatest { event ->
            when (event) {
                AddTransactionViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addTransaction() },
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
            }
        },
        modifier = Modifier.padding(horizontal = 16.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Add Transaction",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 32.dp)
            )
            //Title
            DefaultTextField(
                label = "Title",
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                isError = uiState.titleEmptyError,
                modifier = Modifier.fillMaxWidth(),
                focusManager = focusManager,
                imeAction = ImeAction.Next
            )
            //Income or Expense
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (radioOption in radioOptions) {
                    Row(
                        modifier = Modifier.selectable(
                            selected = (radioOption == uiState.type),
                            onClick = { viewModel.updateType(radioOption) }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (radioOption == uiState.type),
                            onClick = { viewModel.updateType(radioOption) }
                        )
                        Text(text = radioOption, modifier = Modifier.padding(start = 4.dp))
                    }
                }
            }
            //Date and Time
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DefaultTextField(
                    label = "Date",
                    value = uiState.date,
                    onValueChange = { viewModel.updateDate(it) },
                    isError = uiState.dateEmptyError,
                    modifier = Modifier.padding(end = 16.dp),
                    columnModifier = Modifier
                        .weight(1f, false)
                        .clickable { datePicker.show() },
                    focusManager = focusManager,
                    imeAction = ImeAction.Next,
                    enabled = false
                )
                DefaultTextField(
                    label = "Time",
                    value = uiState.time,
                    onValueChange = { viewModel.updateTime(it) },
                    isError = uiState.timeEmptyError,
                    modifier = Modifier.padding(end = 16.dp),
                    columnModifier = Modifier
                        .weight(1f, false)
                        .clickable { timePicker.show() },
                    focusManager = focusManager,
                    imeAction = ImeAction.Next,
                    enabled = false
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Category:", fontSize = 20.sp)
            }
            ChipCategories(
                categories = dummyCategoryList, onChipClicked = { viewModel.updateCategory(it) },
                selectedText = uiState.category
            )
            DefaultTextField(
                label = "Amount",
                value = uiState.amount,
                onValueChange = { viewModel.updateAmount(it) },
                isError = uiState.amountInvalidError,
                focusManager = focusManager,
                imeAction = ImeAction.Done
            )

        }
    }


}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun ChipCategories(
    categories: List<String>,
    onChipClicked: (String) -> Unit,
    selectedText: String
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEach { category ->
            val bgColor = if (selectedText == category) cardColor0 else cardColor2
            Chip(
                onClick = { onChipClicked(category) },
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                colors = ChipDefaults.chipColors(
                    backgroundColor = bgColor
                )
            ) {
                Text(text = category)
            }
        }
    }
}