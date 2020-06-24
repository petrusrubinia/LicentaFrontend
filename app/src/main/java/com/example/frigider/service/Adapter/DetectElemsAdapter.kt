package com.example.frigider.service.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.frigider.R
import com.example.frigider.model.Product.Product
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.view.DetectedElemsFragment
import kotlinx.android.synthetic.main.element_detect_list.view.*
import kotlinx.android.synthetic.main.element_list.view.*


class DetectElemsAdapter(
    private val context: Context,
    private val bookList: MutableList<ProductWithId>,
    private val detectElemsFragment: DetectedElemsFragment,
    private val listener: (ProductWithId) -> Unit
): RecyclerView.Adapter<DetectElemsAdapter.ArticleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.element_detect_list, parent, false))

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(bookList[position], listener)
        holder.delete_button.setOnClickListener {

//            placeholderFragment.deleteElement(holder.adapterPosition)
//            bookList.removeAt(holder.adapterPosition)
//            notifyItemRemoved(holder.adapterPosition)
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Esti sigur ca vrei sa elimini acest produs?")
            builder.setPositiveButton(android.R.string.yes, object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which:Int) {
                    detectElemsFragment.deleteElement(holder.adapterPosition)
                    bookList.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                }
            })
            builder.setNegativeButton(android.R.string.cancel, object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which:Int) {
                }
            })
            builder.show()
        }
        holder.edit_button.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Esti sigur ca vrei sa modifici acest produs?")
            builder.setPositiveButton(android.R.string.yes, object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which:Int) {
                    detectElemsFragment.editElement(holder.adapterPosition)
                    notifyItemChanged(holder.adapterPosition)
                }
            })
            builder.setNegativeButton(android.R.string.cancel, object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which:Int) {
                }
            })
            builder.show()
        }

    }

    override fun getItemCount() = bookList.size

    class ArticleHolder(articleView: View): RecyclerView.ViewHolder(articleView) {
        lateinit var edit_button: CardView
        lateinit var delete_button: CardView
        fun bind(product: ProductWithId, listener: (ProductWithId) -> Unit) = with(itemView) {
            idNume_detect.text ="Denumire: " + product.nume
            idCategroie_detect.text = "Categorie: " + product.categorie
            idCantitate_detect.text = "Cantitate: " + product.categorie
            idTemperatura_detect.text = "Temperatura: " + product.temperatura.toString()
            idDataAchizitionare_detect.text = "Data achizitionare: " + product.data_achizitionare
            idDataExpirare_detect.text = "Data expirare: " + product.data_expirare
            edit_button = findViewById(R.id.id_edit)
            delete_button = findViewById(R.id.id_stergere)
            val img: ImageView = findViewById(R.id.id_image) as ImageView
            var ic_category: String = "ic_" + product.categorie
            println(ic_category)
            val resId =
                img.context.resources.getIdentifier(ic_category, "mipmap", img.context.packageName)
            img.setImageResource(resId)
            setOnClickListener { listener(product) }
        }
    }
}
