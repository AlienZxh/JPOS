package com.j1j2.jposmvvm.data.model.serializertypeadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.lzh.framework.updatepluginlib.model.Update;

import java.lang.reflect.Type;

/**
 * Created by alienzxh on 16-6-10.
 */
public class UpdateDeserializer implements JsonDeserializer<Update> {
    @Override
    public Update deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Update update = new Update(json.getAsString());
        JsonObject jsonObject = json.getAsJsonObject();
        // 此apk包的更新时间
        update.setUpdateTime(System.currentTimeMillis());
        // 此apk包的下载地址
        update.setUpdateUrl(jsonObject.get("ApkDownloadUrl").getAsString());
        // 此apk包的版本号
        update.setVersionCode(jsonObject.get("VersionTag").getAsInt());
        // 此apk包的版本名称
        update.setVersionName(jsonObject.get("NewVersionName").getAsString());
        // 此apk包的更新内容
        update.setUpdateContent(jsonObject.get("UpdatedContents").getAsString());
        // 此apk包是否为强制更新
        update.setForced(jsonObject.get("ForceUpdate").getAsBoolean());
        // 是否忽略此次版本更新
        update.setIgnore(jsonObject.get("Invalid").getAsBoolean());
        return update;
    }
}
