package com.example.groceryhero.adapter

import OrderHistory
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryhero.R
import com.example.groceryhero.activities.OrderDetailActivity
import getData
import kotlinx.android.synthetic.main.row_layout_order_history.view.*

class AdapterOrderHistory(var mContext: Context):RecyclerView.Adapter<AdapterOrderHistory.ViewHolder>() {

    var mList = OrderHistory()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_order_history, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = mList.data[position]

        holder.bindView(item)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(item:getData) {

            itemView.text_view_order_number.text = "Order #: ${position +1}"
            itemView.text_view_price.text = "Price: ₹${item.orderSummary.ourPrice}"
            itemView.text_view_quantity.text = "Total # of items: ${item.orderSummary.orderAmount}"
            itemView.text_view_address.text = "Address: ${item.shippingAddress.houseNo} ${item.shippingAddress.streetName}, ${item.shippingAddress.city}"

            itemView.setOnClickListener {
                var intent = Intent(mContext, OrderDetailActivity::class.java)

                intent.putExtra("item",item)
                intent.putExtra("number",(position +1))

                mContext.startActivity(intent)

            }

        }

    }

    fun setData(list:OrderHistory) {
        mList = list
        notifyDataSetChanged()
    }
}