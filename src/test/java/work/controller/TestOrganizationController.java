package work.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import work.Application;
import work.view.OrganizationView;
import work.view.ResponseView;
import work.view.inputView.OrganizationViewRequest;

import java.util.List;

/**
 * Тест контроллера организации
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestOrganizationController {

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
    public void validateOrganizationById() throws Exception {
        String organization1 = "{\"data\":{\"id\":\"1\",\"name\":\"Орг\",\"fullName\":\"Организация\"," +
                "\"inn\":\"0123456789\",\"kpp\":\"123456789\",\"address\":\"г. Саратов\",\"phone\":\"+7(845)222-22-22\"," +
                "\"isActive\":null}}";
        mockMvc.perform(get("/api/organization/1")).andExpect(status().isOk())
                .andExpect(content().json(organization1));
        String organization2 = "{\"data\":{\"id\":\"2\",\"name\":\"OCS\",\"fullName\":\"OCS Distribution\"," +
                "\"inn\":\"1234567890\",\"kpp\":\"123456789\"," +
                "\"address\":\"108811, г. Москва, Киевское шоссе, Румянцево, офисный парк «Комсити» д.6 стр.1\"," +
                "\"phone\":\"+7 (495) 995-2575\",\"isActive\":null}}";
        mockMvc.perform(get("/api/organization/2")).andExpect(status().isOk())
                .andExpect(content().json(organization2));
        mockMvc.perform(get("/api/organization/hh")).andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").value("организация с id hh не найдена"));
    }

    /**
     * Получить организацию по id
     */
    @Test
    public void testOrganizationById() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/organization/2");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Wrapper<OrganizationView>> responseEntity = restTemplate.exchange("/api/organization/2", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<OrganizationView>>() {});
        Wrapper<OrganizationView> wrapper = responseEntity.getBody();
        OrganizationView organizationView = new OrganizationView("2", "OCS", "OCS Distribution",
                "1234567890","123456789",
                "108811, г. Москва, Киевское шоссе, Румянцево, офисный парк «Комсити» д.6 стр.1",
                "+7 (495) 995-2575", null, null);
        Assert.assertEquals(organizationView, wrapper.getData());
        Assert.assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    /**
     * Получить ответ при неверно введенном id
     */
    @Test(expected = HttpClientErrorException.class)
    public void testOrganizationBadId() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/organization/1.3");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/organization/1.3", HttpMethod.GET,
                entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMsg = new ResponseMessage("организация с id 1.3 не найдена");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMsg);
        Assert.assertEquals(error2, error);
    }

    /**
     * Сохранить новую организацию и получить её
     */
    @Test
    public void testPostAndGetNewOrganization() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OrganizationViewRequest organization = new OrganizationViewRequest();
        organization.setName("название");
        organization.setFullName("полн.название");
        organization.setInn(" 1111111111");
        organization.setKpp("000000000");
        organization.setAddress("Москва");
        organization.setIsActive("TRUE");
        HttpEntity<OrganizationViewRequest> entity = new HttpEntity<>(organization);
        ResponseEntity<Wrapper<ResponseView>> responseEntity = restTemplate.exchange("/api/organization/save", HttpMethod.POST,
                entity, new ParameterizedTypeReference<Wrapper<ResponseView>>() {});
        Wrapper<ResponseView> wrapper = responseEntity.getBody();
        Assert.assertEquals(new ResponseView("success"), wrapper.getData());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());

        ResponseEntity<Wrapper<OrganizationView>> responseEntityId3 = restTemplate.exchange("/api/organization/3", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<OrganizationView>>() {});
        OrganizationView organizationView = new OrganizationView(null, "название", "полн.название", "1111111111", "000000000",
                "Москва", null, "true", null);
        organizationView.setId("3");
        Assert.assertEquals(organizationView, responseEntityId3.getBody().getData());
    }

    /**
     * Получить ответ обработчика ошибок при неверных входных данных организации
     */
    @Test(expected = HttpClientErrorException.class)
    public void testPostNewNotValidOrganization() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OrganizationView organizationView = new OrganizationView(null, "", "полн.название",
                "0123456789", "000000000","8 800 000 00 00", null, "true", null);
        HttpEntity<OrganizationView> entity = new HttpEntity<>(organizationView);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/organization/save",
                HttpMethod.POST, entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMsg =
                new ResponseMessage("введите название организации, организация с таким ИНН уже существует в базе данных, введите адрес организации");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMsg);
        Assert.assertEquals(error2, error);
    }

    /**
     * Обновить существующую организацию
     */
    @Test
    public void testUpdateOrganization() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OrganizationViewRequest organization = new OrganizationViewRequest();
        organization.setId("2");
        organization.setName("New Org");
        organization.setFullName("New Org");
        organization.setInn("1234567777");
        organization.setKpp("123456780");
        organization.setAddress("108811, г. Москва, Киевское шоссе, Румянцево, офисный парк «Комсити» д.6 стр.1");
        organization.setPhone("0000");
        HttpEntity<OrganizationViewRequest> entity = new HttpEntity<>(organization);
        ResponseEntity<Wrapper<ResponseView>> responseEntity = restTemplate.exchange("/api/organization/update", HttpMethod.POST,
                entity, new ParameterizedTypeReference<Wrapper<ResponseView>>() {});
        Wrapper<ResponseView> wrapper = responseEntity.getBody();
        Assert.assertEquals(new ResponseView("success"), wrapper.getData());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        ResponseEntity<Wrapper<OrganizationView>> responseChangedEntity = restTemplate.exchange("/api/organization/2", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<OrganizationView>>() {});
        Wrapper<OrganizationView> responseWrapper = responseChangedEntity.getBody();
        OrganizationView organizationResponse = new OrganizationView("2", "New Org", "New Org",
                "1234567777","123456780",
                "108811, г. Москва, Киевское шоссе, Румянцево, офисный парк «Комсити» д.6 стр.1",
                "0000", null, null);
        Assert.assertEquals(organizationResponse, responseWrapper.getData());
    }

    /**
     * Получить список организаций по фильтру
     */
    @Test
    public void testOrganizationsList() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OrganizationViewRequest organization = new OrganizationViewRequest();
        organization.setName("ОРГ");
        organization.setInn(" 0123456789 ");
        HttpEntity<OrganizationViewRequest> entity = new HttpEntity<>(organization);
        ResponseEntity<Wrapper<List<OrganizationView>>> responseEntity = restTemplate.exchange("/api/organization/list", HttpMethod.POST,
                entity, new ParameterizedTypeReference<Wrapper<List<OrganizationView>>>() {});
        Wrapper<List<OrganizationView>> wrapper = responseEntity.getBody();

        OrganizationView organizationView = new OrganizationView("1", "Орг", null,
                null,null, null, null, null, null);

        Assert.assertEquals(1, wrapper.getData().size());
        Assert.assertEquals(organizationView, wrapper.getData().get(0));
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
