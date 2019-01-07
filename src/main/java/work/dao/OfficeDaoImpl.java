package work.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import work.model.Office;
import work.model.Organization;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * DAO для работы с Office
 */
@Repository
public class OfficeDaoImpl extends AbstractDao<Office, Integer> {

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Конструктор для EntityManager
     *
     * @param entityManager EntityManager
     */
    @Autowired
    public OfficeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // не нужна
    @Override
    public Office loadByParameter(String param) {
        return null;
    }

    /**
     * Обновить существующий офис
     *
     * @param entity офис
     */
    @Override
    public void update(Office entity) {
        Office office = entityManager.find(Office.class, entity.getId());
        office.setName(entity.getName());
        office.setAddress(entity.getAddress());
        if (entity.getPhone() != null) {
            office.setPhone(entity.getPhone());
        }
        if (entity.getIsActive() != null) {
            office.setIsActive(entity.getIsActive());
        }
        if (entity.getOrganization() != null) {
            office.setOrganization(entityManager.find(Organization.class, entity.getOrganization().getId()));
        }
    }

    /**
     * Сохранить новый офис
     *
     * @param entity офис
     */
    @Override
    public void save(@Valid Office entity) {
        if (entity.getOrganization() != null) {
            entity.setOrganization(entityManager.find(Organization.class, entity.getOrganization().getId()));
        }
        entityManager.persist(entity);
    }

    @Override
    public List<Office> loadByParametersList(Office entity) {
        CriteriaQuery<Office> criteria = buildCriteriaByParametersList(entity);
        TypedQuery<Office> query = entityManager.createQuery(criteria);
        try {
            return query.getResultList();
        } catch (RuntimeException ex) {
            return Collections.emptyList();
        }
    }

    private CriteriaQuery<Office> buildCriteriaByParametersList(Office entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Office> criteria = builder.createQuery(Office.class);
        Root<Office> office = criteria.from(Office.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(office.get("organization"), entity.getOrganization().getId()));

        if (entity.getName() != null) {
            predicates.add(builder.equal(builder.lower(office.get("name")), entity.getName().toLowerCase()));
        }
        if (entity.getPhone() != null) {
            predicates.add(builder.equal(office.get("phone"), entity.getPhone()));
        }
        if (entity.getIsActive() != null) {
            predicates.add(builder.equal(office.get("isActive"), entity.getIsActive()));
        }

        criteria.select(office).where(predicates.toArray(new Predicate[]{}));
        return criteria;
    }
}
