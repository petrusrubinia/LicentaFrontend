package com.example.frigider.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frigider.R
import com.example.frigider.model.Product.ExpiredProduct
import com.example.frigider.repository.Adapter.ExpiredElemsAdapter
import com.example.frigider.repository.Adapter.ItemListAdapter
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
import kotlinx.android.synthetic.main.fragment_main.*


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
        println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa intraaaaaaaaaaattttttttttt baieeeeeeeeetti")
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

        val color0 = context?.let { ContextCompat.getColor(it, R.color.theme1_4) }
        val color1 = context?.let { ContextCompat.getColor(it, R.color.theme1_1) }
        val color2 = context?.let { ContextCompat.getColor(it, R.color.theme1_2) }
        val color3 = context?.let { ContextCompat.getColor(it, R.color.theme1_5) }
        val color4 = context?.let { ContextCompat.getColor(it, R.color.theme1_3) }
        val color5 = context?.let { ContextCompat.getColor(it, R.color.theme1_6) }
        val color6 = context?.let { ContextCompat.getColor(it, R.color.theme1_7) }
        val color7 = context?.let { ContextCompat.getColor(it, R.color.theme1_8) }
        val color8 = context?.let { ContextCompat.getColor(it, R.color.theme1_9) }
        val color9 = context?.let { ContextCompat.getColor(it, R.color.theme1_10) }
        val color10 = context?.let { ContextCompat.getColor(it, R.color.theme1_11) }
        val color11 = context?.let { ContextCompat.getColor(it, R.color.theme1_1) }
        val color12 = context?.let { ContextCompat.getColor(it, R.color.theme1_2) }

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
                val h: Highlight =
                    barChart.getHighlightByTouchPoint(me.x, me.y)
                println("->>>>"+h.x)
                current_position_list = h.x.toInt()
                setListExpiredProduct(expiredProduct[h.x.toInt()])
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