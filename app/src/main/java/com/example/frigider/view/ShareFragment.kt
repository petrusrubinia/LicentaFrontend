package com.example.frigider.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.frigider.R
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.viewModel.ShareViewModel

class ShareFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel
    private lateinit var contex: Context


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shareViewModel =
            ViewModelProviders.of(this).get(ShareViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        //val textView: TextView = root.findViewById(R.id.text_share)
        var textMail: EditText = root.findViewById(R.id.idTextEmail)
        shareViewModel.getList()
        shareViewModel.list.observe(this, Observer {
            if(it!= null)
                textMail.setText(getText(it))
        })
        var send: CardView = root.findViewById(R.id.idSendEmail)
        send.setOnClickListener {
            sendMail(root)
        }

        return root
    }
    fun getText(list:List<ProductWithId>):String{
        var text:String = ""
        for (x in list)
            text+= "Produs: "+ x.nume + " Cantitate: " + x.cantitate +
                    "\n            Data achizitionare" +x.data_achizitionare +
                    "\n            Data expirare" +x.data_expirare +
                    "\n"
        return text

    }
    fun sendMail(root:View){
        var i = Intent(Intent.ACTION_SEND)
        var mail: String = root.findViewById<EditText>(R.id.idEmail).text.toString()
        var subjMail: String = root.findViewById<EditText>(R.id.idSubiectEmail).text.toString()
        var textMail: String = root.findViewById<EditText>(R.id.idTextEmail).text.toString()
        if(mail != "" && textMail != "")
        {

            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
            i.putExtra(Intent.EXTRA_SUBJECT, subjMail)
            i.putExtra(Intent.EXTRA_TEXT, textMail)
        }

        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                contex,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}