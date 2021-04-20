package financeManagementSystem.GSONSerializable;

import com.google.gson.*;
import financeManagementSystem.model.Category;

import java.lang.reflect.Type;
import java.util.List;

public class AllCategorySONSerializer implements JsonSerializer<List<Category>> {
    @Override
    public JsonElement serialize(List<Category> allCats, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gsonBuilder.create();

        for (Category c : allCats) {
            jsonArray.add(parser.toJson(c));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
