package work.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import work.Application;
import work.view.CountryView;
import work.view.DocumentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Тест контроллера справочников
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestCatalogController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private RestTemplate restTemplate;

    /**
     * Инициализировать RestTemplate до выполнения тестов
     */
    @Before
    public void setUp() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
    }

    /**
     * Тест вывода списка стран, хранящихся в справочнике
     */
    @Test
    public void testCountriesList() {
        HttpHeaders headers = restTemplate.headForHeaders("/api/countries");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Wrapper<List<CountryView>>> responseEntity = restTemplate.exchange("/api/countries", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<List<CountryView>>>() {});
        Wrapper<List<CountryView>> wrapper = responseEntity.getBody();

        CountryView country = new CountryView("643", "Российская Федерация");
        List<CountryView> countryList = new ArrayList<>();
        countryList.add(country);

        Assert.assertEquals(countryList, wrapper.getData());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    /**
     * Тест вывода списка документов, хранящихся в справочнике
     */
    @Test
    public void testDocumentsList() {
        HttpHeaders headers = restTemplate.headForHeaders("/api/docs");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Wrapper<List<DocumentView>>> responseEntity = restTemplate.exchange("/api/docs", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<List<DocumentView>>>() {});
        Wrapper<List<DocumentView>> wrapper = responseEntity.getBody();

        DocumentView document = new DocumentView("21", "Паспорт гражданина Российской Федерации");
        List<DocumentView> documentList = new ArrayList<>();
        documentList.add(document);

        Assert.assertEquals(documentList, wrapper.getData());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
