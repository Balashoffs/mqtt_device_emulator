package com.balashoff.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JsonAnalyzer<T> {

    private final Gson gson = new Gson();

    public <T> T fromJsonC(String json, Class<?> mainClass, Class<?>... subClass) {
        Type type = TypeToken.getParameterized(mainClass, subClass).getType();
        return gson.fromJson(json, type);
    }

    public <T> String toJsonC(T object, Class<?> mainClass, Class<?>... subClass) {
        Type type = TypeToken.getParameterized(mainClass, subClass).getType();
        return gson.toJson(object, type);
    }

    public <T> T fromJsonT(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public <T> String toJsonT(T object, Type type) {
        return gson.toJson(object, type);
    }
}
