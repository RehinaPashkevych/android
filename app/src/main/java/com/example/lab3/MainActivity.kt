package com.example.lab3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.lab3.adapters.ShoppingListAdapter
import com.example.lab3.data.TaskItem
import com.example.lab3.data.Tasks
import com.example.lab3.databinding.ActivityMainBinding
import com.example.lab3.databinding.FragmentItemListBinding
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
    }

    /*override fun onResume() {
        super.onResume()
       // val tinyDB = SharedPreferencesService(applicationContext)
       // lateinit var binding: FragmentItemListBinding
       // listOfShoppingItem = tinyDB.getShoppingLists("shopping_list", Tasks::class.java)
      //  binding.list.adapter = ShoppingListAdapter(this, listOfShoppingItem)

    }


    private var listOfShoppingItem = ArrayList<Any>()


 private fun saveShoppingList(shoppingItems: ArrayList<Any>) {
     val tinyDB = SharedPreferencesService(applicationContext)
     tinyDB.saveShoppingLists("shopping_list", shoppingItems)
     listOfShoppingItem = tinyDB.getShoppingLists("shopping_list", Tasks::class.java)
     lateinit var binding: FragmentItemListBinding
     // binding.list.adapter = ShoppingListAdapter(this, listOfShoppingItem)

 }*/

 fun saveUserToFile(context: Context, task: Tasks) {
     val fullPath = "shoppingList.json"
     val file = File(context.filesDir, fullPath)
     val gson = Gson()
     val toJson = gson.toJson(task)
     val writer = FileWriter(file)
     writer.write(toJson)
     writer.close()
 }

 fun restoreUserFromFile(context: Context): Tasks? {
     val fullPath = "shoppingList.json"
     val file = File(context.filesDir, fullPath)
     val gson = Gson()
     val task = try {
         gson.fromJson(FileReader(file), Tasks::class.java)
     } catch (e: java.lang.Exception) {
         Log.e("restoreUser", "error", e)
         null
     }
     return task
 }


    fun deleteTaskFromFile(context: Context, taskId: String) {
        val fullPath = "shoppingList.json"
        val file = File(context.filesDir, fullPath)

        if (file.exists()) {
            val gson = Gson()
            val reader = FileReader(file)
            val taskData = gson.fromJson(reader, Array<TaskItem>::class.java).toMutableList()
            reader.close()

            // Find the index of the task with the specified ID
            val taskIndex = taskData.indexOfFirst { it.id == taskId }

            // Remove the task from the task data list
            if (taskIndex != -1) {
                taskData.removeAt(taskIndex)
            }

            // Save the updated task data back to the file
            val toJson = gson.toJson(taskData)
            val writer = FileWriter(file)
            writer.write(toJson)
            writer.close()
        }
    }
}