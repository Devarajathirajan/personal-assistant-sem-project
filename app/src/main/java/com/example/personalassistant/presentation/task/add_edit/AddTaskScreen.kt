package com.example.personalassistant.presentation.task.add_edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
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
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                UiEvent.SaveTask -> navController.navigateUp()
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddTaskEvent.SaveTask) },
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
                text = "Add Task",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 32.dp)
            )
            DefaultTextField(
                label = "Title",
                value = uiState.value.title,
                onValueChange = { viewModel.updateTitle(it) },
                isError = uiState.value.titleError,
                focusManager = focusManager,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically ,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Text(
                    text = "MileStones",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f, false)
                )
                Button(
                    onClick = { viewModel.onEvent(AddTaskEvent.AddNewMileStone) },
                    modifier = Modifier.weight(1f, false)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Milestone")
                }
            }
            for (i in 0 until uiState.value.numberOfSubTasks) {
                MileStoneItem(focusManager, i) {
                    viewModel.updateMileStoneItem(i, it)
                }
            }
        }
    }
}

@Composable
fun MileStoneItem(fm: FocusManager, index:Int = 0, onUpdateMileStone: (MileStoneItemState) -> Unit) {
    //Date and Time picker req
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]



    var nameField by remember {
        mutableStateOf("Default")
    }
    var dateField by remember {
        mutableStateOf("")
    }
    var timeField by remember {
        mutableStateOf("20:00")
    }

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val dayZero = if (selectedDayOfMonth < 10) "0" else ""
            val monthZero = if (selectedMonth + 1 < 10) "0" else ""
            dateField = "$dayZero$selectedDayOfMonth/$monthZero${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            val hourZero = if (selectedHour < 10) "0" else ""
            val minuteZero = if (selectedMinute < 10) "0" else ""
            timeField = "$hourZero$selectedHour:$minuteZero$selectedMinute"
        }, hour, minute, false
    )
    Column(modifier = Modifier.padding(top = 16.dp)) {
        DefaultTextField(
            label = "${index+1}) Name*", value = nameField,
            onValueChange = { nameField = it },
            isError = nameField.isEmpty(), focusManager = fm,
            modifier = Modifier.fillMaxWidth(),
            imeAction = ImeAction.Done
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DefaultTextField(
                label = "Date",
                value = dateField,
                onValueChange = {},
                isError = false,
                modifier = Modifier.padding(end = 16.dp),
                columnModifier = Modifier
                    .weight(1f, false)
                    .clickable { datePicker.show() },
                focusManager = fm,
                imeAction = ImeAction.Next,
                enabled = false
            )
            DefaultTextField(
                label = "Time",
                value = timeField,
                onValueChange = {},
                isError = false,
                modifier = Modifier.padding(end = 16.dp),
                columnModifier = Modifier
                    .weight(1f, false)
                    .clickable { timePicker.show() },
                focusManager = fm,
                imeAction = ImeAction.Next,
                enabled = false
            )

        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colors.surface)
        )
    }
    onUpdateMileStone(
        MileStoneItemState(
            nameField, nameField.isEmpty(), dateField, timeField
        )
    )
}