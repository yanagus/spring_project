package work.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import work.model.Employee;
import work.model.Position;
import work.model.Office;
import work.model.Country;
import work.model.Document;
import work.model.DocumentData;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO для работы с Employee
 */
@Repository
public class EmployeeDaoImpl extends AbstractDao<Employee, Integer> {

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Конструктор для EntityManager
     *
     * @param entityManager EntityManager
     */
    @Autowired
    public EmployeeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Сохранить нового работника
     *
     * @param entity работник
     */
    @Override
    public void save(Employee entity) {

        Employee newEmployee = new Employee(entity.getFirstName(), entity.getSecondName(), entity.getMiddleName(), entity.getLastName(),
                entity.getPhone(), entity.getIsIdentified(), null, null, null, null);

        if (entity.getOffice() != null) {
            newEmployee.setOffice(entityManager.find(Office.class, entity.getOffice().getId()));
        }

        Position position = loadByPositionName(entity.getPosition().getName());
        if (position == null) {
            position = new Position(entity.getPosition().getName());
            entityManager.persist(position);
        }
        newEmployee.setPosition(position);

        if (entity.getCountry() != null) {
            Country country = loadByCitizenshipCode(entity.getCountry().getCode());
            if (country == null) {
                country = new Country(entity.getCountry().getCode(), entity.getCountry().getName());
                entityManager.persist(country);
            }
            newEmployee.setCountry(country);
        }

        entityManager.persist(newEmployee);

        if (entity.getDocumentData() != null) {
            if (entity.getDocumentData().getDocument() != null) {
                if (entity.getDocumentData().getDocument().getCode() != null) {
                    Document document = loadByDocCode(entity.getDocumentData().getDocument().getCode());
                    if (document == null) {
                        document = new Document(entity.getDocumentData().getDocument().getCode(), entity.getDocumentData().getDocument().getName());
                        entityManager.persist(document);
                    }
                    if (entity.getDocumentData().getNumber() != null) {
                        DocumentData documentData = new DocumentData(document, entity.getDocumentData().getNumber(), entity.getDocumentData().getDate(), newEmployee);
                        entityManager.persist(documentData);
                    }
                }
            }
        }

    }

    /**
     * Обновить данные существующего работника
     *
     * @param entity работник
     */
    @Override
    public void update(Employee entity) {
        Employee employee = entityManager.find(Employee.class, entity.getId());

        if (entity.getOffice() != null) {
            employee.setOffice(entityManager.find(Office.class, entity.getOffice().getId()));
        }

        employee.setFirstName(entity.getFirstName());

        if (entity.getSecondName() != null) {
            employee.setSecondName(entity.getSecondName());
        }
        if (entity.getMiddleName() != null) {
            employee.setMiddleName(entity.getMiddleName());
        }

        Position position = loadByPositionName(entity.getPosition().getName());
        if (position == null) {
            position = new Position(entity.getPosition().getName());
            entityManager.persist(position);
        }
        employee.setPosition(position);

        if (entity.getPhone() != null) {
            employee.setPhone(entity.getPhone());
        }


        if (entity.getCountry() != null) {
            Country country = loadByCitizenshipCode(entity.getCountry().getCode());
            if (country == null) {
                country = new Country(entity.getCountry().getCode(), entity.getCountry().getName());
                entityManager.persist(country);
            }
            employee.setCountry(country);
        }

        if (entity.getIsIdentified() != null) {
            employee.setIsIdentified(entity.getIsIdentified());
        }

        if (entity.getDocumentData() != null && employee.getDocumentData() != null) {
            if (employee.getDocumentData().getDocument() != null && entity.getDocumentData().getDocument() != null) {
                Document document = loadByDocName(entity.getDocumentData().getDocument().getName());
                if (document != null) {
                    employee.getDocumentData().setDocument(document);
                }
            }
            if (entity.getDocumentData().getDate() != null) {
                employee.getDocumentData().setDate(entity.getDocumentData().getDate());
            }
            if (entity.getDocumentData().getNumber() != null) {
                employee.getDocumentData().setNumber(entity.getDocumentData().getNumber());
            }
        }


        if (entity.getDocumentData() != null && employee.getDocumentData() == null) {
            if (entity.getDocumentData().getDocument() != null) {
                if (entity.getDocumentData().getDocument().getName() != null) {
                    Document document = loadByDocName(entity.getDocumentData().getDocument().getName());
                    if (document != null && entity.getDocumentData().getNumber() != null) {
                        DocumentData documentData = new DocumentData(document, entity.getDocumentData().getNumber(), entity.getDocumentData().getDate(), employee);
                        entityManager.persist(documentData);
                    }
                }
            }
        }
    }

    /**
     * Найти список работников по фильтру
     *
     * @param entity работник с входными данными для поиска
     * @return список работников
     */
    @Override
    public List<Employee> loadByParametersList(Employee entity) {
        if (entity.getPosition() != null) {
            Position position = loadByPositionName(entity.getPosition().getName());
            if (position != null) {
                entity.setPosition(position);
            } else {
                return Collections.emptyList();
            }
        }

        if (entity.getCountry() != null) {
            Country country = loadByCitizenshipCode(entity.getCountry().getCode());
            if (country != null) {
                entity.setCountry(country);
            } else {
                return Collections.emptyList();
            }
        }

        CriteriaQuery<Employee> criteria = buildCriteriaByParametersList(entity);
        TypedQuery<Employee> query = entityManager.createQuery(criteria);
        List<Employee> employeeList;
        try {
            employeeList = query.getResultList();
        } catch (RuntimeException ex) {
            return Collections.emptyList();
        }

        if (employeeList.isEmpty() || entity.getDocumentData() == null || entity.getDocumentData().getDocument() == null) {
            return employeeList;
        }

        List<Employee> filtered = new ArrayList<>();
        if (entity.getDocumentData() != null && entity.getDocumentData().getDocument() != null) {
            filtered = employeeList.stream()
                    .filter(employee -> ((employee.getDocumentData()!= null) && (employee.getDocumentData().getDocument() != null)))
                    .filter(employee -> employee.getDocumentData().getDocument().getCode().equals(entity.getDocumentData().getDocument().getCode()))
                    .collect(Collectors.toList());
        }

        return filtered;
    }

    /**
     * Найти работника по номеру документа в базе данных
     *
     * @param docNumber номер документа
     * @return Employee работник
     */
    @Override
    public Employee loadByParameter(String docNumber) {
        CriteriaQuery<DocumentData> criteria = buildDocumentDataCriteria(docNumber);
        TypedQuery<DocumentData> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult().getEmployee();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private CriteriaQuery<DocumentData> buildDocumentDataCriteria(String number) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentData> criteria = builder.createQuery(DocumentData.class);
        Root<DocumentData> documentData = criteria.from(DocumentData.class);
        criteria.where(builder.equal(documentData.get("number"), number));
        return criteria;
    }


    private CriteriaQuery<Employee> buildCriteriaByParametersList(Employee entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> employee = criteria.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(employee.get("office"), entity.getOffice().getId()));
        if (entity.getFirstName() != null) {
            predicates.add(builder.equal(builder.lower(employee.get("firstName")), entity.getFirstName().toLowerCase()));
        }
        if (entity.getLastName() != null) {
            predicates.add(builder.equal(builder.lower(employee.get("lastName")), entity.getLastName().toLowerCase()));
        }
        if (entity.getMiddleName() != null) {
            predicates.add(builder.equal(builder.lower(employee.get("middleName")), entity.getMiddleName().toLowerCase()));
        }
        if (entity.getPosition() != null && entity.getPosition().getId() != null) {
            predicates.add(builder.equal(employee.get("position"), entity.getPosition().getId()));
        }
        if (entity.getCountry() != null && entity.getCountry().getId() != null) {
            predicates.add(builder.equal(employee.get("country"), entity.getCountry().getId()));
        }
        criteria.select(employee).where(predicates.toArray(new Predicate[]{}));
        return criteria;
    }


    private Position loadByPositionName(String positionName) {
        CriteriaQuery<Position> criteria = buildPositionCriteria(positionName);
        TypedQuery<Position> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private CriteriaQuery<Position> buildPositionCriteria(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Position> criteria = builder.createQuery(Position.class);
        Root<Position> position = criteria.from(Position.class);
        criteria.where(builder.equal(builder.lower(position.get("name")), name.toLowerCase()));
        return criteria;
    }



    private Document loadByDocCode(String docCode) {
        CriteriaQuery<Document> criteria = buildDocumentCriteria(docCode);
        TypedQuery<Document> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private CriteriaQuery<Document> buildDocumentCriteria(String code) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> criteria = builder.createQuery(Document.class);
        Root<Document> document = criteria.from(Document.class);
        criteria.where(builder.equal(document.get("code"), code));
        return criteria;
    }



    private Document loadByDocName(String docName) {
        CriteriaQuery<Document> criteria = buildDocumentNameCriteria(docName);
        TypedQuery<Document> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private CriteriaQuery<Document> buildDocumentNameCriteria(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> criteria = builder.createQuery(Document.class);
        Root<Document> document = criteria.from(Document.class);
        criteria.where(builder.equal(document.get("name"), name));
        return criteria;
    }




    private Country loadByCitizenshipCode(String citizenshipCode) {
        CriteriaQuery<Country> criteria = buildCountryCriteria(citizenshipCode);
        TypedQuery<Country> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private CriteriaQuery<Country> buildCountryCriteria(String code) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> criteria = builder.createQuery(Country.class);
        Root<Country> country = criteria.from(Country.class);
        criteria.where(builder.equal(country.get("code"), code));
        return criteria;
    }
}
