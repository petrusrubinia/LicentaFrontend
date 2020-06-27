package com.example.frigider.view

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
    lateinit var notificationManager: NotificationManagerCompat
    lateinit var  context: AppCompatActivity

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        context = activity as AppCompatActivity

        notificationManager = NotificationManagerCompat.from(context)

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
    fun sendNotification(message: String)
    {
        var notification: android.app.Notification = NotificationCompat.Builder(context,
            Notification.CHANNEL_1
        )
            .setSmallIcon(R.drawable.ic_home)
            .setContentTitle("Informatii produse")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        notificationManager.notify(1,notification)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setPieChart(
        root: View,
        countCategory: MutableList<CountCategory>
    ) {
        val pieChart: PieChart = root.findViewById(R.id.chart)
        val pieData = mutableListOf<PieEntry>()
        var color0:Int?
        var color1:Int?
        var color2:Int?
        var color3:Int?
        var color4:Int?
        var color5:Int?
        var color6:Int?
        if(getThemeId() == R.style.AppTheme1) {
            color0 = context?.let { ContextCompat.getColor(context, R.color.theme1_4) }
            color1 = context?.let { ContextCompat.getColor(it, R.color.theme1_1) }
            color2 = context?.let { ContextCompat.getColor(it, R.color.theme1_2) }
            color3 = context?.let { ContextCompat.getColor(it, R.color.theme1_5) }
            color4 = context?.let { ContextCompat.getColor(it, R.color.theme1_3) }
            color5 = context?.let { ContextCompat.getColor(it, R.color.theme1_6) }
            color6 = context?.let { ContextCompat.getColor(it, R.color.theme1_7) }
        }else
            if(getThemeId()== R.style.AppTheme2)
            {
                color0 = context?.let { ContextCompat.getColor(it, R.color.theme2_4) }
                color1 = context?.let { ContextCompat.getColor(it, R.color.theme2_7) }
                color2 = context?.let { ContextCompat.getColor(it, R.color.theme2_2) }
                color3 = context?.let { ContextCompat.getColor(it, R.color.theme2_5) }
                color4 = context?.let { ContextCompat.getColor(it, R.color.theme2_3) }
                color5 = context?.let { ContextCompat.getColor(it, R.color.theme2_6) }
                color6 = context?.let { ContextCompat.getColor(it, R.color.theme2_7) }
            }
        else if(getThemeId() == R.style.AppTheme3)
            {
                color0 = context?.let { ContextCompat.getColor(it, R.color.theme3_4) }
                color1 = context?.let { ContextCompat.getColor(it, R.color.theme3_1) }
                color2 = context?.let { ContextCompat.getColor(it, R.color.theme3_2) }
                color3 = context?.let { ContextCompat.getColor(it, R.color.theme3_5) }
                color4 = context?.let { ContextCompat.getColor(it, R.color.theme3_3) }
                color5 = context?.let { ContextCompat.getColor(it, R.color.theme3_6) }
                color6 = context?.let { ContextCompat.getColor(it, R.color.theme3_8) }
            }
        else if(getThemeId() == R.style.AppTheme4)
            {
                color0 = context?.let { ContextCompat.getColor(it, R.color.theme4_4) }
                color1 = context?.let { ContextCompat.getColor(it, R.color.theme4_1) }
                color2 = context?.let { ContextCompat.getColor(it, R.color.theme4_2) }
                color3 = context?.let { ContextCompat.getColor(it, R.color.theme4_5) }
                color4 = context?.let { ContextCompat.getColor(it, R.color.theme4_3) }
                color5 = context?.let { ContextCompat.getColor(it, R.color.theme4_6) }
                color6 = context?.let { ContextCompat.getColor(it, R.color.theme4_7) }

            }
        else{
                color0 = context?.let { ContextCompat.getColor(context, R.color.theme5_4) }
                color1 = context?.let { ContextCompat.getColor(context, R.color.theme5_1) }
                color2 = context?.let { ContextCompat.getColor(context, R.color.theme5_2) }
                color3 = context?.let { ContextCompat.getColor(context, R.color.theme5_5) }
                color4 = context?.let { ContextCompat.getColor(context, R.color.theme5_3) }
                color5 = context?.let { ContextCompat.getColor(context, R.color.theme5_6) }
                color6 = context?.let { ContextCompat.getColor(it, R.color.theme5_8) }
            }
        println("Actual--->>" + getThemeId())
        println("Theme1----->"+ color0!!)
        println("Theme2----->"+ color1!!)
        println("Theme3----->"+ color2!!)
        println("Theme4----->"+ color3!!)
        println("Theme5----->"+ color4!!)

        val MY_COLORS = intArrayOf(

            color0!!,
            color1!!,
            color2!!,
            color3!!,
            color4!!,
            color5!!,
            color6!!
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

        homeViewModel.expiredProducts.observe(this, Observer {
            homeViewModel.getExpiredProduct()
            var text : String = ""
            if(it!= null)
            {
                for (x in it)
                    text += x +"\n"
                sendNotification(text)
            }

        })


    }
    fun getThemeId():Int {
        try
        {
            val wrapper = Context::class.java
            val method = wrapper!!.getMethod("getThemeResId")
            method.setAccessible(true)
            return method.invoke(context) as Int
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
        return 0
    }

}