package com.example.lab3

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab3.data.IMPORTANCE
import com.example.lab3.data.TaskItem
import com.example.lab3.data.Tasks
import com.example.lab3.databinding.FragmentAddTaskBinding
import com.example.lab3.databinding.FragmentItemListBinding
import com.example.lab3.oneFiles.placeholder.OneItem
import com.google.gson.Gson
import java.io.File

/**
 * A simple [Fragment] subclass.
 * Use the [AddTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTaskFragment : Fragment() {
     val args: AddTaskFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddTaskBinding
    val subList = arrayListOf<OneItem>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener{saveTask()}
        binding.titleInput.setText(args.taskToEdit?.title)
      //  binding.descriptionInput.setText(args.taskToEdit?.description)
        when(args.taskToEdit?.importance){
            IMPORTANCE.LOW->binding.importanceGroup.check(R.id.low_radioButton)
            IMPORTANCE.NORMAL->binding.importanceGroup.check(R.id.normal_radioButton)
            IMPORTANCE.HIGH->binding.importanceGroup.check(R.id.high_radioButton)
            null->binding.importanceGroup.check(R.id.normal_radioButton)
        }
    }
    private fun saveTask(){
        var title: String = binding.titleInput.text.toString()
       // var description: String = binding.descriptionInput.text.toString()
        val importance = when(binding.importanceGroup.checkedRadioButtonId){
            R.id.low_radioButton -> IMPORTANCE.LOW
            R.id.normal_radioButton -> IMPORTANCE.NORMAL
            R.id.high_radioButton -> IMPORTANCE.HIGH
            else -> IMPORTANCE.NORMAL

        }

        if(title.isEmpty()) title = getString(R.string.default_task_title) + "${Tasks.ITEMS.size + 1}"
       // if(description.isEmpty()) description = getString(R.string.no_desc_msg)

        val taskItem = TaskItem(
            {title}.hashCode().toString(),
            title,
            importance,
            subList
        )

        if(!args.edit){
            Tasks.addTask(taskItem)
        }
        else{
            Tasks.updateTask(args.taskToEdit,taskItem)
        }

        saveDataToJson(requireContext(), taskItem)



        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        findNavController().popBackStack(R.id.taskFragment,false)

    }

    fun saveDataToJson(context: Context, taskItem: TaskItem) {
        try {
            val fileName = "shoppingList.json"
            val gson = Gson()
            val jsonString = gson.toJson(taskItem)
            val file = File(context.filesDir, fileName)
            file.writeText(jsonString)
        } catch (e: Exception) {
            Log.e("SaveData", "Error writing JSON file", e)
        }
    }



    }
