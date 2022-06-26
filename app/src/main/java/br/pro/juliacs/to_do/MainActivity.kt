package br.pro.juliacs.to_do

import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import br.pro.juliacs.to_do.data.TaskRequest
import br.pro.juliacs.to_do.ui.screens.MainScreenView
import br.pro.juliacs.to_do.ui.theme.ToDoAppTheme


class MainActivity : ComponentActivity() {
    private lateinit var taskRequest: TaskRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.taskRequest = TaskRequest(this)
        this.taskRequest.startTasksRequest()
        setContent {
            ToDoAppTheme {
                this.SetupUIConfigs()
                MainScreenView(taskRequest)
            }
        }
    }

    @Composable
    private fun SetupUIConfigs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (MaterialTheme.colors.isLight) {
                window.decorView
                    .windowInsetsController?.setSystemBarsAppearance(
                        APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS
                    )
            } else {
                window.decorView
                    .windowInsetsController?.setSystemBarsAppearance(
                        0, APPEARANCE_LIGHT_STATUS_BARS
                    )
            }
        }
        window.statusBarColor = MaterialTheme.colors.primaryVariant.toArgb()
    }
}
