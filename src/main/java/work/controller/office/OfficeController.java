package work.controller.office;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import work.controller.EntityNotFoundException;
import work.controller.organization.OrganizationController;
import work.service.IService;
import work.view.OfficeView;
import work.view.OrganizationView;
import work.view.ResponseView;
import work.view.Views;
import work.view.inputView.OfficeViewRequest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для офисов
 */
@RestController
@RequestMapping(value = "/api/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {

    private final IService<OfficeView, Integer> officeService;
    private final IService<OrganizationView, Integer> organizationService;


    @Autowired
    public OfficeController(IService<OfficeView, Integer> officeService, IService<OrganizationView, Integer> organizationService) {
        this.officeService = officeService;
        this.organizationService = organizationService;
    }

    /**
     * Сохранить новый офис в базе данных
     *
     * @param office данные нового офиса
     * @return экземпляр типа ResponseView с сообщением об успешном сохранении нового офиса
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseStatus(HttpStatus.CREATED) ResponseView saveOffice(@Validated(Views.SaveView.class) @RequestBody OfficeViewRequest office) {
        OfficeView officeView = new OfficeView(office);
        if (office.getOrgId() != null) {
            officeView.setOrganization(checkOrganization(office.getOrgId()));
        }
        officeService.add(officeView);
        return new ResponseView("success");
    }

    /**
     * Обновить данные офиса
     *
     * @param office обновлённые данные
     * @return экземпляр типа ResponseView с сообщением об успешном обновлении текущей организации
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseView updateOffice(@Validated(Views.UpdateView.class) @RequestBody OfficeViewRequest office) {
        officeById(office.getId());
        OfficeView officeView = new OfficeView(office);
        if (office.getOrgId() != null) {
            officeView.setOrganization(checkOrganization(office.getOrgId()));
        }
        officeService.update(officeView);
        return new ResponseView("success");
    }

    // для тестирования - получить все офисы
    @RequestMapping(value = "/list/all", method = RequestMethod.GET)
    public List<OfficeView> offices() {
        return officeService.findAll();
    }

    /**
     * Получить список офисов по фильтру
     *
     * @param office фильтр
     * @return список офисов
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @JsonView(Views.FilteredList.class)
    public List<OfficeView> officesByFilter(@Validated(Views.FilteredList.class) @RequestBody OfficeViewRequest office) {
        OrganizationView organization = checkOrganization(office.getOrgId());
        if (office.getName() == null && office.getPhone() == null && office.getIsActive() == null) {
            return new ArrayList<>(organization.getOffices());
        }

        OfficeView officeView = new OfficeView(office);
        officeView.setOrganization(organization);
        return officeService.findByParametersList(officeView);
    }

    /**
     * Найти офис по уникальному идентификатору id
     *
     * @param officeIdentifier id офиса
     * @return OfficeView
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(Views.GetByIdView.class)
    public OfficeView officeById(@PathVariable("id") String officeIdentifier) {
        if (!officeIdentifier.matches("[\\d]+")) {
            throw new EntityNotFoundException("офис с id " + officeIdentifier + " не найден");
        }
        int id = Integer.parseInt(officeIdentifier);
        OfficeView officeView = officeService.findById(id);
        if(officeView == null) {
            throw new EntityNotFoundException("офис с id " + id + " не найден");
        }
        return officeView;
    }

    private OrganizationView checkOrganization(String orgId) {
        OrganizationController controller = new OrganizationController(organizationService);
        return controller.organizationById(orgId);
    }
}
