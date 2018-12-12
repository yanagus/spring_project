package work.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import work.Application;
import work.view.EmployeeView;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

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
        restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
    }

    @Test
    public void testOfficeById() {
        EmployeeView employeeView1 = restTemplate.getForObject("/user/2", EmployeeView.class);
        EmployeeView employeeView2 = new EmployeeView("2", "Иван", "Иванович", "", "Иванов",
                "+7 (495) 995-2575", false, "1", "1", "2");;
        assertEquals(employeeView1, employeeView2);
        HttpHeaders headers = restTemplate.headForHeaders("/user/2");
        assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testOfficeById2() {
        String message = restTemplate.getForObject("/user/3", String.class);
        String response = "{\"message\":\"Not found employee with id is 3\"}";
        assertEquals(message, response);
    }
}
