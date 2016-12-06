package com.j1j2.jposmvvm.data.model.serializertypeadapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryStock;

import java.lang.reflect.Type;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashPuzzyQueryStockSerializer implements JsonSerializer<CashPuzzyQueryStock> {
    @Override
    public JsonElement serialize(CashPuzzyQueryStock src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("BarCode", src.getBarCode());
        jsonObject.addProperty("Name", src.getName());
        jsonObject.addProperty("RetailPrice", src.getRetailPrice());
        jsonObject.addProperty("MemberPrice", src.getMemberPrice());
        jsonObject.addProperty("SmallImgUrl", src.getSmallImgUrl());
        jsonObject.addProperty("Spec", src.getSpec());
        jsonObject.addProperty("Unit", src.getUnit());
        jsonObject.addProperty("StockId", src.getStockId());
        return jsonObject;
    }
}
