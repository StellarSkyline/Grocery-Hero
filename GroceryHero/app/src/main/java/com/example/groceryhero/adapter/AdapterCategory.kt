package com.example.groceryhero.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryhero.R
import com.example.groceryhero.activities.SubCategoryActivity
import com.example.groceryhero.model.Category
import com.example.groceryhero.model.Data
import kotlinx.android.synthetic.main.row_layout_category.view.*

class AdapterCategory(var mContext: Context): RecyclerView.Adapter<AdapterCategory.ViewHolder>() {
    var mList: Category = Category()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_category, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var item = mList.data[position]
        holder.bindItem(item)

    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindItem(item:Data) {

            when(item.catName) {
                "Chicken Items" -> { itemView.image_view_row.setImageResource(R.drawable.icon_chicken)}
                "Veg Items" -> { itemView.image_view_row.setImageResource(R.drawable.icon_veg)}
                "Fruit Items" -> {itemView.image_view_row.setImageResource(R.drawable.icon_fruits)}
                "Beef Item" -> {itemView.image_view_row.setImageResource(R.drawable.icon_beef)}
                "Sehri" -> {itemView.image_view_row.setImageResource(R.drawable.icon_sehri) }
                else -> { itemView.image_view_row.setImageResource(R.drawable.icon_logo)}
            }

            itemView.text_view_row.text = item.catName

            itemView.setOnClickListener{
                var intent = Intent(mContext,SubCategoryActivity::class.java)
                intent.putExtra("data",item)
                mContext.startActivity(intent)
            }
        }


    }

    fun setData(list:Category) {
        mList = list
        notifyDataSetChanged()
    }
}