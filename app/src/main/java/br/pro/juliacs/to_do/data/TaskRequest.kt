package br.pro.juliacs.to_do.data


import br.pro.juliacs.to_do.models.CallBack
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import br.pro.juliacs.to_do.models.TaskData
import com.android.volley.Request
import com.android.volley.toolbox.*
import org.json.JSONArray
import org.json.JSONObject

class TaskRequest(context: Context) {
    private val queue = Volley.newRequestQueue(context)

    companion object {
        private const val URL = "http://10.0.2.2:8000"
        private const val GET_TASKS = "/tasks/"
        private const val CREATE_TASK = "/tasks/new"
        private const val UPDATE_TASK = "/tasks/done"
    }


    fun startTasksRequest() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                tasksRequest(null)
                handler.postDelayed(this, 5000)
            }
        })
    }


    fun createNewTask(taskData: TaskData, callback: CallBack) {
        Log.d("ReqCreateStatus", "Starting")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            URL + CREATE_TASK,
            this.taskToJSONObject(taskData),
            { response ->
                Log.d("ReqCreateRes", response.toString())
                this.tasksRequest(callback)
            },
            { error ->
                Log.e("ReqCreateError", "Connection error. ${error.toString()}")
            }
        )
        this.queue.add(jsonObjectRequest)
    }


    fun updateTaskData(taskData: TaskData) {
        Log.d("ReqUpdateStatus", "Starting")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT,
            URL + UPDATE_TASK + "/${taskData.isDone}/${taskData.id}",
            this.taskToJSONObject(taskData),
            { response ->
                Log.d("ReqUpdateRes", response.toString())
                this.tasksRequest(null)
            },
            { error ->
                Log.e("ReqUpdateError", "Connection error. ${error.toString()}")
            }
        )
        this.queue.add(jsonObjectRequest)
    }


    private fun tasksRequest(callback: CallBack?) {
        val jsonRequest = JsonArrayRequest(
            Request.Method.GET,
            URL + GET_TASKS,
            null,
            { response ->
                val taskList = jsonArrayToTaskList(response)
                TasksSingleton.updateTaskList(taskList)
                callback?.onSuccess()
            },
            { error ->
                Log.e("TaskReqError", "Task request Error: $error")
            }
        )
        this.queue.add(jsonRequest)
    }


    private fun jsonArrayToTaskList(jsonArray: JSONArray): ArrayList<TaskData> {
        val taskList = ArrayList<TaskData>()

        for(i in 0..(jsonArray.length() - 1)) {
            val jsonObject = jsonArray.getJSONObject(i)
            val task = TaskData(
                jsonObject.getLong("id"),
                jsonObject.getString("description"),
                jsonObject.getBoolean("is_urgent"),
                jsonObject.getBoolean("is_done")
            )
            taskList.add(task)
        }
        return taskList
    }


    private fun taskToJSONObject(taskData: TaskData): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("description", taskData.description)
        jsonObject.put("is_urgent", taskData.isUrgent)
        jsonObject.put("is_done", taskData.isDone)

        return jsonObject
    }
}
