package br.pro.juliacs.to_do.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.pro.juliacs.to_do.data.TaskRequest
import br.pro.juliacs.to_do.models.TaskData
import br.pro.juliacs.to_do.ui.theme.*

@Composable
fun CardView(taskData: TaskData, taskRequest: TaskRequest?) {
    var done by remember {
        mutableStateOf(false)
    }
    done = taskData.isDone

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row( modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min) ) {
            TaskUrgentFlag(
                taskData)
            TaskInfo(
                taskData,
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically))
            Checkbox(
                checked = done,
                onCheckedChange = {
                    done = !done
                    taskData.isDone = done
                    taskRequest?.updateTaskData(taskData)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 10.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = lightGreen
                )
            )
        }
    }
}

@Composable
fun TaskUrgentFlag(taskData: TaskData, modifier: Modifier = Modifier) {
    val flagColor = if (taskData.isUrgent) urgentRed else normalCian
    Column (
        Modifier.padding(end = 2.dp)
    ) {
        Box(
            Modifier
                .align(Alignment.Start)
                .width(15.dp)
                .background(flagColor)
                .fillMaxHeight())
    }

}

@Composable
fun TaskInfo(taskData: TaskData, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.padding(vertical = 10.dp, horizontal = 5.dp)
    ) {
        Text(
            text = taskData.description,
            style = Typography.body1
        )
    }
}

@Preview
@Composable
fun PreviewTaskView() {
    val task = TaskData(
        1,
        "Test card development and values for modifier of which card element",
        false,
        false
    )
    ToDoAppTheme(darkTheme = false) {
        CardView(task, null)
    }
}