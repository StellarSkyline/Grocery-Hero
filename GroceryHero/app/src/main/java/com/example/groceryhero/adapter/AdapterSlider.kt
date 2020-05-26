package com.example.groceryhero.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.groceryhero.R
import com.example.groceryhero.helper.log
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.layout_slider.view.*

class AdapterSlider(var mContext: Context, var mList:ArrayList<Int>):
    SliderViewAdapter<AdapterSlider.ViewHolder>() {

    inner class ViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_slider, null)
        return ViewHolder(view)
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.image_view_slider.setImageResource(mList[position%mList.size])
    }

}