package br.pro.juliacs.to_do.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import br.pro.juliacs.to_do.models.TaskData

import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class TaskRequest(context: Context) {
    private val queue = Volley.newRequestQueue(context)

    companion object {
        private const val URL = "http://0.0.0.0"
        private const val GET_TASKS = "/tasks"
    }

    fun startTasksRequest() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                tasksRequest()
                handler.postDelayed(this, 5000)
            }
        })
    }

    private fun tasksRequest() {
        val jsonRequest = JsonArrayRequest(
            Request.Method.GET,
            URL + GET_TASKS,
            null,
            { response ->
                val taskList = JSONArrayToTaskList(response)
                TasksSingleton.updateTaskList(taskList)
            },
            { error ->
                Log.e("TaskReqError", "Task request Error: $error")
            }
        )
        this.queue.add(jsonRequest)
    }

    private fun JSONArrayToTaskList(jsonArray: JSONArray): ArrayList<TaskData> {
        val taskList = ArrayList<TaskData>()

        for(i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val task = TaskData(
                jsonObject.getLong("id"),
                jsonObject.getString("description"),
                jsonObject.getBoolean("isUrgent"),
                jsonObject.getBoolean("isDone")
            )
            taskList.add(task)
        }
        return taskList
    }
}
