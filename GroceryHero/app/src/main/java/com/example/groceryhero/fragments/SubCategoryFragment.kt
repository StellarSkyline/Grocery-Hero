package com.example.groceryhero.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.groceryhero.R
import com.example.groceryhero.activities.CartActivity
import com.example.groceryhero.adapter.AdapterProducts
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.helper.hide
import com.example.groceryhero.model.ProductList
import com.example.groceryhero.model.Products
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_sub_category.*
import kotlinx.android.synthetic.main.fragment_sub_category.view.*
import kotlinx.android.synthetic.main.fragment_sub_category.view.progress_bar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


class SubCategoryFragment : Fragment() {
    var subId:Int = 1
    var mList:ArrayList<Products> = ArrayList()
    lateinit var adapter:AdapterProducts


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_sub_category, container, false)
        init(view)

        return view
    }

    private fun init(view: View) {
        getProducData()
        adapter = AdapterProducts(activity as Context)
        view.recycler_view.layoutManager = LinearLayoutManager(activity!!)
        view.recycler_view.adapter = adapter

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

    fun getProducData() {
        var requestQueue = Volley.newRequestQueue(activity!!)

        var request = StringRequest(
            Request.Method.GET, Endpoint.getProducts()+subId.toString(),
            Response.Listener { response ->
                Log.d("STLog", response.toString())

                var gson = GsonBuilder().create()

                var productList: ProductList = gson.fromJson(response.toString(), ProductList::class.java)

                mList = productList.data

                adapter.setData(mList)
                progress_bar.hide()

            },
            Response.ErrorListener { response ->
                Log.d("data", "Error nyahahah :3")



            })

        requestQueue.add(request)
    }

    override fun onStart() {
        adapter.notifyDataSetChanged()
        super.onStart()
    }


}
