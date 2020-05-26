package com.example.groceryhero.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryhero.R
import com.example.groceryhero.app.Config
import com.squareup.picasso.Picasso
import getProduct
import kotlinx.android.synthetic.main.row_layout_order_detail.view.*
import kotlinx.android.synthetic.main.row_layout_products.view.*

class AdapterOrderDetail(var mContext: Context, var mList:ArrayList<getProduct> = ArrayList()):RecyclerView.Adapter<AdapterOrderDetail.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = mList[position]!!
        holder.bindView(item)

    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        fun bindView(item:getProduct) {
            Picasso
                .get()
                .load(Config.IMAGE_URL + item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.image_view_product_order)

            itemView.text_view_info.text = "Number of Items: ${item.quantity} | Price: ₹${item.price}"

        }

    }


}