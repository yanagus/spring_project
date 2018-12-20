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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestOfficeController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void validateOfficeById() throws Exception {
        String office1 = "{\"data\":{\"id\":\"1\",\"name\":\"Офис Организации\",\"address\":\"г. Саратов, пр. Кирова\"," +
                "\"phone\":\"+7(845)222-22-33\",\"isActive\":true}}";
        mockMvc.perform(get("/office/1")).andExpect(status().isOk())
                .andExpect(content().json(office1));
        String office2 = "{\"data\":{\"id\":\"2\",\"name\":\"OCS Саратов\"," +
                "\"address\":\"410004, Саратов, Ул. Чернышевского, 60/62, офис 903\"," +
                "\"phone\":\"8-800-555-3-999\",\"isActive\":true}}";
        mockMvc.perform(get("/office/2")).andExpect(status().isOk())
                .andExpect(content().json(office2));
        mockMvc.perform(get("/office/3")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").value("Could not find office 3"));
    }
}
