package com.example.frigider.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frigider.R
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.service.Adapter.DetectElemsAdapter
import com.example.frigider.viewModel.DetectedElemsViewModel
import com.example.frigider.viewModel.InfoProductViewModel
import kotlinx.android.synthetic.main.fragment_detect.*
import kotlinx.android.synthetic.main.fragment_info_produts.*

class DetectedElemsFragment : Fragment() {

    private lateinit var detectedViewModel: DetectedElemsViewModel
    var path: String = ""
    private lateinit var list: MutableList<ProductWithId>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detect, container, false)
        detectedViewModel = ViewModelProvider(this).get(DetectedElemsViewModel::class.java)
        if( this.arguments!= null) {
            var bundle: Bundle = this.arguments!!
            if (bundle != null) {
                when(true)
                {
                    bundle.containsKey("detect")->{
                        path = bundle.getString("detect") as String
                        detectProductsFromPhoto(path,root)
                    }
                }
            }
        }
        return root
    }
    private fun detectProductsFromPhoto(path: String, root: View?) {
        detectedViewModel.sendPhoto(path)
        detectedViewModel.detectProducts.observe(this, Observer{
            if(it!=null) {
                setList(it)
                list = it
            }
        })

    }
    private fun setList(it: MutableList<ProductWithId>?) {
        id_recycler_detect.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        id_recycler_detect.layoutManager = layoutManager
        id_recycler_detect.adapter = DetectElemsAdapter(activity as AppCompatActivity,it!!,this) {}

    }

    fun editElement(adapterPosition: Int) {
        var context = activity as AppCompatActivity
        var fragment : Fragment = AddFragment()
        var bundle: Bundle = Bundle()
        bundle.putSerializable("edit",list[adapterPosition])
        fragment.arguments =bundle
        context.replaceFragment(fragment)
        println(list[adapterPosition])
    }

    fun deleteElement(adapterPosition: Int) {
        detectedViewModel.deleteElement(list.get(adapterPosition))
    }
}