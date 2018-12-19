package work.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import work.controller.EntityNotFoundException;
import work.service.IService;
import work.view.OrganizationView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final IService<OrganizationView, Integer> organizationService;

    @Autowired
    public OrganizationController(IService<OrganizationView, Integer> organizationService) {
        this.organizationService = organizationService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void organization(@RequestBody OrganizationView organization) {
        organizationService.add(organization);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrganizationView> organizations() {
        return organizationService.findAll();
    }

    /**
     * Найти организацию по уникальному идентификатору id
     * @param orgIdentifier id
     * @return OrganizationView
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrganizationView organizationById(@PathVariable("id") String orgIdentifier) {
        if (!orgIdentifier.matches("[\\d]+")) {
            throw new EntityNotFoundException("Could not find organization " + orgIdentifier);
        }
        int id = Integer.parseInt(orgIdentifier);
        OrganizationView orgView = organizationService.findById(id);
        if(orgView == null) {
            throw new EntityNotFoundException("Could not find organization " + id);
        }
        return orgView;
    }
}
