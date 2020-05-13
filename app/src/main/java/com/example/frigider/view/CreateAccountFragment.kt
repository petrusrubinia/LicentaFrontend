package com.example.frigider.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.frigider.R
import com.example.frigider.viewModel.AddViewModel


class CreateAccountFragment : Fragment() {


    private lateinit var addViewModel: AddViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_create_account, container, false)

        // Get the activity and widget
        val context = activity as AppCompatActivity
        println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa intraaaaaaaaaaattttttttttt baieeeeeeeeetti")
        // val textView: TextView = root.findViewById(R.id.text_home)
        //homeViewModel.text.observe(this, Observer {
        //  textView.text = it
        //})



        return root
    }

}