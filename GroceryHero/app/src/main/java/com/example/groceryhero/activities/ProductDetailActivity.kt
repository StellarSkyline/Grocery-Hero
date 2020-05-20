package com.example.groceryhero.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.example.groceryhero.R
import com.example.groceryhero.app.Config
import com.example.groceryhero.database.DBHelper
import com.example.groceryhero.helper.setupToolbar
import com.example.groceryhero.helper.toast
import com.example.groceryhero.model.Products
import com.example.groceryhero.model.ProductsDB
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.view.*
import kotlinx.android.synthetic.main.layout_cart_badge.view.*
import kotlinx.android.synthetic.main.row_layout_cart.view.*

class ProductDetailActivity : AppCompatActivity(), View.OnClickListener {
    var mList:ArrayList<ProductsDB> = ArrayList()
    var quantity:Int = 0
    lateinit  var db:DBHelper
    var textViewCartCount: TextView? = null
    lateinit var item:Products


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)


        init()
    }

    private fun init() {
        db = DBHelper()
        item = intent.getSerializableExtra("data") as Products
        quantity = db.itemInCartQuantity(item.productName)
        text_view_title.text = item.productName
        text_view_description.text = item.description
        text_view_description.movementMethod = ScrollingMovementMethod()
        text_view_price.text = "Price: ₹${item.price.toString()}"
        text_view_qty.text = quantity.toString()


        Picasso
            .get()
            .load(Config.IMAGE_URL + item.image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(image_view_product)

        button_add_cart.setOnClickListener(this)
        button_add_quantity.setOnClickListener(this)
        button_minus_quantity.setOnClickListener(this)

        this.setupToolbar(item.productName)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {finish()}
            R.id.menu_cart -> {startActivity(Intent(this,CartActivity::class.java))}
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        var item = menu.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_cart_badge)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_count_badge
        updateCartCount()

        view.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
        return super.onCreateOptionsMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add_quantity -> {
                quantity += 1
                text_view_qty.text = "${quantity.toString()}"
                updateCartCount()
            }
            R.id.button_minus_quantity -> {
                if(quantity > 1) {
                    quantity -= 1
                    text_view_qty.text = "${quantity.toString()}"
                    updateCartCount()
                } else {
                    db.deleteProduct(item.productName)
                    updateCartCount()
                }
            }
            R.id.button_add_cart -> {
                var item: Products = intent.getSerializableExtra("data") as Products
               if(quantity != 0 && db.isItemInCart(item.productName) == false) {
                   var name = item.productName
                   var price = item.price.toString()
                   var image = item.image
                   var mrp = item.mrp
                   var productItems = ProductsDB(name, price,image, quantity, mrp)
                   db.addProduct(productItems,quantity)
                   this.toast("Item added to cart")

               } else if(db.isItemInCart(item.productName)) {
                   db.updateProduct(item.productName,quantity + db.itemInCartQuantity(item.productName))
                   this.toast("Item updated in cart")
               } else {
                   this.toast("Item not added to cart, please update quantity")
               }
                updateCartCount()

            }
        }
    }

    fun updateCartCount() {
        var db = DBHelper()
        var items = db.getTotalQuantity()
        if(items == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = items.toString()
        }
    }

    override fun onStart() {
        updateCartCount()
        super.onStart()
    }


}
