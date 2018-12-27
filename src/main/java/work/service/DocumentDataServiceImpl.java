package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.dao.IGenericDao;
import work.model.DocumentData;
import work.model.mapper.MapperFacade;
import work.view.DocumentDataView;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис персональных данных работника
 */
@Service
public class DocumentDataServiceImpl implements IService<DocumentDataView, Integer> {

    private final IGenericDao<DocumentData, Integer> dao;
    private final MapperFacade mapperFacade;

    @Autowired
    public DocumentDataServiceImpl(IGenericDao<DocumentData, Integer> dao, MapperFacade mapperFacade) {
        this.dao = dao;
        this.dao.setClazz(DocumentData.class);
        this.mapperFacade = mapperFacade;
    }

    @Override
    public void add(@Valid DocumentDataView documentDataView) {
        DocumentData documentData = mapperFacade.map(documentDataView, DocumentData.class);
        dao.save(documentData);
    }

    @Override
    public void update(@Valid DocumentDataView documentDataView) {
        DocumentData documentData = mapperFacade.map(documentDataView, DocumentData.class);
        dao.update(documentData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<DocumentDataView> findAll() {
        List<DocumentData> all = dao.all();
        return mapperFacade.mapAsList(all, DocumentDataView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public DocumentDataView findById(Integer id) {
        DocumentData documentData = dao.loadById(id);
        return mapperFacade.map(documentData, DocumentDataView.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentDataView findByParameter(String parameter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<DocumentDataView> findByParametersList(DocumentDataView view) {
        return null;
    }
}
