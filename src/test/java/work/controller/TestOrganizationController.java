package work.controller;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import work.Application;
import work.view.OrganizationView;

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
    public void validateOrganization1() throws Exception {
        mockMvc.perform(get("/organization/1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value("Орг"))
                .andExpect(jsonPath("$.fullName").value("Организация"))
                .andExpect(jsonPath("$.inn").value("0123456789"))
                .andExpect(jsonPath("$.kpp").value("123456789"))
                .andExpect(jsonPath("$.phone").value("+7(845)222-22-22"));
    }

    @Test
    public void validateOrganization2() throws Exception {
        mockMvc.perform(get("/organization/2")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name").value("OCS"))
                .andExpect(jsonPath("$.fullName").value("OCS Distribution"))
                .andExpect(jsonPath("$.inn").value("1234567890"))
                .andExpect(jsonPath("$.kpp").value("123456789"))
                .andExpect(jsonPath("$.phone").value("+7 (495) 995-2575"));
    }

    @Test
    public void validateOrganization3() throws Exception {
        mockMvc.perform(get("/organization/hh")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("Not found organization with id is hh"));
    }

    @Test
    public void testOfficeById1() {
        RestTemplate restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OrganizationView organizationView1 = restTemplate.getForObject("/organization/1", OrganizationView.class);
        OrganizationView organizationView2 = new OrganizationView("1", "Орг", "Организация",
                "0123456789","123456789", "+7(845)222-22-22","г. Саратов", false);
        assertEquals(organizationView1, organizationView2);
        HttpHeaders headers = restTemplate.headForHeaders("/organization/1");
        assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testOfficeById2() {
        RestTemplate restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
        OrganizationView organizationView1 = restTemplate.getForObject("/organization/2", OrganizationView.class);
        OrganizationView organizationView2 = new OrganizationView("2", "OCS", "OCS Distribution",
                "1234567890","123456789", "+7 (495) 995-2575",
                "108811, г. Москва, Киевское шоссе, Румянцево, офисный парк «Комсити» д.6 стр.1", false);
        assertEquals(organizationView1, organizationView2);
        HttpHeaders headers = restTemplate.headForHeaders("/organization/1");
        assertTrue(headers.getContentType().includes(MediaType.APPLICATION_JSON));
    }
}
