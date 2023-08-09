package com.example.lab3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.data.TaskItem
import com.example.lab3.databinding.FragmentOneBinding
import com.example.lab3.oneFiles.MyItemRecyclerViewAdapter

class ShoppingListAdapter(
    var context: Context,
    var shoppingListArray: ArrayList<Any>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private fun getCount(): Int {
        return shoppingListArray.size
    }

    private fun getItem(p0: Int): TaskItem {
        return shoppingListArray[p0] as TaskItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyItemRecyclerViewAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyItemRecyclerViewAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getItemCount(): Int {
        return shoppingListArray.size
    }

    private fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = FragmentOneBinding.inflate(layoutInflater)
        val shoppingItemNameView = binding.content

        shoppingItemNameView.text = getItem(p0).title

        return binding.root
    }
}