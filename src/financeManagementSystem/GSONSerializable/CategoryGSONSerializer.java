package financeManagementSystem.GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import financeManagementSystem.model.Category;

import java.lang.reflect.Type;

public class CategoryGSONSerializer implements JsonSerializer<Category> {
    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        String dateCreated = category.getDateCreated().toString();
        String dateModified = category.getDateModified().toString();
        JsonObject catJson = new JsonObject();
        catJson.addProperty("name", category.getName());
        catJson.addProperty("description", category.getDescription());
        catJson.addProperty("dateCreated", dateCreated);
        catJson.addProperty("dateModified", dateModified);
        catJson.addProperty("catId", category.getCatId());
        catJson.addProperty("parentID", category.getParentID());

        return catJson;
    }
}
