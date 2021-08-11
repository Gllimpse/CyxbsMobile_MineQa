package com.mredrock.cyxbs.shop.config

object ShopConfig {

    // 商品类型
    /**
     * 邮货
     */
    const val SHOP_GOOD_TYPE_STAMP_GOOD = 0
    /**
     * 装饰
     */
    const val SHOP_GOOD_TYPE_DECORATION = 1

    // 共享元素动画名称
    /**
     * DetailActivity中点击图片过渡到ImageActivity
     */
    const val SHOP_TRANSITION_DETAIL_IMAGE = "shop_detail_trans_name_vp_pic"
    const val SHOP_TRANSITION_GOOD_TO_DETAIL = "shop_center_trans_good_to_detail"

    // Dialog类型
    /**
     * 点击邮货详情界面的兑换按钮
     */
    const val SHOP_DIALOG_TYPE_FIRST_SURE_STAMP = 0
    /**
     * 点击装饰详情界面的兑换按钮
     */
    const val SHOP_DIALOG_TYPE_FIRST_SURE_DECORATION = 1
    /**
     * 兑换请求成功
     */
    const val SHOP_DIALOG_TYPE_SUCCESS = 2
    /**
     * 兑换请求结果 -- 邮票不足
     */
    const val SHOP_DIALOG_TYPE_STAMP_SHORTAGE = 3
    /**
     * 兑换请求结果 -- 货存不足
     */
    const val SHOP_DIALOG_TYPE_COUNT_SHORTAGE = 4
    /**
     * 兑换请求结果 -- 兑换失败
     */
    const val SHOP_DIALOG_TYPE_FAIL = 5

    // 任务类型
    /**
     * 每日任务
     */
    const val SHOP_TASK_TYPE_TODAY = 0
    /**
     * 更多任务
     */
    const val SHOP_TASK_TYPE_MORE = 1
    // 任务界面RecyclerItem类型
    /**
     * 标题Item
     */
    const val SHOP_TASK_ITEM_TYPE_TITLE = 0
    /**
     * 任务Item
     */
    const val SHOP_TASK_ITEM_TYPE_TASK = 1
}