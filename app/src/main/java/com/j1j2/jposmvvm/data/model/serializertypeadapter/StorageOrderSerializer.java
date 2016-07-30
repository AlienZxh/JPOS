package com.j1j2.jposmvvm.data.model.serializertypeadapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.j1j2.jposmvvm.data.model.StorageOrder;

import java.lang.reflect.Type;

/**
 * Created by alienzxh on 16-7-28.
 */
public class StorageOrderSerializer implements JsonSerializer<StorageOrder> {
    @Override
    public JsonElement serialize(StorageOrder src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("OrderId", src.getOrderId());
        jsonObject.addProperty("ClerkId", src.getClerkId());
        jsonObject.addProperty("Amount", src.getAmount());
        jsonObject.addProperty("CreateOrderSubmitTimeStr", src.getCreateOrderSubmitTimeStr());
        jsonObject.addProperty("OrderNO", src.getOrderNO());
        jsonObject.addProperty("OtherAmount", src.getOtherAmount());
        jsonObject.addProperty("Remark", src.getRemark());
        jsonObject.addProperty("ShopId", src.getShopId());
        jsonObject.addProperty("State", src.getState());
        jsonObject.addProperty("SubmitTime", src.getSubmitTime());
        jsonObject.addProperty("SubmitTimeStr", src.getSubmitTimeStr());
        jsonObject.addProperty("SupplerId", src.getSupplerId());
        jsonObject.addProperty("SupplerName", src.getSupplerName());
        return jsonObject;
    }
}
