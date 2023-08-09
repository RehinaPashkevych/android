package com.example.lab3

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab3.data.TaskItem
import com.example.lab3.data.Tasks
import com.example.lab3.data.Tasks.restoreUserFromFile
import com.example.lab3.databinding.ActivityMainBinding
import com.example.lab3.databinding.FragmentItemBinding
import com.example.lab3.databinding.FragmentItemListBinding
import com.example.lab3.oneFiles.placeholder.Ones
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.ArrayList

class TaskFragment : Fragment(), ToDoListListener,
    DeleteDialogFragment.OnDeleteDialogInteractionListener {
    private lateinit var binding: FragmentItemListBinding

 /*   private val gson = Gson()
    private val jsonFile = "shoppingList.json" // Replace with the actual file name/path
    private val jsonString = context?.assets?.open(jsonFile)?.bufferedReader().use { it?.readText() }
    // Parse JSON into an array of TaskItem objects
    private val taskItems: Array<TaskItem> = gson.fromJson(jsonString, Array<TaskItem>::class.java)
*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentItemListBinding.inflate(inflater, container, false)


        val gson = Gson()
        val jsonFile = "shoppingList.json" // Replace with the actual file name/path
        val jsonString = context?.assets?.open(jsonFile)?.bufferedReader().use { it?.readText() }
        // Parse JSON into an array of TaskItem objects
        val taskItems: Array<TaskItem> = gson.fromJson(jsonString, Array<TaskItem>::class.java)



        with(binding.list){
            layoutManager =LinearLayoutManager(context)

           // adapter = MyTaskRecyclerViewAdapter(taskItems.toList(), this@TaskFragment)
            adapter = MyTaskRecyclerViewAdapter(Tasks.ITEMS, this@TaskFragment)
        }





        binding.addButon.setOnClickListener{addButtonClick()}
        return binding.root
    }


  /*  override fun onResume() {
        super.onResume()
        val updatedData: MutableList<TaskItem>? = restoreUserFromFile(requireContext())

        // Update the data source (e.g., JSON file) if necessary

        // Update the main activity and refresh the RecyclerView adapter
        if (updatedData != null) {
            // Assuming you have a reference to the RecyclerView adapter
            val adapter = MyTaskRecyclerViewAdapter(updatedData, this@TaskFragment)
            binding.list.adapter = adapter

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged()
        }
    }*/

    override fun onItemLongClick(position: Int) {
        showDeleteDialog(position)
    }

    private fun showDeleteDialog(position:Int){
        //Tasks.ITEMS=taskItems.toMutableList()
        val deleteDialog=DeleteDialogFragment.newInstance(Tasks.ITEMS.get(position).title, position,this)
        deleteDialog.show(requireActivity().supportFragmentManager, "DeleteDialog")
    }


    override fun onItemClick(position: Int) {
    //    val gson = Gson()
      //  val jsonFile = "shoppingList.json" // Replace with the actual file name/path
        //val jsonString = context?.assets?.open(jsonFile)?.bufferedReader().use { it?.readText() }
        // Parse JSON into an array of TaskItem objects
        //val taskItems: Array<TaskItem> = gson.fromJson(jsonString, Array<TaskItem>::class.java)

      //  Tasks.ITEMS=taskItems.toMutableList()
        val actionTaskFragmentToDisplayTaskFragment=
            TaskFragmentDirections.actionTaskFragmentToDisplayTaskFragment(Tasks.ITEMS.get(position))
        Ones.ONES=Tasks.ITEMS.get(position).subList
        findNavController().navigate(actionTaskFragmentToDisplayTaskFragment)
    }

    private fun addButtonClick(){
        findNavController().navigate(R.id.action_taskFragment_to_addTaskFragment)
    }

    override fun onDialogPositiveClick(pos: Int?) {
       // Tasks.ITEMS=taskItems.toMutableList()
        Tasks.ITEMS.removeAt(pos!!)
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


    /*private fun getJSONFromAssets(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = assets.open("shoppingList.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }*/

}