package com.example.groceryhero.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.groceryhero.R
import com.example.groceryhero.helper.log
import kotlinx.android.synthetic.main.layout_slider.view.*

class AdapterSlider(var mContext: Context, var mList:ArrayList<Int>):PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {

        return mList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

//        var view = LayoutInflater.from(mContext).inflate(R.layout.layout_slider, container, false)
        var inflater = LayoutInflater.from(mContext)
        var view = inflater.inflate(R.layout.layout_slider, container,false)

        view.image_view_slider.setImageResource(mList[position])
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViews(position,position)

    }


}