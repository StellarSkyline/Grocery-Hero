package com.example.groceryhero.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryhero.R
import com.example.groceryhero.app.Config
import com.example.groceryhero.model.ProductsDB
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_cart.view.*
import kotlinx.android.synthetic.main.row_layout_order.view.*
import kotlinx.android.synthetic.main.row_layout_order.view.image_view_product
import kotlinx.android.synthetic.main.row_layout_order.view.text_view_mrp
import kotlinx.android.synthetic.main.row_layout_order.view.text_view_price
import kotlinx.android.synthetic.main.row_layout_order.view.text_view_title_product
import kotlinx.android.synthetic.main.row_layout_products.view.*

class AdapterOrder(var mContext: Context):RecyclerView.Adapter<AdapterOrder.ViewHolder>() {
    var mList:ArrayList<ProductsDB> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_order, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = mList[position]
        holder.bindView(item)

    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindView(item:ProductsDB) {
            itemView.text_view_title_product.text = item.name
            itemView.text_view_price.text = "Price: ₹${item.price}"
            itemView.text_view_mrp.text = "Quantity: ${item.quantity.toString()}"

            Picasso
                .get()
                .load(Config.IMAGE_URL + item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.image_view_product)
        }

    }

    fun setData(list:ArrayList<ProductsDB>) {
        mList = list
        notifyDataSetChanged()
    }
}