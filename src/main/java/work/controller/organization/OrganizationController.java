package work.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import work.controller.EntityAlreadyExistException;
import work.controller.EntityNotFoundException;
import work.service.IService;
import work.view.OrganizationView;
import work.view.ResponseView;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final IService<OrganizationView, Integer> organizationService;

    @Autowired
    public OrganizationController(IService<OrganizationView, Integer> organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * Сохранить новую организацию в базе данных
     * @param organization новая организация
     * @return экземпляр типа ResponseView с сообщением об успешном сохранении новой организации
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseView organization(@Valid @RequestBody OrganizationView organization) {
        List<OrganizationView> currentOrganizations = organizations();
        for (OrganizationView organizationView : currentOrganizations){
            if (organization.getInn().equals(organizationView.getInn())){
                throw new EntityAlreadyExistException("организация с таким ИНН уже существует в базе данных");
            }
        }
        organizationService.add(organization);
        return new ResponseView("success");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrganizationView> organizations() {
        return organizationService.findAll();
    }

    /**
     * Найти организацию по уникальному идентификатору id
     * @param organizationIdentifier id организации
     * @return экземпляр типа OrganizationView
     */
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
