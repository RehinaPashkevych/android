package com.example.lab3.oneFiles
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.lab3.R
import com.example.lab3.ToDoListListener
import com.example.lab3.databinding.FragmentOneBinding
import com.example.lab3.oneFiles.placeholder.BOUGHT
import com.example.lab3.oneFiles.placeholder.OneItem


class MyItemRecyclerViewAdapter(
    private val values: List<OneItem>,
    private val eventListener: ToDoListListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentOneBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.title

        val resource=when(item.bought){
            BOUGHT.NO-> R.drawable.unchecked
           BOUGHT.YES->R.drawable.checked
        }

        holder.amountView.text = item.amount
        holder.typeView.text=item.type
        holder.imgView.setImageResource(resource)

        holder.itemContainer.setOnClickListener{
            eventListener.onItemClick(position)
        }
        holder.itemContainer.setOnLongClickListener{
            eventListener.onItemLongClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentOneBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content
        val amountView: TextView = binding.numOne
        val typeView: TextView = binding.typeOfOne
        val imgView: ImageButton =binding.imgBought
        val itemContainer: View =binding.root


        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}