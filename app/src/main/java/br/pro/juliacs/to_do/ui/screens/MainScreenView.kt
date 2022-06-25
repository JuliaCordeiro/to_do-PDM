package br.pro.juliacs.to_do.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.pro.juliacs.to_do.data.TasksSingleton
import br.pro.juliacs.to_do.ui.components.CardView
import br.pro.juliacs.to_do.ui.theme.ToDoAppTheme

@Composable
fun MainScreenView() {
    Scaffold() {
        LazyColumn {
            items(TasksSingleton.getTasks()) { task ->
                CardView(taskData = task)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreenViewDark() {
    ToDoAppTheme(darkTheme = true) {
        MainScreenView()
    }
}

@Preview
@Composable
fun PreviewMainScreenView() {
    ToDoAppTheme(darkTheme = false) {
        MainScreenView()
    }
}
