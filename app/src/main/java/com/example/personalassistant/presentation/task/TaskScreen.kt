package com.example.personalassistant.presentation.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.personalassistant.domain.task.Task
import com.example.personalassistant.domain.task.getCompletion
import com.example.personalassistant.presentation.util.AddButton
import com.example.personalassistant.ui.theme.Shapes
import com.example.personalassistant.ui.theme.taskCardColors

@Composable
@Preview
fun TaskScreen(
    onAddTaskAction: () -> Unit = {},
    viewModel: TaskViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = { AddButton(onClick = onAddTaskAction) },
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item{
                Text(
                    text = "Task Screen",
                    fontSize = 32.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
            }
            itemsIndexed(uiState.value.taskList) { index, item ->
                TaskListItem(task = item, i = (index % 3), uiState.value.openedCard, {
                    viewModel.updateOpenedCardState(index)
                }) { isChecked, mIndex ->
                    viewModel.updateMileStone(isChecked, mIndex, index, item)
                }
            }
        }
    }

}

@Composable
fun TaskListItem(task: Task, i: Int, openedIndex: Int, onCardClicked: ()->Unit, onCheckChange: (Boolean, Int) -> Unit) {

    Box(modifier = Modifier.padding(vertical = 16.dp)) {
        Card(
            backgroundColor = taskCardColors[i],
            shape = Shapes.large,
            modifier = Modifier
                .clickable { onCardClicked() }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = task.title, fontSize = 20.sp, modifier = Modifier.weight(1f, false))
                    Text(
                        text = "${task.getCompletion()}%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f, false)
                    )
                }
                AnimatedVisibility(
                    visible = i == openedIndex,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        task.mileStones.forEachIndexed { index, milestone ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 8.dp, start = 8.dp,
                                        end = 8.dp
                                    )
                                    .clickable { onCheckChange(!milestone.isCompleted, index) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (milestone.isCompleted) {
                                    Checkbox(checked = true, onCheckedChange = {
                                        onCheckChange(it, index)
                                    })
                                    Text(
                                        text = milestone.name,
                                        style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                    )
                                } else {
                                    Checkbox(checked = false, onCheckedChange = {
                                        onCheckChange(it, index)
                                    })
                                    Text(text = milestone.name)
                                }
                                milestone.deadLine?.let {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "(${it.substringBefore("T")})")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}