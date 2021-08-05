package com.mredrock.cyxbs.shop.pages.stampcenter.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aefottt.module_shop.R
import com.alibaba.android.arouter.facade.annotation.Route
import com.mredrock.cyxbs.common.config.SHOP_ENTRY
import com.mredrock.cyxbs.common.ui.BaseViewModelActivity
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.ShopViewModel

@Route(path= SHOP_ENTRY)
class ShopActivity : BaseViewModelActivity<ShopViewModel>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_main)

    }
}