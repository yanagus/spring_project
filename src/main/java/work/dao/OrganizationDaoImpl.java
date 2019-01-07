package work.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import work.model.Organization;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DAO для работы с Organization
 */
@Repository
public class OrganizationDaoImpl extends AbstractDao<Organization, Integer> {

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Конструктор для EntityManager
     *
     * @param entityManager EntityManager
     */
    @Autowired
    public OrganizationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Обновить существующую организацию
     *
     * @param entity организация
     */
    public void update(Organization entity) {
        Organization organization = entityManager.find(Organization.class, entity.getId());
        organization.setName(entity.getName());
        organization.setFullName(entity.getFullName());
        organization.setInn(entity.getInn());
        organization.setKpp(entity.getKpp());
        organization.setAddress(entity.getAddress());
        if (entity.getPhone() != null) {
            organization.setPhone(entity.getPhone());
        }
        if (entity.getIsActive() != null) {
            organization.setIsActive(entity.getIsActive());
        }
    }

    /**
     * Получить список организаций
     *
     * @param entity организация с входными данными для поиска (название, ИНН, статус)
     * @return список организаций
     */
    @Override
    public List<Organization> loadByParametersList(Organization entity) {
        CriteriaQuery<Organization> criteria = buildCriteriaByParametersList(entity);
        TypedQuery<Organization> query = entityManager.createQuery(criteria);
        try {
            return query.getResultList();
        } catch (RuntimeException ex) {
            return Collections.emptyList();
        }
    }

    /**
     * Получить организацию по ИНН
     *
     * @param inn ИНН
     * @return организация - если найдена в БД, null - если нет
     */
    @Override
    public Organization loadByParameter(String inn) {
        CriteriaQuery<Organization> criteria = buildCriteria(inn);
        TypedQuery<Organization> query = entityManager.createQuery(criteria);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private CriteriaQuery<Organization> buildCriteria(String inn) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Organization> criteria = builder.createQuery(Organization.class);

        Root<Organization> organization = criteria.from(Organization.class);
        criteria.where(builder.equal(organization.get("inn"), inn));

        return criteria;
    }

    private CriteriaQuery<Organization> buildCriteriaByParametersList(Organization entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Organization> criteria = builder.createQuery(Organization.class);
        Root<Organization> organization = criteria.from(Organization.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(builder.lower(organization.get("name")), entity.getName().toLowerCase()));
        if (entity.getInn() != null) {
            predicates.add(builder.equal(organization.get("inn"), entity.getInn()));
        }
        if (entity.getIsActive() != null) {
            predicates.add(builder.equal(organization.get("isActive"), entity.getIsActive()));
        }

        criteria.select(organization).where(predicates.toArray(new Predicate[]{}));
        return criteria;
    }
}
