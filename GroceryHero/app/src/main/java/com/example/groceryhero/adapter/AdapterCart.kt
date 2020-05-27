package com.example.groceryhero.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryhero.R
import com.example.groceryhero.activities.CartActivity
import com.example.groceryhero.app.Config
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.Linker
import com.example.groceryhero.model.ProductsDB
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_cart.view.*

class AdapterCart(var mContext: Context):RecyclerView.Adapter<AdapterCart.ViewHolder>() {
    var mList:ArrayList<ProductsDB> = ArrayList()
    var db = DBHelper()
    var quantity = 0
    var linker = mContext as Linker


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_cart, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = mList[position]

        holder.bindItem(item)
    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(item:ProductsDB) {

            itemView.text_view_title_product.text = item.name
            itemView.text_view_price.text = "Price: ₹${item.price}"
            itemView.text_view_quantity.text = "Quantity: ${item.quantity}"

            Picasso
                .get()
                .load(Config.IMAGE_URL + item.image)
                .placeholder(R.drawable.img_noimage)
                .into(itemView.image_view_product)

            itemView.button_remove.setOnClickListener{
                mList.removeAt(position)
                notifyItemRemoved(position)
                db.deleteProduct(item.name)
                var link = mContext as Linker
                link.updateUI()
                notifyDataSetChanged()
            }

            itemView.button_add_quantity.setOnClickListener {
                db.updateProduct(item.name, 1 + db.itemInCartQuantity(item.name))

                var link = mContext as Linker
                link.updateUI()

                notifyDataSetChanged()

            }

            itemView.button_minus_quantity.setOnClickListener {
                db.updateProduct(item.name,db.itemInCartQuantity(item.name) - 1)
                item.quantity -=1
                if(item.quantity == 0) {
                    mList.removeAt(position)
                    notifyItemRemoved(position)
                    db.deleteProduct(item.name)
                }

                var link = mContext as Linker
                link.updateUI()
                notifyDataSetChanged()
            }
        }

    }

    fun setData(list:ArrayList<ProductsDB>) {
        mList = list
        notifyDataSetChanged()
    }

}