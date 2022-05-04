package br.pro.juliacs.to_do.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pro.juliacs.to_do.R
import br.pro.juliacs.to_do.models.TaskData

class TaskDataAdapter(
    private var tasksList: ArrayList<TaskData>
) : RecyclerView.Adapter<TaskDataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutRes = R.layout.task
        val itemView = layoutInflater.inflate(layoutRes, parent, false)
        return TaskDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskDataViewHolder, position: Int) {
        holder.bind(this.tasksList[position])
    }

    override fun getItemCount(): Int {
        return this.tasksList.size
    }
}