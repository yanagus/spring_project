package work.dao.employee;


import work.model.Employee;

import java.util.List;

/**
 * DAO для работы с Employee
 */
public interface EmployeeDao {

    /**
     * Получить все объекты Employee
     *
     * @return List<Employee>
     */
    List<Employee> all();

    /**
     * Получить Employee по идентификатору
     *
     * @param id
     * @return Employee
     */
    Employee loadById(Integer id);

    /**
     * Обновить Employee
     *
     * @param employee
     */
    void update(Employee employee);

    /**
     * Сохранить Employee
     *
     * @param employee
     */
    void save(Employee employee);
}
