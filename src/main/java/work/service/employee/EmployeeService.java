package work.service.employee;


import org.springframework.validation.annotation.Validated;
import work.view.EmployeeView;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис
 */
@Validated
public interface EmployeeService {

    /**
     * Добавить нового работника в БД
     *
     * @param employee
     */
    void add(@Valid EmployeeView employee);

    /**
     * Получить список работников
     *
     * @return {@Employee}
     */
    List<EmployeeView> employees();

    /**
     * Найти работника по id
     *
     * @param id
     */
    EmployeeView findById(Integer id);
}
