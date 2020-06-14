package com.example.frigider.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.frigider.R
import com.example.frigider.service.Adapter.SectionsPagerAdapter
import com.example.frigider.viewModel.ManageViewModel
import com.google.android.material.tabs.TabLayout


class ManageFragment : Fragment() {


    private lateinit var manageViewModel: ManageViewModel
    var intrat:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_manage, container, false)
        manageViewModel = ViewModelProvider(this).get(ManageViewModel::class.java)
        val context = activity as AppCompatActivity


        val sectionsPagerAdapter = SectionsPagerAdapter(context, fragmentManager)
        val viewPager: ViewPager = root.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        return root
    }

}