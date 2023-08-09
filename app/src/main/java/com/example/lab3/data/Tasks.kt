package com.example.lab3.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.lab3.TaskFragment
import com.example.lab3.databinding.FragmentItemListBinding
import com.example.lab3.oneFiles.placeholder.OneItem
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.io.File
import java.io.FileReader
import java.util.ArrayList

object Tasks {

    private lateinit var binding: FragmentItemListBinding

    var ITEMS: MutableList<TaskItem> = ArrayList()
    //val itemList: MutableList<TaskItem>? = restoreUserFromFile(context)

    private val COUNT = 0

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addTask(createPlaceholderItem(i))
        }
    }

    fun addTask(item: TaskItem) {
        ITEMS.add(item)
    }

    fun parseJsonFile(context: Context): List<TaskItem> {
        val gson = Gson()
        val jsonFile = "shoppingList.json" // Replace with the actual file name/path
        val jsonString = context.assets.open(jsonFile).bufferedReader().use { it.readText() }
        // Parse JSON into an array of TaskItem objects
        return gson.fromJson(jsonString, Array<TaskItem>::class.java).toList()
    }

    private fun createPlaceholderItem(position: Int): TaskItem {
        return TaskItem(position.toString(), "Item " + position, IMPORTANCE.NORMAL, arrayListOf<OneItem>())
    }

    fun restoreUserFromFile(context: Context): MutableList<TaskItem>? {
        val ITEMS: MutableList<TaskItem> = ArrayList()
        val fullPath = "shoppingList.json"
        val file = File(context.filesDir, fullPath)
        val gson = Gson()
        val jsonString = try {
            file.readText()
        } catch (e: Exception) {
            Log.e("restoreUser", "error", e)
            return null
        }

        val jsonArray = JsonParser.parseString(jsonString).asJsonArray
        jsonArray.forEach { jsonElement ->
            val taskItem = gson.fromJson(jsonElement, TaskItem::class.java)
            ITEMS.add(taskItem)
        }
        return ITEMS
    }

    fun updateTask(taskToEdit: TaskItem?, newTask: TaskItem){
        taskToEdit?.let{oldTask->
            val indexOfOldTask= ITEMS.indexOf(oldTask)
            ITEMS.add(indexOfOldTask, newTask)
            ITEMS.removeAt((indexOfOldTask+1))

        }
    }

}

data class TaskItem(val id: String, val title: String, val importance: IMPORTANCE = IMPORTANCE.NORMAL, var subList: ArrayList<OneItem>):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        IMPORTANCE.values()[parcel.readInt()],
        parcel.createTypedArrayList(OneItem.CREATOR)!!
    ) {
    }

    override fun toString(): String = title
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(importance.ordinal)
        parcel.writeList(subList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskItem> {
        override fun createFromParcel(parcel: Parcel): TaskItem {
            return TaskItem(parcel)
        }

        override fun newArray(size: Int): Array<TaskItem?> {
            return arrayOfNulls(size)
        }
    }
}

enum class IMPORTANCE{
    LOW, NORMAL, HIGH
}