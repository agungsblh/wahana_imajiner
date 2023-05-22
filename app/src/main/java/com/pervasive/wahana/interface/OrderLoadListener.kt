package com.pervasive.wahana.`interface`

import com.pervasive.wahana.model.OrderModel

interface OrderLoadListener {
    fun onLoadOrderSuccess(orderModelList:List<OrderModel>)
    fun onLoadOrderFailed(message:String?)
}