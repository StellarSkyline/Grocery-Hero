package com.example.groceryhero.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.groceryhero.R
import com.example.groceryhero.adapter.AdapterCategory
import com.example.groceryhero.app.Endpoint
import com.example.groceryhero.helper.hide
import com.example.groceryhero.helper.log
import com.example.groceryhero.helper.show
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.Category
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.progress_bar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var mList:Category = Category()
    lateinit var adapter: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_home, container, false)




        init(view)
        return view
    }

    private fun init(view: View) {
        getCategoryData(view)
        adapter = AdapterCategory(activity!!)
        view.recycler_view.layoutManager = GridLayoutManager(activity, 2)
        view.recycler_view.adapter = adapter



    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getCategoryData(view:View) {
        var requestQueue = Volley.newRequestQueue(activity)

        var request = StringRequest(Request.Method.GET, Endpoint.getCategory(),
            Response.Listener { response ->
                activity!!.log(response.toString())
                var gson = GsonBuilder().create()
                mList = gson.fromJson(response.toString(), Category::class.java)
                adapter.setData(mList)
                progress_bar.hide()




            },
            Response.ErrorListener { response ->
                activity!!.log(response.message.toString())

            })

        requestQueue.add(request)
    }
}
