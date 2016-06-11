package com.j1j2.jposmvvm.data.model.serializertypeadapter;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.j1j2.jposmvvm.data.model.ShopInfo;

import java.lang.reflect.Type;

/**
 * Created by alienzxh on 16-5-23.
 */
public class ShopInfoSerializer implements JsonSerializer<ShopInfo> {
    @Override
    public JsonElement serialize(ShopInfo src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ShopId", src.getShopId());
        jsonObject.addProperty("ShopName", src.getShopName());
        jsonObject.addProperty("ClerkAccount", src.getClerkAccount());
        jsonObject.addProperty("ClerkId", src.getClerkId());
        jsonObject.addProperty("ClerkName", src.getClerkName());
        jsonObject.addProperty("IsAdmin", src.isAdmin());
        return jsonObject;
    }


}
