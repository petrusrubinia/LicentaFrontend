package com.example.frigider.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frigider.R
import com.example.frigider.viewModel.ManageViewModel
import com.example.frigider.viewModel.TempViewModel


class TempFragment : Fragment() {


    private lateinit var tempViewModel: TempViewModel
    lateinit var temperatura_actuala:CardView
    lateinit var temperatura_optima:CardView
    lateinit var id_imagine_temperatura_actuala: ImageView
    lateinit var id_imagine_temperatura_optima: ImageView
    var optimTemp: Int = 0
    var actualTemp: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root =  inflater.inflate(R.layout.fragment_temp, container, false)
        val context = activity as AppCompatActivity
        tempViewModel = ViewModelProvider(this).get(TempViewModel::class.java)


        tempViewModel.optimTemp.observe(this, Observer {
            tempViewModel.getOptimTemp()
            if(it!= null)
                optimTemp = it
        })
        tempViewModel.actualTemp.observe(this, Observer {
            tempViewModel.getActualTemp()
            if(it != null)
                actualTemp = it
        })

        temperatura_actuala= root.findViewById(R.id.id_temperatura_actuala)
        id_imagine_temperatura_actuala = root.findViewById(R.id.id_imagine_temperatura_actuala)
        temperatura_optima = root.findViewById(R.id.id_temperatura_optima)
        id_imagine_temperatura_optima = root.findViewById(R.id.id_imagine_temperatura_actuala)

        temperatura_actuala.setOnClickListener {
            setCardViewImg(root,temperatura_actuala,actualTemp,id_imagine_temperatura_actuala)
        }
        temperatura_optima.setOnClickListener {
            setCardViewImg(root, temperatura_optima, optimTemp, id_imagine_temperatura_optima)
        }

        return root
    }

    private fun setCardViewImg(
        root: View?,
        temp_cardView: CardView?,
        temp: Int,
        imagine: ImageView
    ) {
        var ic_number:String = "ic_"
        when (temp){
            -6-> ic_number = ic_number+"sase_minus"
            -5-> ic_number = ic_number+"cinci_minus"
            -4-> ic_number = ic_number+"patru_minus"
            -3-> ic_number = ic_number+"trei_minus"
            -2->ic_number = ic_number+"unu_minus"
            -1->ic_number = ic_number+"unu_minus"
            0->{ic_number = ic_number+"zero"}
            1->{ic_number = ic_number+"unu"}
            2->{ic_number = ic_number+"doi"}
            3->{ic_number = ic_number+"trei"}
            4->{ic_number = ic_number+"patru"}
            5->{ic_number = ic_number+"cinci"}
            6->{ic_number = ic_number+"sase"}
            7->{ic_number = ic_number+"sapte"}
        }
        println(ic_number)

        val resId = imagine.context.resources.getIdentifier(ic_number, "mipmap", imagine.context.packageName)
        imagine.setImageResource(resId)
    }


}