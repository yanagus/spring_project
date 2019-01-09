package work.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import work.service.IService;
import work.view.OrganizationView;
import work.view.ResponseView;
import work.view.Views;
import work.view.inputView.OrganizationViewRequest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для организаций
 */
@RestController
@RequestMapping(value = "/api/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final IService<OrganizationView, Integer> organizationService;

    /**
     * Конструктор контроллера
     *
     * @param organizationService сервис организации
     */
    @Autowired
    public OrganizationController(IService<OrganizationView, Integer> organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * Сохранить новую организацию в базе данных
     *
     * @param organization новая организация
     * @return экземпляр типа ResponseView с сообщением об успешном сохранении новой организации
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseStatus(HttpStatus.CREATED) ResponseView organization(@Validated(Views.SaveView.class) @RequestBody OrganizationViewRequest organization) {
        OrganizationView organizationByInn = organizationService.findByParameter(organization.getInn().trim());
        if (organizationByInn != null) {
            throw new EntityAlreadyExistException("организация с таким ИНН уже существует в базе данных");
        }
        OrganizationView organizationView = new OrganizationView(organization);
        organizationService.add(organizationView);
        return new ResponseView("success");
    }

    /**
     * Обновить данные организации
     *
     * @param organization обновлённые данные
     * @return экземпляр типа ResponseView с сообщением об успешном обновлении текущей организации
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseView updateOrganization(@Validated(Views.UpdateView.class) @RequestBody OrganizationViewRequest organization) {
        organizationById(organization.getId());
        OrganizationView organizationByInn = organizationService.findByParameter(organization.getInn().trim());
        if (organizationByInn != null && !organization.getId().equals(organizationByInn.getId())) {
            throw new EntityAlreadyExistException("организация с таким ИНН уже существует в базе данных, её id = " + organizationByInn.getId());
        }
        OrganizationView organizationView = new OrganizationView(organization);
        organizationService.update(organizationView);
        return new ResponseView("success");
    }

    /**
     * Получить список организаций по фильтру
     *
     * @param organization фильтр
     * @return список организаций
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @JsonView(Views.FilteredList.class)
    public List<OrganizationView> organizationsByFilter(@Validated(Views.FilteredList.class) @RequestBody OrganizationViewRequest organization) {
        OrganizationView organizationView = new OrganizationView(organization);
        return organizationService.findByParametersList(organizationView);
    }

    /**
     * Найти организацию по уникальному идентификатору id
     *
     * @param organizationIdentifier id организации
     * @return экземпляр типа OrganizationView
     */
    @JsonView(Views.GetByIdView.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrganizationView organizationById(@PathVariable("id") String organizationIdentifier) {
        if (!organizationIdentifier.matches("[\\d]+")) {
            throw new EntityNotFoundException("организация с id " + organizationIdentifier + " не найдена");
        }
        int id = Integer.parseInt(organizationIdentifier);
        OrganizationView orgView = organizationService.findById(id);
        if(orgView == null) {
            throw new EntityNotFoundException("организация с id " + id + " не найдена");
        }
        return orgView;
    }
}
