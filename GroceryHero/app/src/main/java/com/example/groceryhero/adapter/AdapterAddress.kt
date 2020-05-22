package com.example.groceryhero.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryhero.R
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.database.DBAddress
import com.example.groceryhero.helper.log
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.AddData
import com.example.groceryhero.model.AddressData
import kotlinx.android.synthetic.main.row_layout_address.view.*

class AdapterAddress(var mContext:Context):RecyclerView.Adapter<AdapterAddress.ViewHolder>() {

    var mList:AddressData = AddressData()
    lateinit var db:DBAddress

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_address, parent, false)
        db = DBAddress()

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = mList.data[position]

        holder.bindView(item)

    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        fun bindView(item:AddData) {
            itemView.text_view_house_street.text = "${item.houseNo} ${item.streetName}"
            itemView.text_view_city_state_zip.text = "${item.city}, ${item.location}, ${item.pincode}"
            itemView.text_view_type.text = item.type

            itemView.button_set_primary.setOnClickListener{
                if(db.isPopulated()) {
                    db.deleteData()
                    db.addAddress(item)
                    mContext.toast("Set as Primary Address")

                } else {
                    db.addAddress(item)
                    mContext.toast("Set as Primary Address")
                }
            }

            itemView.button_remove.setOnClickListener {
                deleteId(mList.data[position]._id)

                mList.data.removeAt(position)
                notifyItemRemoved(position)

            }

        }
    }

    fun setData(list:AddressData) {
        mList = list
        notifyDataSetChanged()
    }

    fun deleteId(id:String) {
        var requestQueue = Volley.newRequestQueue(mContext)

        var request = StringRequest(Request.Method.DELETE, Endpoint.getAddress() + id,
            Response.Listener { response ->
                mContext.log(response.toString())
            },
            Response.ErrorListener {response ->
                mContext.log(response.message.toString())
            })
        requestQueue.add(request)
    }
}