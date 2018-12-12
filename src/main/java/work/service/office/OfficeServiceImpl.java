package work.service.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.controller.EntityNotFoundException;
import work.dao.office.OfficeDao;
import work.model.Office;
import work.model.mapper.MapperFacade;
import work.view.OfficeView;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeDao dao;
    private final MapperFacade mapperFacade;

    @Autowired
    public OfficeServiceImpl(OfficeDao dao, MapperFacade mapperFacade) {
        this.dao = dao;
        this.mapperFacade = mapperFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void add(OfficeView view) {
        Office office = new Office(view.name, view.phone, view.address, view.isActive, null);
        dao.save(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<OfficeView> offices() {
        List<Office> all = dao.all();
        return mapperFacade.mapAsList(all, OfficeView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public OfficeView findById(Integer id) {
        if(id!=1) {
            throw new EntityNotFoundException("Not found office with id is " + id);
        }
        return new OfficeView("1", "Офис", "+7 (495) 322-2233", "г. Москва", true, "1");
    }
}
