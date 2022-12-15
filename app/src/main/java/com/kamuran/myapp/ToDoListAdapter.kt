package com.kamuran.myapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoListAdapter(

    private val mContext: Context,
    private val mTodoClickListener: OnTodoClickListener,
    private val mTodoList: ArrayList<ToDoModel> = ArrayList()
) :
    RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder>() {
    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvtodoName: TextView = itemView.findViewById(R.id.textViewTasarim)
        val deletetodo: ImageView = itemView.findViewById(R.id.silTasarim)
        val updatetodo: ImageView = itemView.findViewById(R.id.duzenleTasarim)

    }

    interface OnTodoClickListener {
        fun onUpdate(position: Int, model: ToDoModel)
        fun onDelete(model: ToDoModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclertasarim, parent, false)
        val holder = ToDoViewHolder(view)


        holder.deletetodo.setOnClickListener {
            val position = holder.adapterPosition
            val model = mTodoList[position]
            mTodoClickListener.onDelete(model)
        }

        holder.updatetodo.setOnClickListener {
            // adapterPosition give the actual position of the item in the RecyclerView
            val position = holder.adapterPosition
            val model = mTodoList[position]
            mTodoClickListener.onUpdate(position, model)

        }


        return holder
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = mTodoList[position]
        holder.tvtodoName.text = todo.name

    }

    override fun getItemCount(): Int {
        return mTodoList.size
    }

    fun addTodo(model: ToDoModel) {
        mTodoList.add(model)
        // notifyDataSetChanged() // this method is costly I avoid it whenever possible
        notifyItemInserted(mTodoList.size)
    }

    fun removeTodo(model: ToDoModel) {
        val position = mTodoList.indexOf(model)
        mTodoList.remove(model)
        notifyItemRemoved(position)
    }

    fun getNextItemId(): Int {
        var id = 1
        if (mTodoList.isNotEmpty()) {
            // .last is equivalent to .size() - 1
            // we want to add 1 to that id and return it
            id = mTodoList.last().id + 1
        }
        return id
    }

    fun updateTodo(model: ToDoModel?) {

        if (model == null) return // we cannot update the value because it is null

        for (item in mTodoList) {
            // search by id
            if (item.id == model.id) {
                val position = mTodoList.indexOf(model)
                mTodoList[position] = model
                notifyItemChanged(position)
                break // we don't need to continue anymore
            }
        }


    }

}