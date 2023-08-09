package com.example.lab3.oneFiles.placeholder

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object Ones {

    var ONES: MutableList<OneItem> = ArrayList()

    private val COUNT = 0

    init {
        // Add some sample items.
        for (i in 0..COUNT) {
            addTasks(createPlaceholderItem(i))
        }
    }

     fun addTasks(item: OneItem) {
        ONES.add(item)
    }

    private fun createPlaceholderItem(position: Int): OneItem {
        return OneItem(position.toString(), "Item " + position," 0 " , "kg")
    }

    /*private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }*/

    fun updateTask(taskToEdit: OneItem?, newTask: OneItem){
        taskToEdit?.let{oldTask->
            val indexOfOldTask= ONES.indexOf(oldTask)
            ONES.add(indexOfOldTask, newTask)
            ONES.removeAt((indexOfOldTask+1))
        }
    }

}
data class OneItem(val id:String, val title: String, val amount: String, val type: String, var bought: BOUGHT=BOUGHT.NO): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        BOUGHT.values()[parcel.readInt()]
    ) {
    }

    override fun toString(): String = title

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(amount)
        parcel.writeString(type)
        parcel.writeInt(bought.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OneItem> {
        override fun createFromParcel(parcel: Parcel): OneItem {
            return OneItem(parcel)
        }

        override fun newArray(size: Int): Array<OneItem?> {
            return arrayOfNulls(size)
        }
    }
}
enum class BOUGHT{
   YES, NO
}