package com.example.frigider.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.frigider.R
import com.example.frigider.viewModel.SendViewModel


class SendFragment : Fragment() {

    private lateinit var sendViewModel: SendViewModel
    private lateinit var contex:Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProviders.of(this).get(SendViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        contex = activity as AppCompatActivity

        //val textView: TextView = root.findViewById(R.id.text_send)
        sendViewModel.text.observe(this, Observer {
          //  textView.text = it
        })
        return root
    }

}