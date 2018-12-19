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
import work.view.OfficeView;
import work.view.OrganizationView;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestOfficeController {

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
        OfficeView officeView1 = restTemplate.getForObject("/office/1", OfficeView.class);
        OfficeView officeView2 = new OfficeView("1", "Офис", "+7 (495) 322-2233",
                "г. Москва", true, new OrganizationView("1", "Орг", "Организация", "0123456789",
                "123456789", "+7(845)222-22-22", "г. Саратов", false));
        assertEquals(officeView1, officeView2);
        HttpHeaders headers = restTemplate.headForHeaders("/office/1");
        assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testOfficeById2() {
        String message = restTemplate.getForObject("/office/3", String.class);
        String response = "{\"error\":\"Could not find office 3\"}";
        assertEquals(message, response);
    }
}
