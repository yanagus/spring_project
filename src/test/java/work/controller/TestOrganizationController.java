package work.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import work.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class TestOrganizationController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void validateOrganizationById() throws Exception {
        String organization1 = "{\"data\":{\"id\":\"1\",\"name\":\"Орг\",\"fullName\":\"Организация\"," +
                "\"inn\":\"0123456789\",\"kpp\":\"123456789\",\"address\":\"г. Саратов\",\"phone\":\"+7(845)222-22-22\"," +
                "\"isActive\":false}}";
        mockMvc.perform(get("/organization/1")).andExpect(status().isOk())
                .andExpect(content().json(organization1));
        String organization2 = "{\"data\":{\"id\":\"2\",\"name\":\"OCS\",\"fullName\":\"OCS Distribution\"," +
                "\"inn\":\"1234567890\",\"kpp\":\"123456789\"," +
                "\"address\":\"108811, г. Москва, Киевское шоссе, Румянцево, офисный парк «Комсити» д.6 стр.1\"," +
                "\"phone\":\"+7 (495) 995-2575\",\"isActive\":false}}";
        mockMvc.perform(get("/organization/2")).andExpect(status().isOk())
                .andExpect(content().json(organization2));
        mockMvc.perform(get("/organization/hh")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").value("Could not find organization hh"));
    }

}
