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
        List<Organization> organizationList = new ArrayList<>();
        if(entity.getInn() != null) {
            organizationList.add(loadByParameter(entity.getInn()));
        }
        if(organizationList.isEmpty()) {
            organizationList = Collections.emptyList();
        }
        return organizationList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization loadByParameter(String parameter) {
        CriteriaQuery<Organization> criteria = buildCriteria(parameter.trim());
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

        Root<Organization> person = criteria.from(Organization.class);
        criteria.where(builder.equal(person.get("inn"), inn));

        return criteria;
    }
}
