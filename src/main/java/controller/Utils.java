package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.ModelAndView;
import spark.Request;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.lang.reflect.Type;
import java.util.Map;

public class Utils {
    private static Utils ourInstance = null;

    private Utils() {
    }

    public static Utils getInstance() {
        if (ourInstance == null) {
            ourInstance = new Utils();
        }
        return ourInstance;
    }

    public Map<String, String> parseJson(Request request) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();

        return gson.fromJson(request.body(), type);
    }

    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public String renderTemplate(Map model, String template) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, template));
    }

}