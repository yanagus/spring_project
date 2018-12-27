package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.dao.IGenericDao;
import work.model.Organization;
import work.model.mapper.MapperFacade;
import work.view.OrganizationView;

import java.util.List;

/**
 * Сервис организации
 */
@Service
public class OrganizationServiceImpl implements IService<OrganizationView, Integer> {

    private final IGenericDao<Organization, Integer> dao;
    private final MapperFacade mapperFacade;

    @Autowired
    public OrganizationServiceImpl(IGenericDao<Organization, Integer> dao, MapperFacade mapperFacade) {
        this.dao = dao;
        this.dao.setClazz(Organization.class);
        this.mapperFacade = mapperFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void add(OrganizationView organizationView) {
        Organization organization = mapperFacade.map(organizationView, Organization.class);
        dao.save(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(OrganizationView organizationView) {
        Organization organization = mapperFacade.map(organizationView, Organization.class);
        dao.update(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrganizationView> findAll() {
        List<Organization> all = dao.all();
        return mapperFacade.mapAsList(all, OrganizationView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationView findById(Integer id) {
        Organization organization = dao.loadById(id);
        return mapperFacade.map(organization, OrganizationView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationView findByParameter(String parameter) {
        Organization organization = dao.loadByParameter(parameter);
        return mapperFacade.map(organization, OrganizationView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrganizationView> findByParametersList(OrganizationView view) {
        Organization organization = mapperFacade.map(view, Organization.class);
        List<Organization> organizationList = dao.loadByParametersList(organization);
        return mapperFacade.mapAsList(organizationList, OrganizationView.class);
    }
}
