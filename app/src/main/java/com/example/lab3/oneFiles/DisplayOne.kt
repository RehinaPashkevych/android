package com.example.lab3.oneFiles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab3.R
import com.example.lab3.databinding.FragmentDisplayOneBinding
import com.example.lab3.oneFiles.placeholder.BOUGHT
import com.example.lab3.oneFiles.placeholder.OneItem

class DisplayOne : Fragment() {

    lateinit var binding: FragmentDisplayOneBinding
    val args: DisplayOneArgs by navArgs()
    private var isChecked: Boolean = false
   // private lateinit var imageButton: ImageButton




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDisplayOneBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val one: OneItem =args.task
        binding.DisplayTitle.text=one.title
        binding.DisplayAmount.text=one.amount
        binding.DisplayType.text=one.type


        val resource=when(one.bought){
            BOUGHT.NO->R.drawable.unchecked
            BOUGHT.YES->R.drawable.checked
        }
        binding.DisplayBought.setImageResource(resource)

        binding.DisplayBought.setOnClickListener{changeBought(binding.DisplayBought, one)}

        binding.displayEditOne.setOnClickListener{
            val taskToEdit=
                DisplayOneDirections.actionDisplayOneToAddOne(
                    taskToEdit = one,
                    edit = true)
            findNavController().navigate(taskToEdit)
        }
    }


    private fun changeBought(imageButton: ImageButton, item: OneItem){
        item.bought = if (item.bought == BOUGHT.YES) BOUGHT.NO else BOUGHT.YES
        isChecked = !isChecked
        val imageResource = when (item.bought) {
            BOUGHT.YES -> R.drawable.checked
            BOUGHT.NO -> R.drawable.unchecked
        }
        imageButton.setImageResource(imageResource)
    }




}