package work.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import work.Application;
import work.view.OfficeView;
import work.view.ResponseView;
import work.view.inputView.OfficeViewRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест контроллера офиса
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestOfficeController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private RestTemplate restTemplate;

    /**
     * Инициализировать MockMvc до выполнения тестов
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Проверка валидации JSON
     *
     * @throws Exception
     */
    @Test
    public void validateOfficeById() throws Exception {
        String office1 = "{\"data\":{\"id\":\"1\",\"name\":\"Офис Организации\",\"address\":\"г. Саратов, пр. Кирова\"," +
                "\"phone\":\"+7(845)222-22-33\",\"isActive\":\"true\"}}";
        mockMvc.perform(get("/api/office/1")).andExpect(status().isOk())
                .andExpect(content().json(office1));
        String office2 = "{\"data\":{\"id\":\"2\",\"name\":\"OCS Саратов\"," +
                "\"address\":\"410004, Саратов, Ул. Чернышевского, 60/62, офис 903\"," +
                "\"phone\":\"8-800-555-3-999\",\"isActive\":\"true\"}}";
        mockMvc.perform(get("/api/office/2")).andExpect(status().isOk())
                .andExpect(content().json(office2));
        mockMvc.perform(get("/api/office/3")).andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").value("офис с id 3 не найден"));
    }

    /**
     * Получить офис по id
     */
    @Test
    public void testOfficeById() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/office/1");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Wrapper<OfficeView>> responseEntity = restTemplate.exchange("/api/office/1", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<OfficeView>>() {});
        Wrapper<OfficeView> wrapper = responseEntity.getBody();

        OfficeView officeView = new OfficeView("1", "Офис Организации", "+7(845)222-22-33",
                "г. Саратов, пр. Кирова","true", null, null);

        Assert.assertEquals(officeView, wrapper.getData());
        Assert.assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    /**
     * Получить ответ при неверно введенном id офиса
     */
    @Test(expected = HttpClientErrorException.class)
    public void testOfficeBadId() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/office/bb");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/office/bb",
                HttpMethod.GET, entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMessage = new ResponseMessage("офис с id bb не найден");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMessage);
        Assert.assertEquals(error2, error);
    }

    /**
     * Сохранить новый офис и получить его
     */
    @Test
    public void testPostAndGetNewOffice() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OfficeViewRequest office = new OfficeViewRequest();
        office.setName("OCS Самара");
        office.setAddress("Самара");
        office.setIsActive("TRUE");
        HttpEntity<OfficeViewRequest> entity = new HttpEntity<>(office);
        ResponseEntity<Wrapper<ResponseView>> responseEntity = restTemplate.exchange("/api/office/save",
                HttpMethod.POST, entity, new ParameterizedTypeReference<Wrapper<ResponseView>>() {});
        Wrapper<ResponseView> wrapper = responseEntity.getBody();
        Assert.assertEquals(new ResponseView("success"), wrapper.getData());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());

        ResponseEntity<Wrapper<OfficeView>> responseEntityId3 = restTemplate.exchange("/api/office/3",
                HttpMethod.GET, entity, new ParameterizedTypeReference<Wrapper<OfficeView>>() {});
        OfficeView officeView = new OfficeView("3", "OCS Самара", null, "Самара", "true",
                null, null);
        Assert.assertEquals(officeView, responseEntityId3.getBody().getData());
    }

    /**
     * Получить ответ обработчика ошибок при неверных входных данных офиса
     */
    @Test(expected = HttpClientErrorException.class)
    public void testPostNewNotValidOffice() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OfficeViewRequest office = new OfficeViewRequest();
        office.setOrgId("1.3");
        office.setName("OCS Самара");
        HttpEntity<OfficeViewRequest> entity = new HttpEntity<>(office);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/office/save",
                HttpMethod.POST, entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMsg =
                new ResponseMessage("организация с id 1.3 не найдена");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMsg);
        Assert.assertEquals(error2, error);
    }

    /**
     * Обновить существующий офис
     */
    @Test
    public void testUpdateOffice() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OfficeViewRequest office = new OfficeViewRequest();
        office.setId("1");
        office.setName("New");
        office.setAddress("г. Саратов");
        HttpEntity<OfficeViewRequest> entity = new HttpEntity<>(office);
        ResponseEntity<Wrapper<ResponseView>> responseEntity = restTemplate.exchange("/api/office/update",
                HttpMethod.POST, entity, new ParameterizedTypeReference<Wrapper<ResponseView>>() {});
        Wrapper<ResponseView> wrapper = responseEntity.getBody();
        Assert.assertEquals(new ResponseView("success"), wrapper.getData());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        ResponseEntity<Wrapper<OfficeView>> responseChangedEntity = restTemplate.exchange("/api/office/1",
                HttpMethod.GET, entity, new ParameterizedTypeReference<Wrapper<OfficeView>>() {});
        Wrapper<OfficeView> responseWrapper = responseChangedEntity.getBody();
        OfficeView officeView = new OfficeView("1", "New", "+7(845)222-22-33",
                "г. Саратов","true", null, null);
        Assert.assertEquals(officeView, responseWrapper.getData());
    }

    /**
     * Получить список офисов по фильтру
     */
    @Test
    public void testOfficeList() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OfficeViewRequest office = new OfficeViewRequest();
        office.setOrgId("1");
        office.setName("ОФИС ОРГАНИЗАЦИИ");
        HttpEntity<OfficeViewRequest> entity = new HttpEntity<>(office);
        ResponseEntity<Wrapper<List<OfficeView>>> responseEntity = restTemplate.exchange("/api/office/list",
                HttpMethod.POST, entity, new ParameterizedTypeReference<Wrapper<List<OfficeView>>>() {});
        Wrapper<List<OfficeView>> wrapper = responseEntity.getBody();

        OfficeView officeView = new OfficeView("1", "Офис Организации", null,
                null, "true", null, null);

        Assert.assertEquals(1, wrapper.getData().size());
        Assert.assertEquals(officeView, wrapper.getData().get(0));
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
