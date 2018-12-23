package work.controller.employee;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import work.controller.EntityNotFoundException;
import work.service.IService;
import work.view.EmployeeView;
import work.view.Views;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
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
     * @param employeeIdentifier id работника
     * @return EmployeeView
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(Views.GetByIdView.class)
    public EmployeeView employeeById(@PathVariable("id") String employeeIdentifier) {
        if (!employeeIdentifier.matches("[\\d]+")) {
            throw new EntityNotFoundException("работник с id " + employeeIdentifier + " не найден");
        }
        int id = Integer.parseInt(employeeIdentifier);
        EmployeeView employeeView = employeeService.findById(id);
        if(employeeView == null) {
            throw new EntityNotFoundException("работник с id " + id + " не найден");
        }
        return employeeView;
    }
}
