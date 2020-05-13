package com.example.frigider.view

import android.content.ClipData
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frigider.R
import com.example.frigider.model.Product.CountCategory
import com.example.frigider.viewModel.HomeViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val context = activity as AppCompatActivity


        var countCategory=  mutableListOf<CountCategory>()
        homeViewModel.liveData.observe(this, Observer {
            homeViewModel.getListOfCategory()
            if (it != null) {
                countCategory = it
            }

            setPieChart(root,countCategory)
        })
        val add_button: CardView  = root.findViewById(R.id.button3)
        add_button.setOnClickListener {
            println( "------------------------------------------")
            context.replaceFragment(AddFragment())
            println("------------------------------------------------")
        }

        val temp_button: CardView = root.findViewById(R.id.button6)
        temp_button.setOnClickListener {
            println( "------------------------------------------")
            context.replaceFragment(TempFragment())
            println("------------------------------------------------")
        }


        val manage_button: CardView = root.findViewById(R.id.button4)
        manage_button.setOnClickListener {
            println( "------------------------------------------")
            context.replaceFragment(ManageFragment())
            println("------------------------------------------------")
        }
        val info_button: CardView = root.findViewById(R.id.button5)
        info_button.setOnClickListener {
            println( "------------------------------------------")
            context.replaceFragment(InfoProductFragment())
            println("------------------------------------------------")
        }

        return root
    }

    private fun setPieChart(
        root: View,
        countCategory: MutableList<CountCategory>
    ) {
        val pieChart: PieChart = root.findViewById(R.id.chart)
        val pieData = mutableListOf<PieEntry>()

        val color0 = context?.let { ContextCompat.getColor(it, R.color.theme1_4) }
        val color1 = context?.let { ContextCompat.getColor(it, R.color.theme1_1) }
        val color2 = context?.let { ContextCompat.getColor(it, R.color.theme1_2) }
        val color3 = context?.let { ContextCompat.getColor(it, R.color.theme1_5) }
        val color4 = context?.let { ContextCompat.getColor(it, R.color.theme1_3) }
        val color5 = context?.let { ContextCompat.getColor(it, R.color.theme1_6) }


        val MY_COLORS = intArrayOf(

            color0!!,
            color1!!,
            color2!!,
            color3!!,
            color4!!,
            color5!!
        )
        val colors2 = ArrayList<Int>()

        for (c in MY_COLORS) colors2.add(c)



        val colors = IntArray(7)
        colors.set(0, R.color.theme1_1
        )
        colors.set(1,Color.LTGRAY)
        colors.set(2,Color.YELLOW)
        colors.set(3,Color.WHITE)
        colors.set(4,R.color.theme1_3)
        colors.set(5,Color.BLUE)
        colors.set(6,Color.GRAY)

        for(x in 0 until countCategory.size)
        {
            val slice :PieEntry =PieEntry(countCategory[x].number.toFloat(),countCategory[x].category)
            pieData.add(slice)

        }
        val pieChartData = PieDataSet(pieData,"Alimente")
        pieChartData.setColors(colors2)
        val pieData2:PieData = PieData(pieChartData)
        pieChart.centerText = "Alimente"
        pieChart.holeRadius = 30F
        pieChart.transparentCircleRadius = 40F
        pieChart.data = pieData2



    }


}