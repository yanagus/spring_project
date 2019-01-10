package work.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import work.Application;
import work.view.CountryView;
import work.view.EmployeeView;
import work.view.PositionView;
import work.view.ResponseView;
import work.view.inputView.EmployeeViewRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест контроллера работника
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestEmployeeController {

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
    public void validateEmployeeById() throws Exception {
        String employee1 = "{\"data\":{\"id\":\"1\",\"firstName\":\"Иван\",\"secondName\":null,\"middleName\":null," +
                "\"position\":\"менеджер\",\"phone\":\"+7(927)111-11-11\"," +
                "\"docName\":\"Паспорт гражданина Российской Федерации\",\"docNumber\":\"6305 454552\"," +
                "\"docDate\":\"2007-05-25\",\"citizenshipName\":\"Российская Федерация\"," +
                "\"citizenshipCode\":\"643\",\"isIdentified\":null}}";
        mockMvc.perform(get("/api/user/1")).andExpect(status().isOk())
                .andExpect(content().json(employee1));
        String employee2 = "{\"data\":{\"id\":\"2\",\"firstName\":\"Петр\",\"secondName\":null,\"middleName\":null," +
                "\"position\":\"менеджер\",\"phone\":\"+7(917)000-00-00\",\"docName\":\"Паспорт гражданина Российской Федерации\"," +
                "\"docNumber\":\"6305 454356\",\"docDate\":\"2017-02-03\",\"citizenshipName\":\"Российская Федерация\"," +
                "\"citizenshipCode\":\"643\",\"isIdentified\":null}}";
        mockMvc.perform(get("/api/user/2")).andExpect(status().isOk())
                .andExpect(content().json(employee2));
        mockMvc.perform(get("/api/user/3")).andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").value("работник с id 3 не найден"));
    }

    /**
     * Получить работника по id
     */
    @Test
    public void testEmployeeById() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/user/1");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Wrapper<EmployeeView>> responseEntity = restTemplate.exchange("/api/user/1", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<EmployeeView>>() {});
        Wrapper<EmployeeView> wrapper = responseEntity.getBody();

        PositionView positionView = new PositionView("менеджер");
        CountryView countryView = new CountryView("643", "Российская Федерация");
        EmployeeView employeeView = new EmployeeView("1", "Иван", null, null,
                null, "+7(927)111-11-11", null, positionView, null, null, null);
        Assert.assertEquals(employeeView, wrapper.getData());
        employeeView.setCountry(countryView);
        Assert.assertEquals(employeeView.getPositionName(), wrapper.getData().getPositionName());
        Assert.assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    /**
     * Получить ответ при неверно введенном id работника
     */
    @Test(expected = HttpClientErrorException.class)
    public void testEmployeeBadId() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/user/4");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/user/4",
                HttpMethod.GET, entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMessage = new ResponseMessage("работник с id 4 не найден");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMessage);
        Assert.assertEquals(error2, error);
    }

    /**
     * Сохранить нового работника и получить его
     */
    @Test
    public void testPostAndGetNewEmployee() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        EmployeeViewRequest employee = new EmployeeViewRequest();
        employee.setFirstName("Екатерина");
        employee.setPosition("продавец");
        employee.setPhone("322-223");
        employee.setIsIdentified("true");
        HttpEntity<EmployeeViewRequest> entity = new HttpEntity<>(employee);
        ResponseEntity<Wrapper<ResponseView>> responseEntity = restTemplate.exchange("/api/user/save",
                HttpMethod.POST, entity, new ParameterizedTypeReference<Wrapper<ResponseView>>() {});
        Wrapper<ResponseView> wrapper = responseEntity.getBody();
        Assert.assertEquals(new ResponseView("success"), wrapper.getData());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());

        ResponseEntity<Wrapper<EmployeeView>> responseEntityId3 = restTemplate.exchange("/api/user/3", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<EmployeeView>>() {});
        EmployeeView employeeResponse = new EmployeeView("3", "Екатерина", null,
                null, null, "322-223", "true",
                new PositionView("продавец"), null, null, null);
        Assert.assertEquals(employeeResponse, responseEntityId3.getBody().getData());
    }

    /**
     * Получить ответ обработчика ошибок при неверных входных данных работника
     */
    @Test(expected = HttpClientErrorException.class)
    public void testPostNewNotValidEmployee() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        EmployeeViewRequest employee = new EmployeeViewRequest();
        employee.setOfficeId("2");
        employee.setFirstName("Екатерина");
        employee.setPosition("продавец");
        employee.setDocCode("21");
        employee.setDocNumber("6305 454552");
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date parsingDate = null;
        try {
            parsingDate = ft.parse("2007-05-25");
        }catch (ParseException e) {
            System.out.println("Нераспаршена с помощью " + ft);
        }
        employee.setDocDate(parsingDate);
        HttpEntity<EmployeeViewRequest> entity = new HttpEntity<>(employee);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/office/save",
                HttpMethod.POST, entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMsg =
                new ResponseMessage("работник с такими паспортными данными существует");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMsg);
        Assert.assertEquals(error2, error);
    }

    /**
     * Обновить существующего работника
     */
    @Test
    public void testUpdateEmployee() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        EmployeeViewRequest employee = new EmployeeViewRequest();
        employee.setId("1");
        employee.setFirstName("Иван");
        employee.setPosition("ведущий менеджер");
        HttpEntity<EmployeeViewRequest> entity = new HttpEntity<>(employee);
        ResponseEntity<Wrapper<ResponseView>> responseEntity = restTemplate.exchange("/api/user/update",
                HttpMethod.POST, entity, new ParameterizedTypeReference<Wrapper<ResponseView>>() {});
        Wrapper<ResponseView> wrapper = responseEntity.getBody();
        Assert.assertEquals(new ResponseView("success"), wrapper.getData());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        ResponseEntity<Wrapper<EmployeeView>> responseChangedEntity = restTemplate.exchange("/api/user/1",
                HttpMethod.GET, entity, new ParameterizedTypeReference<Wrapper<EmployeeView>>() {});
        Wrapper<EmployeeView> responseWrapper = responseChangedEntity.getBody();
        EmployeeView employeeResponse = new EmployeeView("1", "Иван", null, null,
                null, "+7(927)111-11-11", null,
                new PositionView("ведущий менеджер"), null, null, null);
        Assert.assertEquals(employeeResponse, responseWrapper.getData());
    }

    /**
     * Получить ответ обработчика ошибок при неверных входных данных работника
     */
    @Test(expected = HttpClientErrorException.class)
    public void testNotValidUpdateEmployee() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        EmployeeViewRequest employee = new EmployeeViewRequest();
        employee.setOfficeId("2");
        employee.setId("1");
        employee.setFirstName("Иван");
        employee.setPosition("ведущий менеджер");
        employee.setDocName("Паспорт гражданина Российской Федерации");
        employee.setDocNumber("6305 454356");
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date parsingDate = null;
        try {
            parsingDate = ft.parse("2007-05-25");
        }catch (ParseException e) {
            System.out.println("Нераспаршена с помощью " + ft);
        }
        employee.setDocDate(parsingDate);
        HttpEntity<EmployeeViewRequest> entity = new HttpEntity<>(employee);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/office/update",
                HttpMethod.POST, entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMsg =
                new ResponseMessage("работник с такими паспортными данными существует, его id = 2");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMsg);
        Assert.assertEquals(error2, error);
    }

    /**
     * Получить список работников по фильтру
     */
    @Test
    public void testOfficeList() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        EmployeeViewRequest employee = new EmployeeViewRequest();
        employee.setOfficeId("1");
        HttpEntity<EmployeeViewRequest> entity = new HttpEntity<>(employee);
        ResponseEntity<Wrapper<List<EmployeeView>>> responseEntity = restTemplate.exchange("/api/user/list",
                HttpMethod.POST, entity, new ParameterizedTypeReference<Wrapper<List<EmployeeView>>>() {});
        Wrapper<List<EmployeeView>> wrapper = responseEntity.getBody();

        EmployeeView employeeView = new EmployeeView("1", "Иван", null, null,
                null, null, null,
                new PositionView("менеджер"), null, null, null);

        Assert.assertEquals(1, wrapper.getData().size());
        Assert.assertEquals(employeeView, wrapper.getData().get(0));
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        employee.setPosition("продавец");
        responseEntity = restTemplate.exchange("/api/user/list", HttpMethod.POST,
                entity, new ParameterizedTypeReference<Wrapper<List<EmployeeView>>>() {});
        wrapper = responseEntity.getBody();

        Assert.assertEquals(0, wrapper.getData().size());
    }
}
