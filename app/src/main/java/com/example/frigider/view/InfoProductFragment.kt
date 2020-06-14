package com.example.frigider.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frigider.R
import com.example.frigider.model.Product.ExpiredProduct
import com.example.frigider.service.Adapter.ExpiredElemsAdapter
import com.example.frigider.viewModel.InfoProductViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import kotlinx.android.synthetic.main.fragment_info_produts.*


class InfoProductFragment : Fragment() {


    private lateinit var infoProductViewModel: InfoProductViewModel
    var current_expiredProduct= mutableListOf<ExpiredProduct>()
    var current_position_list: Int = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_info_produts, container, false)

        infoProductViewModel = ViewModelProvider(this).get(InfoProductViewModel::class.java)
        setHasOptionsMenu(true);

        infoProductViewModel.liveData.observe(this, Observer {
            infoProductViewModel.getExpiredList()
            if (it != null) {
                current_expiredProduct = it
            }

           setBarChart(root, current_expiredProduct)
        })

        return root
    }

    private fun setBarChart(root: View?, expiredProduct: MutableList<ExpiredProduct>) {
        val barChart: BarChart = root!!.findViewById(R.id.id_barChart)
        var color0:Int?
        var color1:Int?
        var color2:Int?
        var color3:Int?
        var color4:Int?
        var color5:Int?
        var color6:Int?
        var color7:Int?
        var color8:Int?
        var color9:Int?
        var color10:Int?
        var color11:Int?
        var color12:Int?
        if(getThemeId() == R.style.AppTheme1) {
            color0 = context?.let { ContextCompat.getColor(it, R.color.theme1_4) }
            color1 = context?.let { ContextCompat.getColor(it, R.color.theme1_1) }
            color2 = context?.let { ContextCompat.getColor(it, R.color.theme1_2) }
            color3 = context?.let { ContextCompat.getColor(it, R.color.theme1_5) }
            color4 = context?.let { ContextCompat.getColor(it, R.color.theme1_3) }
            color5 = context?.let { ContextCompat.getColor(it, R.color.theme1_6) }
            color6 = context?.let { ContextCompat.getColor(it, R.color.theme1_7) }
            color7 = context?.let { ContextCompat.getColor(it, R.color.theme1_8) }
            color8 = context?.let { ContextCompat.getColor(it, R.color.theme1_9) }
            color9 = context?.let { ContextCompat.getColor(it, R.color.theme1_10) }
            color10 = context?.let { ContextCompat.getColor(it, R.color.theme1_3) }
            color11 = context?.let { ContextCompat.getColor(it, R.color.theme1_6) }
            color12 = context?.let { ContextCompat.getColor(it, R.color.theme1_1) }
        }else
            if(getThemeId() == R.style.AppTheme2)
            {
                color0 = context?.let { ContextCompat.getColor(it, R.color.theme2_4) }
                color1 = context?.let { ContextCompat.getColor(it, R.color.theme2_1) }
                color2 = context?.let { ContextCompat.getColor(it, R.color.theme2_2) }
                color3 = context?.let { ContextCompat.getColor(it, R.color.theme2_5) }
                color4 = context?.let { ContextCompat.getColor(it, R.color.theme2_3) }
                color5 = context?.let { ContextCompat.getColor(it, R.color.theme2_6) }
                color6 = context?.let { ContextCompat.getColor(it, R.color.theme2_7) }
                color7 = context?.let { ContextCompat.getColor(it, R.color.theme2_8) }
                color8 = context?.let { ContextCompat.getColor(it, R.color.theme2_9) }
                color9 = context?.let { ContextCompat.getColor(it, R.color.theme2_10) }
                color10 = context?.let { ContextCompat.getColor(it, R.color.theme2_3) }
                color11 = context?.let { ContextCompat.getColor(it, R.color.theme2_6) }
                color12 = context?.let { ContextCompat.getColor(it, R.color.theme2_1) }
            }
            else if(getThemeId() == R.style.AppTheme3)
            {
                color0 = context?.let { ContextCompat.getColor(it, R.color.theme3_4) }
                color1 = context?.let { ContextCompat.getColor(it, R.color.theme3_1) }
                color2 = context?.let { ContextCompat.getColor(it, R.color.theme3_2) }
                color3 = context?.let { ContextCompat.getColor(it, R.color.theme3_5) }
                color4 = context?.let { ContextCompat.getColor(it, R.color.theme3_3) }
                color5 = context?.let { ContextCompat.getColor(it, R.color.theme3_6) }
                color6 = context?.let { ContextCompat.getColor(it, R.color.theme3_7) }
                color7 = context?.let { ContextCompat.getColor(it, R.color.theme3_8) }
                color8 = context?.let { ContextCompat.getColor(it, R.color.theme3_9) }
                color9 = context?.let { ContextCompat.getColor(it, R.color.theme3_10) }
                color10 = context?.let { ContextCompat.getColor(it, R.color.theme3_3) }
                color11 = context?.let { ContextCompat.getColor(it, R.color.theme3_6) }
                color12 = context?.let { ContextCompat.getColor(it, R.color.theme3_1) }
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
                color7 = context?.let { ContextCompat.getColor(it, R.color.theme4_8) }
                color8 = context?.let { ContextCompat.getColor(it, R.color.theme4_9) }
                color9 = context?.let { ContextCompat.getColor(it, R.color.theme4_10) }
                color10 = context?.let { ContextCompat.getColor(it, R.color.theme4_3) }
                color11 = context?.let { ContextCompat.getColor(it, R.color.theme4_6) }
                color12 = context?.let { ContextCompat.getColor(it, R.color.theme4_1) }
            }
            else{
                color0 = context?.let { ContextCompat.getColor(it, R.color.theme5_4) }
                color1 = context?.let { ContextCompat.getColor(it, R.color.theme5_1) }
                color2 = context?.let { ContextCompat.getColor(it, R.color.theme5_2) }
                color3 = context?.let { ContextCompat.getColor(it, R.color.theme5_5) }
                color4 = context?.let { ContextCompat.getColor(it, R.color.theme5_3) }
                color5 = context?.let { ContextCompat.getColor(it, R.color.theme5_6) }
                color6 = context?.let { ContextCompat.getColor(it, R.color.theme5_7) }
                color7 = context?.let { ContextCompat.getColor(it, R.color.theme5_8) }
                color8 = context?.let { ContextCompat.getColor(it, R.color.theme5_9) }
                color9 = context?.let { ContextCompat.getColor(it, R.color.theme5_10) }
                color10 = context?.let { ContextCompat.getColor(it, R.color.theme5_3) }
                color11 = context?.let { ContextCompat.getColor(it, R.color.theme5_6) }
                color12 = context?.let { ContextCompat.getColor(it, R.color.theme5_1) }
            }
        val MY_COLORS = intArrayOf(
            color0!!,
            color1!!,
            color2!!,
            color3!!,
            color4!!,
            color5!!,
            color6!!,
            color7!!,
            color8!!,
            color9!!,
            color10!!,
            color11!!,
            color12!!
        )
        val colors2 = ArrayList<Int>()

        for (c in MY_COLORS) colors2.add(c)

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        var maxim = 0
        for (x in 0 until expiredProduct.size) {
            entries.add(
                BarEntry(
                    x.toFloat(),
                    expiredProduct[x].lista_produse.size.toFloat(),
                    expiredProduct[x].month + " " + expiredProduct[x].year

                )
            )
            if ( expiredProduct[x].lista_produse.size >= maxim)
                maxim = expiredProduct[x].lista_produse.size
            labels.add(expiredProduct[x].month + " " + expiredProduct[x].year)
        }
        val barDataSet = BarDataSet(entries, "Cells")
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
       // barChart.xAxis.setCenterAxisLabels(true);



        val yAxis: YAxis = barChart.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = maxim.toFloat()
        yAxis.granularity = 1f

        val yAxisRight: YAxis = barChart.axisRight
        yAxisRight.axisMinimum = 0f
        yAxisRight.axisMaximum = maxim.toFloat()
        yAxisRight.granularity = 1f



        val data = BarData(barDataSet)
        data.barWidth=0.8f
        barChart.xAxis.setCenterAxisLabels(true);

        barChart.onChartGestureListener = object : OnChartGestureListener {
            override fun onChartDoubleTapped(me: MotionEvent) {
                Toast.makeText(activity, "Double", Toast.LENGTH_SHORT).show()
                println(me)
            }

            override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
                print("")            }

            override fun onChartGestureEnd(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {
                print("")            }

            override fun onChartFling(
                me1: MotionEvent?,
                me2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ) {
                print("")            }

            override fun onChartSingleTapped(me: MotionEvent) {
                Toast.makeText(activity, "Single", Toast.LENGTH_SHORT).show()
                if(barChart.getHighlightByTouchPoint(me.x, me.y)!= null) {
                    val h: Highlight =
                        barChart.getHighlightByTouchPoint(me.x, me.y)
                    println("->>>>" + h.x)
                    current_position_list = h.x.toInt()
                    setListExpiredProduct(expiredProduct[h.x.toInt()])
                }
            }

            override fun onChartGestureStart(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {
                print("")
            }

            override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
                print("")
            }

            override fun onChartLongPressed(me: MotionEvent?) {
                print("")
            }
        }
        barChart.data = data
        barDataSet.colors = colors2
        barChart.isDoubleTapToZoomEnabled = false
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
    private fun setListExpiredProduct(expiredProduct: ExpiredProduct) {
        id_recycler_expired.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        id_recycler_expired.layoutManager = layoutManager
        id_recycler_expired.adapter =
            ExpiredElemsAdapter(activity as AppCompatActivity,expiredProduct.lista_produse,this) {
                println(it.nume)
            }
        id_recycler_expired
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        if (item.itemId == android.R.id.home) {
            if (activity != null) {
                activity!!.onBackPressed();
            }
            return true;
        };
        return super.onOptionsItemSelected(item);

    }

    fun deleteElement(position: Int) {
        infoProductViewModel.deleteElement(current_expiredProduct[current_position_list].lista_produse.get(position))

    }

}