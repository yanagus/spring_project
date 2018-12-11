package work.service.organization;

import org.springframework.validation.annotation.Validated;
import work.view.OrganizationView;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис
 */
@Validated
public interface OrganizationService {

    /**
     * Добавить новую организацию в БД
     *
     * @param organization
     */
    void add(@Valid OrganizationView organization);

    /**
     * Получить список организаций
     *
     * @return {@Organization}
     */
    List<OrganizationView> organizations();

    /**
     * Найти организацию по id
     *
     * @param id
     */
    OrganizationView findById(Integer id);

}
