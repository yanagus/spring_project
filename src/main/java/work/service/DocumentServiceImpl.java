package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.dao.IGenericDao;
import work.model.Document;
import work.model.mapper.MapperFacade;
import work.view.DocumentView;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис справочника видов документов, удостоверяющих личность физического лица
 */
@Service
public class DocumentServiceImpl implements IService<DocumentView, Byte> {

    private final IGenericDao<Document, Byte> dao;
    private final MapperFacade mapperFacade;

    @Autowired
    public DocumentServiceImpl(IGenericDao<Document, Byte> dao, MapperFacade mapperFacade) {
        this.dao = dao;
        this.dao.setClazz(Document.class);
        this.mapperFacade = mapperFacade;
    }

    @Override
    public void add(@Valid DocumentView view) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<DocumentView> findAll() {
        List<Document> all = dao.all();
        return mapperFacade.mapAsList(all, DocumentView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public DocumentView findById(Byte id) {
        Document document = dao.loadById(id);
        return mapperFacade.map(document, DocumentView.class);
    }
}
