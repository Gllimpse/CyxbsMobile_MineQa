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
     * 第一次点击邮货详情界面的兑换按钮
     */
    const val SHOP_DETAIL_DIALOG_FIRST_EXCHANGE = 0
    /**
     * 兑换成功
     */
    const val SHOP_DETAIL_DIALOG_EXCHANGE_SUCCESS = 1
    /**
     * 兑换失败
     */
    const val SHOP_DETAIL_DIALOG_EXCHANGE_FAIL = 2
    /**
     * 兑换请求失败
     */
    const val SHOP_DETAIL_DIALOG_EXCHANGE_REQUEST_FAIL = 3
    /**
     * 兑换请求结果 -- 兑换失败
     */
    const val SHOP_DETAIL_DIALOG_STAMP_SHORTAGE = 5

    // 任务类型
    /**
     * 每日任务
     */
    const val SHOP_TASK_TYPE_TODAY = "base"
    /**
     * 更多任务
     */
    const val SHOP_TASK_TYPE_MORE = "more"
    // 任务界面RecyclerItem类型
    /**
     * 标题Item
     */
    const val SHOP_TASK_ITEM_TYPE_TITLE = 0
    /**
     * 任务Item
     */
    const val SHOP_TASK_ITEM_TYPE_TASK = 1

    var SHOP_CHILD_TOP = 0

    // 状态码Status
    const val SHOP_STATUS_GET_EXCHANGE_INFO_SUCCESS = 10000
    const val SHOP_STATUS_GET_GET_INFO_SUCCESS = 10000
}