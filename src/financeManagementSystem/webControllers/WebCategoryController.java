/*
package financeManagementSystem.webControllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import financeManagementSystem.GSONSerializable.AllCategorySONSerializer;
import financeManagementSystem.GSONSerializable.CategoryGSONSerializer;
import financeManagementSystem.model.Category;
import financeManagementSystem.utils.CategoryDataBaseManagement;
import financeManagementSystem.utils.UserDataBaseManagement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/category")

public class WebCategoryController {

    public WebCategoryController() throws SQLException, ClassNotFoundException {
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    String getCategoryData(@RequestParam int userId) {
        List<Category> allCats = CategoryDataBaseManagement.getAllCategories();

        String[] responsible;
        ArrayList<Category> filteredCats = new ArrayList<Category>();

        if (UserDataBaseManagement.getResponsibleCategories(userId) != null) {
            responsible = UserDataBaseManagement.getResponsibleCategories(userId).split(":");
            for (Category cat : allCats) {
                for (String res : responsible) {
                    if (cat.getCatId() == Integer.parseInt(res)) {
                        filteredCats.add(cat);
                        //break;
                    }
                }
            }
        }


        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(filteredCats.get(0));

        Type categoryList = new TypeToken<List<Category>>() {
        }.getType();
        gson.registerTypeAdapter(categoryList, new AllCategorySONSerializer());
        parser = gson.create();

        return parser.toJson(filteredCats);
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody
    String deleteCategoryData(@RequestParam int catId) throws SQLException {

        CategoryDataBaseManagement.deleteCategory(catId);
        return "Category Deleted Successfully";
    }

    @PutMapping(path = "/updatename")
    public @ResponseBody
    String updateCategoryName(@RequestParam String name, @RequestParam String catId) throws SQLException {

        CategoryDataBaseManagement.updateCategoryName(name, catId);
        return "Name Updated Successfully";
    }

    @PutMapping(path = "/updatedescription")
    public @ResponseBody
    String updateCategoryDescription(@RequestParam String description, @RequestParam String catId) throws SQLException {

        CategoryDataBaseManagement.updateCategoryDescription(description, catId);
        return "Decription Updated Successfully";
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    String addCategoryData(@RequestParam String name, @RequestParam String description, @RequestParam String parentID) throws SQLException {

        if (parentID.equals(""))
            CategoryDataBaseManagement.insertCategory(new Category(name, description, CategoryDataBaseManagement.getCategoryIdCount(), LocalDate.now(), LocalDate.now(), "null"));
        else
            CategoryDataBaseManagement.insertCategory(new Category(name, description, CategoryDataBaseManagement.getCategoryIdCount(), LocalDate.now(), LocalDate.now(), parentID));

        CategoryDataBaseManagement.addCategoryIdCount();

        return "Category inserted Successfully";
    }

    @GetMapping(path = "/getbyid")
    public @ResponseBody
    String getCategoryById(@RequestParam String catId) {

        return CategoryDataBaseManagement.getCategoryById(catId).toString();
    }
}
*/
