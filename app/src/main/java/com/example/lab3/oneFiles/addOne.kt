package com.example.lab3.oneFiles

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab3.R
import com.example.lab3.databinding.FragmentAddOneBinding
import com.example.lab3.oneFiles.placeholder.BOUGHT
import com.example.lab3.oneFiles.placeholder.OneItem
import com.example.lab3.oneFiles.placeholder.Ones


class addOne : Fragment() {
    val args: addOneArgs by navArgs()
    private lateinit var binding: FragmentAddOneBinding

  //  val type = resources.getStringArray(R.array.type_one)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddOneBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener{saveTask()}
        binding.titleInput.setText(args.taskToEdit?.title)
        binding.amountOne.setText(args.taskToEdit?.amount)




               /* when(args.taskToEdit?.importance){
            IMPORTANCE.LOW->binding.importanceGroup.check(R.id.low_radioButton)
            IMPORTANCE.NORMAL->binding.importanceGroup.check(R.id.normal_radioButton)
            IMPORTANCE.HIGH->binding.importanceGroup.check(R.id.high_radioButton)
            null->binding.importanceGroup.check(R.id.normal_radioButton)
        }*/
    }

    private fun saveTask(){
        var title: String = binding.titleInput.text.toString()
        var amount: String = binding.amountOne.text.toString()
        var bought: BOUGHT =BOUGHT.NO

        val type: String = binding.typeOne.selectedItem.toString() //??????????????????????????????????????????????????????

        if(title.isEmpty()) title = getString(R.string.default_task_title) + "${Ones.ONES.size + 1}"
        if(amount.isEmpty()) amount = "1"



        val oneItem = OneItem(
            {title+amount}.hashCode().toString(),
            title,
            amount,
            type,
            bought
        )

        if(!args.edit){
            Ones.addTasks(oneItem)
        }
        else{
            Ones.updateTask(args.taskToEdit,oneItem)
        }



        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        findNavController().popBackStack(R.id.oneFragment,false)

    }

}