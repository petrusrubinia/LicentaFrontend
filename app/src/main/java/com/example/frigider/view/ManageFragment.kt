package com.example.frigider.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.frigider.R
import com.example.frigider.model.Product.CountCategory
import com.example.frigider.model.Product.Product
import com.example.frigider.repository.Adapter.ItemListAdapter
import com.example.frigider.repository.Adapter.SectionsPagerAdapter
import com.example.frigider.viewModel.AddViewModel
import com.example.frigider.viewModel.HomeViewModel
import com.example.frigider.viewModel.ManageViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_manage.*


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