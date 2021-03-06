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
import work.model.DocumentData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Тест работы DAO
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
     * Тест метода получения сущностей по id и связанных списков
     */
    @Test
    public void testLoadById() {
        Organization organization = new Organization("Орг", "Организация", "0123456789",
                "123456789", "+7(845)222-22-22", "г. Саратов", null);
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

        Set<Office> offices = organization2.getOffices();
        Assert.assertNotNull(offices);
        Assert.assertEquals(1, offices.size());

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
                null, position, country, office, null);
        employee.setId(1);
        employeeDao.setClazz(Employee.class);
        Employee employee2 = employeeDao.loadById(1);
        Assert.assertEquals(employee, employee2);

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date parsingDate = null;
        try {
            parsingDate = ft.parse("2007-05-25");
        }catch (ParseException e) {
            System.out.println("Нераспаршена с помощью " + ft);
        }
        DocumentData documentData = new DocumentData(document, "6305 454552", parsingDate, employee);
        documentData.setId(1);
        DocumentData documentData2 = employee2.getDocumentData();
        Assert.assertEquals(documentData, documentData2);

        Set<Employee> employees = office2.getEmployees();
        Assert.assertNotNull(employees);
        Assert.assertEquals(1, employees.size());

    }

    /**
     * Тест методов сохранения и изменения сущностей
     */
    @Test
    public void testSaveUpdateLoadByParameters() {
        Organization organization = new Organization("Новая", "Новая Организация", "0123456700",
                "123456780", null, "г. Саратов", true);
        organizationDao.setClazz(Organization.class);
        organizationDao.save(organization);
        organization.setId(3);
        Organization organization2 = organizationDao.loadById(3);
        Assert.assertEquals(organization, organization2);

        Organization orgParam = new Organization();
        orgParam.setName("Новая");
        List<Organization> organizations = organizationDao.loadByParametersList(orgParam);
        Assert.assertNotNull(organizations);
        Assert.assertEquals(1, organizations.size());

        organization.setPhone("322-2233");
        organizationDao.update(organization);
        organization2 = organizationDao.loadById(3);
        Assert.assertEquals(organization, organization2);

        Office office = new Office("Новый офис", null, "г. Саратов",
                true, organization);
        officeDao.setClazz(Office.class);
        officeDao.save(office);
        office.setId(3);
        Office office2 = officeDao.loadById(3);
        Assert.assertEquals(office, office2);

        office.setName("new");
        officeDao.update(office);
        office2 = officeDao.loadById(3);
        Assert.assertEquals(office, office2);

        Position position = new Position("продавец");
        Country country = new Country("643", "Российская Федерация");
        Document document = new Document("21", "Паспорт гражданина Российской Федерации");
        Employee employee = new Employee("Екатерина", null, null, "Иванова", null,
                null, position, country, office, null);

        employeeDao.setClazz(Employee.class);
        employeeDao.save(employee);
        position.setId((short) 2);
        country.setId((short) 1);
        document.setId((byte) 1);
        employee.setId(3);
        Employee employee2 = employeeDao.loadById(3);
        Assert.assertEquals(employee, employee2);

        Employee emplParam = new Employee();
        emplParam.setOffice(office);
        emplParam.setPosition(position);
        List<Employee> employees = employeeDao.loadByParametersList(emplParam);
        Assert.assertNotNull(employees);
        Assert.assertEquals(1, employees.size());

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date parsingDate = null;
        try {
            parsingDate = ft.parse("2015-11-25");
        }catch (ParseException e) {
            System.out.println("Нераспаршена с помощью " + ft);
        }
        DocumentData documentData = new DocumentData(document, "6305 454552", parsingDate, employee);
        employee2.setDocumentData(documentData);
        documentData.setId(3);
        employee.setDocumentData(documentData);
        Assert.assertEquals(employee.getDocumentData(), employee2.getDocumentData());

    }

    /**
     * Тест методов получения списков справочников
     */
    @Test
    public void testCatalogs() {
        countryDao.setClazz(Country.class);
        List<Country> countries = countryDao.all();
        Assert.assertNotNull(countries);
        Assert.assertEquals(1, countries.size());

        documentDao.setClazz(Document.class);
        List<Document> documents = documentDao.all();
        Assert.assertNotNull(documents);
        Assert.assertEquals(1, documents.size());
    }

}
