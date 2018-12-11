package work.dao.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import work.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final EntityManager em;

    @Autowired
    public EmployeeDaoImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> all() {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee loadById(Integer id) {
        return em.find(Employee.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Employee employee) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Employee employee) {
        em.persist(employee);
    }
}
