package com.example.frigider.repository.Adapter


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
import com.example.frigider.model.Product.ProductWithId
import com.example.frigider.view.InfoProductFragment
import kotlinx.android.synthetic.main.element_expired_elems.view.*


class ExpiredElemsAdapter(
    private val context : Context,
    private val bookList: ArrayList<ProductWithId>,
    private val infoProductFragment:InfoProductFragment,
    private val listener: (ProductWithId) -> Unit
) : RecyclerView.Adapter<ExpiredElemsAdapter.ArticleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.element_expired_elems, parent, false)
    )

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(bookList[position], listener)
        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Esti sigur ca vrei sa elimini acest produs?")
            builder.setPositiveButton(android.R.string.yes, object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which:Int) {
                    infoProductFragment.deleteElement(holder.adapterPosition)
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
    }

    override fun getItemCount() = bookList.size

    class ArticleHolder(articleView: View) : RecyclerView.ViewHolder(articleView) {
        lateinit var deleteButton: CardView

        fun bind(article: ProductWithId, listener: (ProductWithId) -> Unit) = with(itemView) {
            title_expired.text = article.nume
            data_expirare.text = "Data expirare:" + article.data_expirare
            deleteButton = findViewById(R.id.id_stergere_expired)
            val img: ImageView = findViewById(R.id.id_image_expired)
            var icCategory: String = "ic_" + article.categorie
            val resId =
                img.context.resources.getIdentifier(icCategory, "mipmap", img.context.packageName)
            img.setImageResource(resId)
            setOnClickListener { listener(article) }

        }
    }
}
