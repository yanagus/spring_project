package work.controller.employee;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import work.controller.EntityAlreadyExistException;
import work.controller.EntityNotFoundException;
import work.service.IService;
import work.view.EmployeeView;
import work.view.OfficeView;
import work.view.ResponseView;
import work.view.Views;
import work.view.PositionView;
import work.view.DocumentDataView;
import work.view.DocumentView;
import work.view.CountryView;
import work.view.inputView.EmployeeViewRequest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для работников
 */
@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final IService<EmployeeView, Integer> employeeService;

    private final IService<OfficeView, Integer> officeService;

    /**
     * Конструктор контроллера
     *
     * @param employeeService сервис работника
     * @param officeService сервис офиса
     */
    @Autowired
    public EmployeeController(IService<EmployeeView, Integer> employeeService, IService<OfficeView, Integer> officeService) {
        this.employeeService = employeeService;
        this.officeService = officeService;
    }

    /**
     * Сохранить нового работника в базе данных
     *
     * @param employee данные нового работника
     * @return экземпляр типа ResponseView с сообщением об успешном сохранении нового работника
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseStatus(HttpStatus.CREATED) ResponseView saveEmployee(@Validated(Views.SaveView.class) @RequestBody EmployeeViewRequest employee) {
        if (employee.getDocNumber() != null) {
            EmployeeView employeeByDocNumber = employeeService.findByParameter(employee.getDocNumber().trim());
            if (employeeByDocNumber != null) {
                throw new EntityAlreadyExistException("работник с такими паспортными данными существует");
            }
        }

        EmployeeView employeeView = new EmployeeView(employee);

        if (employee.getOfficeId() != null) {
            OfficeView officeView = checkOffice(employee.getOfficeId());
            employeeView.setOffice(officeView);
        }

        PositionView position = new PositionView(employee.getPosition());
        employeeView.setPosition(position);

        if (employee.getDocCode() != null || employee.getDocName() != null) {
            DocumentView documentView = new DocumentView(employee.getDocCode(), employee.getDocName());
            if (employee.getDocNumber() != null) {
                DocumentDataView documentDataView = new DocumentDataView(documentView, employee.getDocNumber(), employee.getDocDate(), employeeView);
                employeeView.setDocumentData(documentDataView);
            }
        }

        if (employee.getCitizenshipCode() != null) {
            CountryView countryView = new CountryView(employee.getCitizenshipCode());
            employeeView.setCountry(countryView);
        }

        employeeService.add(employeeView);
        return new ResponseView("success");
    }

    /**
     * Обновить данные работника
     *
     * @param employee обновлённые данные
     * @return экземпляр типа ResponseView с сообщением об успешном обновлении данных работника
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseView updateOrganization(@Validated(Views.UpdateView.class) @RequestBody EmployeeViewRequest employee) {
        employeeById(employee.getId());

        if (employee.getDocNumber() != null) {
            EmployeeView employeeByDocNumber = employeeService.findByParameter(employee.getDocNumber().trim());
            if (employeeByDocNumber != null && !employee.getId().equals(employeeByDocNumber.getId())) {
                throw new EntityAlreadyExistException("работник с такими паспортными данными существует, его id = " + employeeByDocNumber.getId());
            }
        }

        EmployeeView employeeView = new EmployeeView(employee);

        if (employee.getOfficeId() != null) {
            OfficeView officeView = checkOffice(employee.getOfficeId());
            employeeView.setOffice(officeView);
        }

        PositionView position = new PositionView(employee.getPosition());
        employeeView.setPosition(position);

        if (employee.getDocName() != null) {
            DocumentView documentView = new DocumentView(null, employee.getDocName());
            if (employee.getDocNumber() != null) {
                DocumentDataView documentDataView = new DocumentDataView(documentView, employee.getDocNumber(), employee.getDocDate(), employeeView);
                employeeView.setDocumentData(documentDataView);
            }
        }

        if (employee.getCitizenshipCode() != null) {
            CountryView countryView = new CountryView(employee.getCitizenshipCode());
            employeeView.setCountry(countryView);
        }

        employeeService.update(employeeView);
        return new ResponseView("success");
    }

    // для тестирования - получить все офисы
    @RequestMapping(value = "/list/all", method = RequestMethod.GET)
    public List<EmployeeView> employees() {
        return employeeService.findAll();
    }

    /**
     * Найти список работников по офису и фильтрам
     *
     * @param employee входные данные для поиска
     * @return список работников
     */
    @JsonView(Views.FilteredList.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<EmployeeView> employeesByFilter(@Validated(Views.FilteredList.class) @RequestBody EmployeeViewRequest employee) {
        OfficeView officeView = checkOffice(employee.getOfficeId());
        if (employee.getFirstName() == null && employee.getLastName() == null && employee.getMiddleName() == null &&
                employee.getPosition() == null && employee.getDocCode() == null && employee.getCitizenshipCode() == null) {
            return new ArrayList<>(officeView.getEmployees());
        }
        EmployeeView employeeView = new EmployeeView(employee);
        employeeView.setOffice(officeView);

        if (employee.getPosition() != null) {
            PositionView position = new PositionView(employee.getPosition());
            employeeView.setPosition(position);
        }

        if (employee.getDocCode() != null) {
            DocumentView documentView = new DocumentView(employee.getDocCode(), null);
            DocumentDataView documentDataView = new DocumentDataView(documentView, null, null, employeeView);
            employeeView.setDocumentData(documentDataView);
        }

        if (employee.getCitizenshipCode() != null) {
            CountryView countryView = new CountryView(employee.getCitizenshipCode());
            employeeView.setCountry(countryView);
        }

        return employeeService.findByParametersList(employeeView);
    }

    /**
     * Найти работника по уникальному идентификатору id
     *
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

    private OfficeView checkOffice (String officeId) {
        if (!officeId.matches("[\\d]+")) {
            throw new EntityNotFoundException("офис с id " + officeId + " не найден");
        }
        int id = Integer.parseInt(officeId);
        OfficeView officeView = officeService.findById(id);
        if(officeView == null) {
            throw new EntityNotFoundException("офис с id " + id + " не найден");
        }
        return officeView;
    }
}
