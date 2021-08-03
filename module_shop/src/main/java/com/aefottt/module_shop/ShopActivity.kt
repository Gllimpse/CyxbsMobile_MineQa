package com.aefottt.module_shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.mredrock.cyxbs.common.config.SHOP_ENTRY

@Route(path= SHOP_ENTRY)
class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)
        Toast.makeText(this, "这是一个Toast", Toast.LENGTH_SHORT).show()
    }
}