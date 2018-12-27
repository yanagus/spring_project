package work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.dao.IGenericDao;
import work.model.Employee;
import work.model.mapper.MapperFacade;
import work.view.EmployeeView;

import java.util.List;

/**
 * Сервис работника
 */
@Service
public class EmployeeServiceImpl  implements IService<EmployeeView, Integer> {

    private final IGenericDao<Employee, Integer> dao;
    private final MapperFacade mapperFacade;

    @Autowired
    public EmployeeServiceImpl(IGenericDao<Employee, Integer> dao, MapperFacade mapperFacade) {
        this.dao = dao;
        this.dao.setClazz(Employee.class);
        this.mapperFacade = mapperFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void add(EmployeeView employeeView) {
        Employee employee = mapperFacade.map(employeeView, Employee.class);
        dao.save(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(EmployeeView employeeView) {
        Employee employee = mapperFacade.map(employeeView, Employee.class);
        dao.update(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeView> findAll() {
        List<Employee> all = dao.all();
        return mapperFacade.mapAsList(all, EmployeeView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeView findById(Integer id) {
        Employee employee = dao.loadById(id);
        return mapperFacade.map(employee, EmployeeView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeView findByParameter(String parameter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployeeView> findByParametersList(EmployeeView view) {
        return null;
    }
}
