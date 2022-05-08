package br.pro.juliacs.to_do.ui.list

import android.view.View
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.pro.juliacs.to_do.R
import br.pro.juliacs.to_do.models.TaskData

class TaskDataViewHolder(
    itemView: View,
    private val taskDataAdapter: TaskDataAdapter
) : RecyclerView.ViewHolder(itemView) {
    private val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
    private val tagUrgent: FrameLayout = itemView.findViewById(R.id.tagUrgent)
    private val cbxDone: CheckBox = itemView.findViewById(R.id.cbxDone)
    private lateinit var currentTaskData: TaskData

    init {
        this.cbxDone.setOnCheckedChangeListener { _, value ->
            this.taskDataAdapter.getOnIsDoneListener()?.onIsDone(this.currentTaskData, value)
        }
    }

    fun bind(taskData: TaskData) {
        this.currentTaskData = taskData
        if(this.currentTaskData.isUrgent) {
            this.tagUrgent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red))
        } else {
            this.tagUrgent.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.cian))
        }
        this.txtDescription.text = this.currentTaskData.description
        this.cbxDone.isChecked = this.currentTaskData.isDone

    }
}