package com.example.ibrahim.chatddemo.models;

import android.support.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * By ramya on 10/6/16.
 */
public class BaseModel implements Serializable {


    @Nullable
    public JSONObject getJsonRequestObject() {
        Gson gson = new Gson();
        JSONObject object = new JSONObject();
        try {
            object = new JSONObject(gson.toJson(this));
        } catch (Exception ignored) {

        }
        return object;
    }

    public String asString() {
        Gson gson = new Gson();
        JSONObject object = new JSONObject();
        try {
            object = new JSONObject(gson.toJson(this));
        } catch (Exception ignored) {

        }
        return object.toString();
    }
}
