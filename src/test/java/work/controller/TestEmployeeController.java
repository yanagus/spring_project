package work.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import work.Application;

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
        mockMvc.perform(get("/user/1")).andExpect(status().isOk())
                .andExpect(content().json(employee1));
        String employee2 = "{\"data\":{\"id\":\"2\",\"firstName\":\"Петр\",\"secondName\":null,\"middleName\":null," +
                "\"position\":\"менеджер\",\"phone\":\"+7(917)000-00-00\",\"docName\":\"Паспорт гражданина Российской Федерации\"," +
                "\"docNumber\":\"6305 454356\",\"docDate\":\"2017-02-03\",\"citizenshipName\":\"Российская Федерация\"," +
                "\"citizenshipCode\":\"643\",\"isIdentified\":false}}";
        mockMvc.perform(get("/user/2")).andExpect(status().isOk())
                .andExpect(content().json(employee2));
        mockMvc.perform(get("/user/3")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").value("Could not find employee 3"));
    }
}
