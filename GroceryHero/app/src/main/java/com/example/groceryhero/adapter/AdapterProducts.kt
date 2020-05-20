package com.example.groceryhero.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryhero.R
import com.example.groceryhero.activities.ProductDetailActivity
import com.example.groceryhero.app.Config
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.hide
import com.example.groceryhero.helper.show
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.Products
import com.example.groceryhero.model.ProductsDB
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_products.view.*

class AdapterProducts(var mContext: Context):RecyclerView.Adapter<AdapterProducts.ViewHolder>() {

    var mList:ArrayList<Products> = ArrayList()
    lateinit var db:DBHelper


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        db = DBHelper()
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_products, parent, false)

        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = mList[position]
        holder.bindView(item)
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bindView(item:Products) {

            Picasso
                .get()
                .load(Config.IMAGE_URL + item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.image_view_product)

            itemView.text_view_title_product.text = item.productName
            itemView.text_view_mrp.text = "MRP: ₹${item.mrp.toString()}"
            itemView.text_view_price.text = "Price: ₹${item.price.toString()}"
            itemView.text_view_mrp.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            if(db.isItemInCart(item.productName)) {
                itemView.text_view_qty_product.text = db.itemInCartQuantity(item.productName).toString()
                itemView.layout_plus_minus_button.show()
                itemView.layout_add_cart.hide()
            } else {
                itemView.layout_plus_minus_button.hide()
                itemView.layout_add_cart.show()
            }



            itemView.setOnClickListener{
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra("data",item)
                mContext.startActivity(intent)
            }



            itemView.button_add_to_cart.setOnClickListener {

                var name = item.productName
                var price = item.price.toString()
                var image = item.image
                var mrp = item.mrp.toString()
                var productItems = ProductsDB(name, price,image, 1, mrp.toDouble())
                db.addProduct(productItems,1)
                mContext.toast("Item added to cart")
                itemView.text_view_qty_product.text = db.itemInCartQuantity(item.productName).toString()
                itemView.layout_add_cart.hide()
                itemView.layout_plus_minus_button.show()

            }

            itemView.button_add_quantity.setOnClickListener{
                db.updateProduct(item.productName, 1 + db.itemInCartQuantity(item.productName))
                itemView.text_view_qty_product.text = db.itemInCartQuantity(item.productName).toString()

            }

            itemView.button_minus_quantity.setOnClickListener{
                if(db.itemInCartQuantity(item.productName) > 1) {
                    db.updateProduct(item.productName,db.itemInCartQuantity(item.productName) - 1)
                    itemView.text_view_qty_product.text = db.itemInCartQuantity(item.productName).toString()
                } else {
                    db.deleteProduct(item.productName)
                    itemView.layout_add_cart.show()
                    itemView.layout_plus_minus_button.hide()
                }
            }

        }

    }

    fun setData(list:ArrayList<Products>) {
        mList = list
        notifyDataSetChanged()
    }

}