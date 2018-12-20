package work.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import work.Application;
import work.model.Organization;
import work.model.Office;
import work.model.Employee;
import work.model.Position;
import work.model.Country;
import work.model.Document;

/**
 * Тест методов работы с базой данных
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
@Transactional
@DirtiesContext
public class TestDao {

    @Autowired
    private IGenericDao<Organization, Integer> organizationDao;

    @Autowired
    private IGenericDao<Office, Integer> officeDao;

    @Autowired
    private IGenericDao<Employee, Integer> employeeDao;

    @Autowired
    private IGenericDao<Position, Short> positionDao;

    @Autowired
    private IGenericDao<Country, Short> countryDao;

    @Autowired
    private IGenericDao<Document, Byte> documentDao;


    /**
     * Тест метода получения сущностей по id
     */
    @Test
    public void testLoadById() {
        Organization organization = new Organization("Орг", "Организация", "0123456789",
                "123456789", "+7(845)222-22-22", "г. Саратов", false);
        organization.setId(1);
        organizationDao.setClazz(Organization.class);
        Organization organization2 = organizationDao.loadById(1);
        Assert.assertEquals(organization, organization2);

        Office office = new Office("Офис Организации", "+7(845)222-22-33", "г. Саратов, пр. Кирова",
                true, organization);
        office.setId(1);
        officeDao.setClazz(Office.class);
        Office office2 = officeDao.loadById(1);
        Assert.assertEquals(office, office2);

        Position position = new Position("менеджер");
        position.setId((short) 1);
        positionDao.setClazz(Position.class);
        Position position2 = positionDao.loadById((short) 1);
        Assert.assertEquals(position, position2);

        Country country = new Country("643", "Российская Федерация");
        country.setId((short) 1);
        countryDao.setClazz(Country.class);
        Country country2 = countryDao.loadById((short) 1);
        Assert.assertEquals(country, country2);

        Document document = new Document("21", "Паспорт гражданина Российской Федерации");
        document.setId((byte) 1);
        documentDao.setClazz(Document.class);
        Document document2 = documentDao.loadById((byte) 1);
        Assert.assertEquals(document, document2);

        Employee employee = new Employee("Иван", null, null, "Иванов", "+7(927)111-11-11",
                false, position, country, office);
        employee.setId(1);
        employeeDao.setClazz(Employee.class);
        Employee employee2 = employeeDao.loadById(1);
        Assert.assertEquals(employee, employee2);
    }
}
