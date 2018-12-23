package work.controller.office;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import work.controller.EntityNotFoundException;
import work.service.IService;
import work.view.OfficeView;
import work.view.Views;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {

    private final IService<OfficeView, Integer> officeService;

    @Autowired
    public OfficeController(IService<OfficeView, Integer> officeService) {
        this.officeService = officeService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void office(@RequestBody OfficeView office) {
        officeService.add(office);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OfficeView> offices() {
        return officeService.findAll();
    }

    /**
     * Найти офис по уникальному идентификатору id
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
}
