package com.example.frigider.view


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frigider.R
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.service.Adapter.ItemListAdapter
import com.example.frigider.viewModel.PageViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    var intrat: Boolean = false
    lateinit var listOfProducts:List<ProductWithId>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1);getListOfProducts(arguments?.getInt(
            ARG_SECTION_NUMBER
        ) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        val context = activity as AppCompatActivity

        pageViewModel.liveData.observe(this, Observer<List<ProductWithId>?> {
                listOfProducts = it!!
                intrat = true
                setupRecycler(listOfProducts as ArrayList<ProductWithId>,context)
        })


        return root
    }
    fun setupRecycler(productsList: ArrayList<ProductWithId>,context: Context) {
        id_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        id_recycler.layoutManager = layoutManager
        id_recycler.adapter =
            ItemListAdapter(activity as AppCompatActivity,productsList,this) {
                //Log.v("Product", it.ID.toString())
            }
    }

    fun deleteElement(adapterPosition: Int) {
        //println(listOfProducts[adapterPosition])
        pageViewModel.deleteElement(listOfProducts[adapterPosition])
    }

    fun editElement(adapterPosition: Int) {
        var context = activity as AppCompatActivity
        var fragment : Fragment = AddFragment()
        var bundle: Bundle = Bundle()
        bundle.putSerializable("edit",listOfProducts[adapterPosition])
        fragment.arguments = bundle
        context.replaceFragment(fragment)
        println(listOfProducts[adapterPosition])
    }
    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}