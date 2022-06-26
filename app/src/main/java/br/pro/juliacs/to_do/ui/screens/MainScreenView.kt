package br.pro.juliacs.to_do.ui.screens

import br.pro.juliacs.to_do.models.CallBack
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.pro.juliacs.to_do.data.*
import br.pro.juliacs.to_do.models.TaskData
import br.pro.juliacs.to_do.ui.components.CardView
import br.pro.juliacs.to_do.ui.theme.ToDoAppTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreenView( taskRequest: TaskRequest ) {
    var newTaskDescription = remember { mutableStateOf(TextFieldValue("")) }
    var isUrgent = remember { mutableStateOf(false) }
    val stateTasksList = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 5.dp)
                ) {
                    Text(
                        "Is Urgent?",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 10.dp, end = 5.dp)
                    )
                    Switch(
                        checked = isUrgent.value,
                        onCheckedChange = {
                            isUrgent.value = !isUrgent.value
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    TextField(
                        value = newTaskDescription.value,
                        onValueChange = { newDescription -> newTaskDescription.value = newDescription},
                        placeholder = { Text( "Type the task description ") },
                        modifier = Modifier.padding(10.dp)
                    )
                    Button(
                        onClick = {
                            Log.d("Button", "click")
                            taskRequest.createNewTask(
                                TaskData(
                                    0,
                                    newTaskDescription.value.text,
                                    isUrgent.value
                                ),
                                object : CallBack {
                                    override fun onSuccess() {
                                        coroutineScope.launch {
                                            stateTasksList.animateScrollToItem(TasksSingleton.getTasks().size)
                                        }
                                    }
                                }
                            )
                            newTaskDescription.value = TextFieldValue("")
                        },
                        Modifier
                            .align(Alignment.CenterVertically)
                            .width(80.dp)
                            .padding(10.dp)
                    ) {
                        Text( "OK" )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                LazyColumn (state = stateTasksList) {
                    items(TasksSingleton.getTasks()) { task ->
                        CardView(taskData = task)
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewMainScreenViewDark() {
//    ToDoAppTheme(darkTheme = true) {
//        MainScreenView()
//    }
//}
//
//@Preview
//@Composable
//fun PreviewMainScreenView() {
//    ToDoAppTheme(darkTheme = false) {
//        MainScreenView()
//    }
//}
