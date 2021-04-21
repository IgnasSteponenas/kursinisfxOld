/*
package financeManagementSystem.webControllers;

import com.google.gson.Gson;
import financeManagementSystem.model.Company;
import financeManagementSystem.model.Individual;
import financeManagementSystem.utils.UserDataBaseManagement;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@Controller
@RequestMapping(path = "/user")

public class WebUserController {
    public WebUserController() {
    }


    @RequestMapping(value = "/individualLogin", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String individualLogin(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String loginName = data.getProperty("login");
        String password = data.getProperty("psw");
        Individual individual = UserDataBaseManagement.findIndividual(loginName, password);

        if (individual == null) {
            return "Wrong credentials";
        }

        return String.valueOf(individual.getId());
    }

    @RequestMapping(value = "/companyLogin", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String companyLogin(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String loginName = data.getProperty("login");
        String password = data.getProperty("psw");
        Company company = UserDataBaseManagement.findCompany(loginName, password);

        if (company == null) {
            return "Wrong credentials";
        }

        return String.valueOf(company.getId());
    }

}
*/
