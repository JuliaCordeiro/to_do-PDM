package br.pro.juliacs.to_do

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.pro.juliacs.to_do.models.TaskData
import br.pro.juliacs.to_do.ui.list.TaskDataAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var etxtDescription: EditText
    private lateinit var tasksList: ArrayList<TaskData>
    private lateinit var rvTaskData: RecyclerView
    private lateinit var taskDataAdapter: TaskDataAdapter
    private lateinit var swtUrgent: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.etxtDescription = findViewById(R.id.etxtDescription)
        this.rvTaskData = findViewById(R.id.rvTaskData)
        this.swtUrgent = findViewById(R.id.swtUrgent)
        if(savedInstanceState == null)
            this.tasksList = ArrayList()
        else if(savedInstanceState.containsKey("RECOVER_TASK")) {
            this.tasksList =
                savedInstanceState.getParcelableArrayList<TaskData>("RECOVER_TASK")
                as ArrayList<TaskData>
        }
        this.taskDataAdapter = TaskDataAdapter(this.tasksList)
        this.taskDataAdapter.setOnDoneCheckListener { task ->
            Toast.makeText(this, task.isDone.toString(), Toast.LENGTH_SHORT).show()
        }
        this.rvTaskData.layoutManager = LinearLayoutManager(this)
        this.rvTaskData.adapter = this.taskDataAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("RECOVER_TASK", this.tasksList)
    }

    fun onClickSend(v: View) {
        val taskText = this.etxtDescription.text.toString()
        if(taskText.isNotBlank()) {
            val task = TaskData(
                taskText,
                this.swtUrgent.isChecked,
                false
                )
            this.tasksList.add(task)
            this.taskDataAdapter.notifyItemInserted(this.tasksList.size-1)
            this.rvTaskData.scrollToPosition(this.tasksList.size-1)
            this.swtUrgent.isChecked = false
            this.etxtDescription.text.clear()

        }
    }

}