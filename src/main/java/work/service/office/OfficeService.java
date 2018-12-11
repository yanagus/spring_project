package work.service.office;

import org.springframework.validation.annotation.Validated;
import work.view.OfficeView;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис
 */
@Validated
public interface OfficeService {

    /**
     * Добавить новый офис в БД
     *
     * @param office
     */
    void add(@Valid OfficeView office);

    /**
     * Получить список офисов
     *
     * @return {@Office}
     */
    List<OfficeView> offices();

    /**
     * Найти офис по id
     *
     * @param id
     */
    OfficeView findById(Integer id);
}
