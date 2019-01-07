package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.dao.IGenericDao;
import work.model.Position;
import work.model.mapper.MapperFacade;
import work.view.PositionView;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис справочника должностей
 */
@Service
public class PositionServiceImpl implements IService<PositionView, Short> {

    private final IGenericDao<Position, Short> dao;
    private final MapperFacade mapperFacade;

    @Autowired
    public PositionServiceImpl(IGenericDao<Position, Short> dao, MapperFacade mapperFacade) {
        this.dao = dao;
        this.dao.setClazz(Position.class);
        this.mapperFacade = mapperFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@Valid PositionView positionView) {
        Position position = mapperFacade.map(positionView, Position.class);
        dao.save(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(@Valid PositionView positionView) {
        Position position = mapperFacade.map(positionView, Position.class);
        dao.update(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<PositionView> findAll() {
        List<Position> all = dao.all();
        return mapperFacade.mapAsList(all, PositionView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PositionView findById(Short id) {
        Position position = dao.loadById(id);
        return mapperFacade.map(position, PositionView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PositionView findByParameter(String parameter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<PositionView> findByParametersList(PositionView view) {
        return null;
    }
}
