package com.example.groceryhero.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryhero.fragments.SubCategoryFragment
import com.example.groceryhero.model.SubCategory

class AdapterFragment(fm: FragmentManager):FragmentPagerAdapter(fm) {

    var FragmentList:ArrayList<Fragment> = ArrayList()
    var mList:ArrayList<SubCategory> = ArrayList()
    var mTitle:ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return FragmentList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]

    }

    fun addFragment(catId:Int, catName:String){
        FragmentList.add(SubCategoryFragment.newInstance(catId))
        mTitle.add(catName)

    }

    fun setData(list:ArrayList<SubCategory>) {
        mList = list
        notifyDataSetChanged()
    }
}