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
    public void add(OrganizationView view) {
        Organization organization = new Organization(view.name, view.fullName, view.inn, view.kpp, view.phone, view.address, view.isActive);
        dao.save(organization);
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
        Organization org = dao.loadById(id);
        return mapperFacade.map(org, OrganizationView.class);
    }
}
