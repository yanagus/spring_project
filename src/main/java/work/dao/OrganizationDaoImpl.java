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
 * {@inheritDoc}
 */
@Repository
public class OrganizationDaoImpl extends AbstractDao<Organization, Integer> {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public OrganizationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> loadByParametersList(Organization entity) {
        CriteriaQuery<Organization> criteria = buildCriteriaByNameAndActive(entity);
        TypedQuery<Organization> query = entityManager.createQuery(criteria);
        try {
            return query.getResultList();
        } catch (RuntimeException ex) {
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization loadByParameter(String parameter) {
        CriteriaQuery<Organization> criteria = buildCriteria(parameter);
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

    private CriteriaQuery<Organization> buildCriteriaByNameAndActive(Organization entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Organization> criteria = builder.createQuery(Organization.class);
        Root<Organization> organization = criteria.from(Organization.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(organization.get("name"), entity.getName()));
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
