package com.example.lab3

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.lab3.data.IMPORTANCE
import com.example.lab3.data.TaskItem
import com.example.lab3.databinding.FragmentItemBinding

class MyTaskRecyclerViewAdapter(
    private val values: List<TaskItem>,
    private val eventListener: ToDoListListener
) : RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        //val subListAdapter = MyItemRecyclerViewAdapter(item.subList.toList(), eventListener)
        //holder.itemContainer.adapter = subListAdapter

        val resource = when(item.importance){
            IMPORTANCE.LOW -> R.drawable.circle_drawable_green
            IMPORTANCE.NORMAL -> R.drawable.circle_drawable_orange
            IMPORTANCE.HIGH -> R.drawable.circle_drawable_red
          //  else -> R.drawable.circle_drawable_green
        }


        holder.imgView.setImageResource(resource)
        holder.contentView.text = item.title

        holder.itemContainer.setOnClickListener{
            eventListener.onItemClick(position)
        }
        holder.itemContainer.setOnLongClickListener{
            eventListener.onItemLongClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemContainer: View = binding.root
        val imgView: ImageView = binding.itemImg
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}