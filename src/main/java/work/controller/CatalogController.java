package work.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import work.service.IService;
import work.view.CountryView;
import work.view.DocumentView;
import work.view.Views;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер для справочников
 */
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class CatalogController {

    private final IService<DocumentView, Byte> documentService;

    private final IService<CountryView, Short> countryService;

    /**
     * Конструктор для справочников
     *
     * @param documentService сервис справочника документов
     * @param countryService сервис справочника стран
     */
    @Autowired
    public CatalogController(IService<DocumentView, Byte> documentService, IService<CountryView, Short> countryService) {
        this.documentService = documentService;
        this.countryService = countryService;
    }

    /**
     * Получить список документов из справочника
     *
     * @return список документов
     */
    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    @JsonView(Views.ListView.class)
    public List<DocumentView> documents() {
        return documentService.findAll();
    }

    /**
     * Получить список стран из справочника
     *
     * @return список стран
     */
    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    @JsonView(Views.ListView.class)
    public List<CountryView> countries() {
        return countryService.findAll();
    }
}
