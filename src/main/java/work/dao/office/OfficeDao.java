package work.dao.office;


import work.model.Office;

import java.util.List;

/**
 * DAO для работы с Office
 */
public interface OfficeDao {

    /**
     * Получить все объекты Office
     *
     * @return List<Office>
     */
    List<Office> all();

    /**
     * Получить Office по идентификатору
     *
     * @param id
     * @return Office
     */
    Office loadById(Integer id);

    /**
     * Обновить Office
     *
     * @param office
     */
    void update(Office office);

    /**
     * Сохранить Office
     *
     * @param office
     */
    void save(Office office);
}
