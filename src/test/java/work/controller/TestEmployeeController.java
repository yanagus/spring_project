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
import work.view.EmployeeView;
import work.view.PositionView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestEmployeeController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void validateEmployeeById() throws Exception {
        String employee1 = "{\"data\":{\"id\":\"1\",\"firstName\":\"Иван\",\"secondName\":null,\"middleName\":null," +
                "\"position\":\"менеджер\",\"phone\":\"+7(927)111-11-11\"," +
                "\"docName\":\"Паспорт гражданина Российской Федерации\",\"docNumber\":\"6305 454552\"," +
                "\"docDate\":\"2007-05-25\",\"citizenshipName\":\"Российская Федерация\"," +
                "\"citizenshipCode\":\"643\",\"isIdentified\":false}}";
        mockMvc.perform(get("/api/user/1")).andExpect(status().isOk())
                .andExpect(content().json(employee1));
        String employee2 = "{\"data\":{\"id\":\"2\",\"firstName\":\"Петр\",\"secondName\":null,\"middleName\":null," +
                "\"position\":\"менеджер\",\"phone\":\"+7(917)000-00-00\",\"docName\":\"Паспорт гражданина Российской Федерации\"," +
                "\"docNumber\":\"6305 454356\",\"docDate\":\"2017-02-03\",\"citizenshipName\":\"Российская Федерация\"," +
                "\"citizenshipCode\":\"643\",\"isIdentified\":false}}";
        mockMvc.perform(get("/api/user/2")).andExpect(status().isOk())
                .andExpect(content().json(employee2));
        mockMvc.perform(get("/api/user/3")).andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").value("работник с id 3 не найден"));
    }

    @Test
    public void testEmployeeById() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/user/1");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Wrapper<EmployeeView>> responseEntity = restTemplate.exchange("/api/user/1", HttpMethod.GET,
                entity, new ParameterizedTypeReference<Wrapper<EmployeeView>>() {});
        Wrapper<EmployeeView> wrapper = responseEntity.getBody();
        PositionView positionView = new PositionView("менеджер");
        EmployeeView employeeView = new EmployeeView("1", "Иван", null, null,
                null, "+7(927)111-11-11",false, positionView, null, null);
        Assert.assertEquals(employeeView, wrapper.getData());
        Assert.assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    @Test(expected = HttpClientErrorException.class)
    public void testEmployeeBadId() {
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        HttpHeaders headers = restTemplate.headForHeaders("/api/user/4");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<ErrorWrapper<ResponseMessage>> responseEntity = restTemplate.exchange("/api/user/4", HttpMethod.GET,
                entity, new ParameterizedTypeReference<ErrorWrapper<ResponseMessage>>() {});
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        ErrorWrapper<ResponseMessage> error = responseEntity.getBody();
        ResponseMessage responseMessage = new ResponseMessage("работник с id 4 не найден");
        ErrorWrapper<ResponseMessage> error2 = new ErrorWrapper<>(responseMessage);
        Assert.assertEquals(error2, error);
    }
}
