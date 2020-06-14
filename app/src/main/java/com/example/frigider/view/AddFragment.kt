package com.example.frigider.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frigider.R
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.viewModel.AddViewModel
import com.example.frigider.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var addViewModel: AddViewModel
    var spinner: Spinner? = null
    var cal = Calendar.getInstance()
    var button_data_expirare: Button? = null
    var button_data_achizitionare: Button? = null
    var data_expirare: EditText? = null
    var data_achizitionare: EditText? = null
    var spinner_text: String = ""
    var isUpdateTime: Boolean = false
    var product: ProductWithId? = null
    var barcode: String = ""
    var path: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add, container, false)
        root.isFocusable = true
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        val context = activity as AppCompatActivity

        addViewModel.liveData.observe(this, Observer {
            addViewModel.getCategory()
            if (it != null) {
                initSpinner(it!!, context)
            }
        })
        println("IDDD"+LoginViewModel.userAccou!!.id)
        val saveProduct: Button = root.findViewById(R.id.id_saveProduct)
        saveProduct.setOnClickListener {
            saveProductToDB(root)
        }
        //pentru editare
        if (this.arguments != null) {
            var bundle: Bundle = this.arguments!!
            if (bundle != null) {
                when (true) {
                    bundle.containsKey("edit") -> {
                        product = bundle.getSerializable("edit") as ProductWithId
                        setDataForEdit(product!!, root)
                    }
                    bundle.containsKey("barcode") -> {
                        barcode = bundle.getString("barcode") as String
                        setDataUsingBarcode(barcode, root)
                    }
                    bundle.containsKey("detect") -> {
                        path = bundle.getString("detect") as String
                        detectProductsFromPhoto(path, root)
                    }
                }
            }
        }
        if (AddViewModel.scannerBarcode != null) {
            val codDeBare: EditText? = root.findViewById<EditText>(R.id.id_codDeBare)

        }
        return root
    }

    private fun detectProductsFromPhoto(path: String, root: View?) {
        addViewModel.sendPhoto(path)

    }

    private fun setDataUsingBarcode(barcode: String, root: View?) {
        //folosim observe pentru comunicarea dintre addFragment si addViewModel
        //addViewModel.getProduct(barcode)
        val codDeBare: EditText? = root!!.findViewById<EditText>(R.id.id_codDeBare)
        if (barcode != null) {
            codDeBare!!.setText(barcode)
            addViewModel.getProductByBarcode(barcode)
            addViewModel.barcodeProduct.observe(this, Observer {
                if (it != null ) {
                    val numeProdus: EditText? = root.findViewById(R.id.id_numeProdus)
                    val temperatura: EditText? = root.findViewById(R.id.id_temperatura)
                    //setare categorie
                    println(it.denumire)
                    numeProdus!!.setText(it.denumire)
                    temperatura!!.setText(it.temperatura.toString())


                } else
                    Toast.makeText(context, "Acest cod nu este inregistrat", Toast.LENGTH_LONG).show()
            })
        }
        println("-BARCODE BARCODE BARCODE BARCODE ")
        println("baarrr->>" + barcode)
    }

    private fun setDataForEdit(product: ProductWithId, root: View) {

        val old_numeProdus: EditText = root.findViewById<EditText>(R.id.id_numeProdus)
        val old_cantitate: EditText? = root.findViewById<EditText>(R.id.id_cantitate)
        val old_data_achizitionare: EditText? =
            root.findViewById<EditText>(R.id.id_data_achizitionare)
        val old_data_expirare: EditText? = root.findViewById<EditText>(R.id.id_data_expirare)
        val old_codDeBare: EditText? = root.findViewById<EditText>(R.id.id_codDeBare)
        val old_temperatura: EditText? = root.findViewById(R.id.id_temperatura)

        //setare text
        old_numeProdus.setText(product.nume.toString())
        old_cantitate!!.setText(product.cantitate.toString())
        old_data_achizitionare!!.setText(product.data_achizitionare.toString())
        old_data_expirare!!.setText(product.data_expirare.toString())
        if (product.cod_de_bare != null)
            old_codDeBare!!.setText(product.cod_de_bare.toString())
        if (product.temperatura != null)
            old_temperatura!!.setText(product.temperatura.toString())
    }

    private fun saveProductToDB(root: View) {
        var context = activity as AppCompatActivity
        val numeProdus: String = root.findViewById<EditText>(R.id.id_numeProdus).text.toString()
        val cantitate: String = root.findViewById<EditText>(R.id.id_cantitate).text.toString()
        val data_achizitionare: String =
            root.findViewById<EditText>(R.id.id_data_achizitionare).text.toString()
        val data_expirare: String =
            root.findViewById<EditText>(R.id.id_data_expirare).text.toString()
        val codDeBare: String = root.findViewById<EditText>(R.id.id_codDeBare).text.toString()
        println("sa vedem daca afisaeaza codul de bare" + codDeBare)
        val temperatura: String = root.findViewById<EditText>(R.id.id_temperatura).text.toString()
        if (product == null) {
            var produs:Product? = null
            println("IDDD"+LoginViewModel.userAccou!!.id)
            if (temperatura != "" && numeProdus != "" && data_achizitionare != "" && data_expirare != "") {

                 produs = Product(
                     LoginViewModel.userAccou!!.id,
                     codDeBare,
                    numeProdus,
                    spinner_text,
                    cantitate,
                    temperatura.toInt(),
                    data_expirare,
                    data_achizitionare
                )
            }else
                if (temperatura == "" && numeProdus != "" && data_achizitionare != "" && data_expirare != "")
                {produs = Product(
                    LoginViewModel.userAccou!!.id,
                    codDeBare,
                    numeProdus,
                    spinner_text,
                    cantitate,
                    -100,
                    data_expirare,
                    data_achizitionare
                )

                }
            if(produs != null) {
                addViewModel.save(produs!!)
                addViewModel.save.observe(this, Observer {
                    if (it != null && it) {
                        Toast.makeText(context, "Salvare efectuata cu succes!", Toast.LENGTH_LONG)
                            .show()
                        context.replaceFragment(HomeFragment())
                    } else
                        Toast.makeText(context, "Eroare", Toast.LENGTH_LONG).show()
                })
            }
            else{
                Toast.makeText(context, "Eroare", Toast.LENGTH_LONG).show()
            }
        } else {
            val produs = ProductWithId(
                product!!.id,
                LoginViewModel.userAccou!!.id,
                codDeBare,
                numeProdus,
                spinner_text,
                cantitate,
                temperatura.toInt(),
                data_expirare,
                data_achizitionare
            )
            addViewModel.update(produs)
            addViewModel.update.observe(this, Observer {
                if (it != null && it) {
                    Toast.makeText(context, "Editare efectuata cu succes!", Toast.LENGTH_LONG)
                        .show()
                    context.replaceFragment(HomeFragment())
                } else
                    Toast.makeText(context, "Eroare", Toast.LENGTH_LONG).show()
            })

        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        val context = activity as AppCompatActivity
        button_data_achizitionare = this.id_button_data_achizitionare
        button_data_expirare = this.id_button_data_expirare
        data_expirare = this.id_data_expirare
        data_achizitionare = this.id_data_achizitionare
        setDataPicker(context, data_expirare, button_data_expirare)
        setDataPicker(context, data_achizitionare, button_data_achizitionare)

    }

    private fun setDataPicker(
        context: AppCompatActivity,
        data: EditText?,
        button: Button?
    ) {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(data)
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    context,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })
    }

    private fun updateDateInView(editText: EditText?) {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText!!.setText(sdf.format(cal.getTime()))
    }

    private fun initSpinner(
        it: List<String>,
        context: AppCompatActivity
    ) {
        spinner = this.spinner_sample
        spinner!!.onItemSelectedListener = this

        // Create an ArrayAdapter using a simple spinner layout and array
        val aa = ArrayAdapter(
            context, android.R.layout.simple_spinner_item, it
        )
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        spinner_text = parent!!.getItemAtPosition(0).toString()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        println("click")
        println(position)

        spinner_text = parent!!.getItemAtPosition(position).toString()
    }


}