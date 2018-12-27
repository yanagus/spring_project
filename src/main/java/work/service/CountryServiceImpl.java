package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.dao.IGenericDao;
import work.model.Country;
import work.model.mapper.MapperFacade;
import work.view.CountryView;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис справочника общероссийского классификатора стран мира
 */
@Service
public class CountryServiceImpl implements IService<CountryView, Short> {

    private final IGenericDao<Country, Short> dao;
    private final MapperFacade mapperFacade;

    @Autowired
    public CountryServiceImpl(IGenericDao<Country, Short> dao, MapperFacade mapperFacade) {
        this.dao = dao;
        this.dao.setClazz(Country.class);
        this.mapperFacade = mapperFacade;
    }

    @Override
    public void add(@Valid CountryView countryView) {
        Country country = mapperFacade.map(countryView, Country.class);
        dao.save(country);
    }

    @Override
    public void update(@Valid CountryView countryView) {
        Country country = mapperFacade.map(countryView, Country.class);
        dao.update(country);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountryView> findAll() {
        List<Country> all = dao.all();
        return mapperFacade.mapAsList(all, CountryView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public CountryView findById(Short id) {
        Country country = dao.loadById(id);
        return mapperFacade.map(country, CountryView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public CountryView findByParameter(String parameter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountryView> findByParametersList(CountryView view) {
        return null;
    }
}
