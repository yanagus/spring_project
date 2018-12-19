package work.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import work.controller.EntityNotFoundException;
import work.service.IService;
import work.view.EmployeeView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final IService<EmployeeView, Integer> employeeService;

    @Autowired
    public EmployeeController(IService<EmployeeView, Integer> employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void employee(@RequestBody EmployeeView employee) {
        //return "Вызван метод добавления работника";
        employeeService.add(employee);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<EmployeeView> employees() {
        return employeeService.findAll();
    }

    /**
     * Найти работника по уникальному идентификатору id
     * @param orgIdentifier id
     * @return EmployeeView
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EmployeeView employeeById(@PathVariable("id") String orgIdentifier) {
        if (!orgIdentifier.matches("[\\d]+")) {
            throw new EntityNotFoundException("Could not find employee " + orgIdentifier);
        }
        int id = Integer.parseInt(orgIdentifier);
        EmployeeView employeeView = employeeService.findById(id);
        if(employeeView == null) {
            throw new EntityNotFoundException("Could not find employee " + id);
        }
        return employeeView;
    }
}
