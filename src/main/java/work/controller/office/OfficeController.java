package work.controller.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import work.controller.EntityNotFoundException;
import work.service.office.OfficeService;
import work.view.OfficeView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void office(@RequestBody OfficeView office) {
        officeService.add(office);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OfficeView> offices() {
        return officeService.offices();
    }

    /**
     * Найти офис по уникальному идентификатору id
     * @param orgIdentifier id
     * @return OfficeView
     */
    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.GET)
    public OfficeView officeById(@PathVariable("id") String orgIdentifier) {
        if (!orgIdentifier.matches("[\\d]+")) {
            throw new EntityNotFoundException("Not found office with id is " + orgIdentifier);
        }
        int id = Integer.parseInt(orgIdentifier);
        OfficeView officeView = officeService.findById(id);
        if(officeView == null) {
            throw new EntityNotFoundException("Not found office with id is " + id);
        }
        return officeView;
    }
}