package com.example.lab3.oneFiles.placeholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3.DeleteDialogFragment
import com.example.lab3.R
import com.example.lab3.ToDoListListener
import com.example.lab3.data.TaskItem
import com.example.lab3.databinding.FragmentItemListBinding
import com.example.lab3.oneFiles.MyItemRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class OneFragment : Fragment(), ToDoListListener,
    DeleteDialogFragment.OnDeleteDialogInteractionListener {

    private lateinit var binding: FragmentItemListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentItemListBinding.inflate(inflater, container, false)

     /*   val gson = Gson()
        val jsonFile = "shoppingList.json" // Replace with the actual file name/path
        val jsonString = context?.assets?.open(jsonFile)?.bufferedReader().use { it?.readText() }
        // Parse JSON into an array of TaskItem objects
        val taskItems: Array<OneItem> = gson.fromJson(jsonString, Array<TaskItem>::class.java)*/




        with(binding.list){
            layoutManager =LinearLayoutManager(context)
            adapter = MyItemRecyclerViewAdapter(Ones.ONES.toList(), this@OneFragment)
        }


        binding.addButon.setOnClickListener{addButtonClick()}
        return binding.root
    }


    private fun addButtonClick(){
        findNavController().navigate(R.id.action_oneFragment_to_addOne)
    }

    override fun onItemClick(position: Int) {
        val actionOneFragmentToDisplayOneFragment=
            OneFragmentDirections.actionOneFragmentToDisplayOne(Ones.ONES.get(position))
        findNavController().navigate(actionOneFragmentToDisplayOneFragment)
    }

    override fun onItemLongClick(position: Int) {
        showDeleteDialog(position)
    }

    private fun showDeleteDialog(position:Int){
        val deleteDialog= DeleteDialogFragment.newInstance(Ones.ONES.get(position).title, position,this)
            deleteDialog.show(requireActivity().supportFragmentManager, "DeleteDialog")
    }

    override fun onDialogPositiveClick(pos: Int?) {
        Ones.ONES.removeAt(pos!!)
        Snackbar.make(requireView(), "Task Deleted", Snackbar.LENGTH_LONG).show()
        notifyDataSetChanged()
    }

    override fun onDialogNegativeClick(pos: Int?) {
        Snackbar.make(requireView(), "Delete cancelled", Snackbar.LENGTH_LONG)
            .setAction("Redo", View.OnClickListener { showDeleteDialog(pos!!) })
            .show()
    }

    private fun notifyDataSetChanged(){
        val rvAdapter=binding.list.adapter
        rvAdapter?.notifyDataSetChanged()
    }



}