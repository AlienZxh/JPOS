package com.j1j2.jposmvvm.data.model.serializertypeadapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryUser;

import java.lang.reflect.Type;

/**
 * Created by alienzxh on 16-8-2.
 */
public class CashPuzzyQueryUserSerializer implements JsonSerializer<CashPuzzyQueryUser> {

    @Override
    public JsonElement serialize(CashPuzzyQueryUser src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Name", src.getName());
        jsonObject.addProperty("Balance", src.getBalance());
        jsonObject.addProperty("CostSum", src.getCostSum());
        jsonObject.addProperty("Mobile", src.getMobile());
        jsonObject.addProperty("Points", src.getPoints());
        jsonObject.addProperty("SaveSum", src.getSaveSum());
        jsonObject.addProperty("UserId", src.getUserId());

        return jsonObject;
    }
}
