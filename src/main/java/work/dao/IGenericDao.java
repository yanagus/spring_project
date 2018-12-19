package work.dao;

import java.io.Serializable;
import java.util.List;

/**
 * DAO для работы с Entity
 */
public interface IGenericDao<T extends Serializable, ID extends Number> {

    /**
     * Установить класс сущности
     *
     * @param clazzToSet класс сущности
     */
    void setClazz(Class<T> clazzToSet);

    /**
     * Получить список Entity
     *
     * @return List
     */
    List<T> all();

    /**
     * Получить Entity по идентификатору
     *
     * @param id уникальный идентификатор
     * @return T
     */
    T loadById(ID id);

    /**
     * Обновить Entity
     *
     * @param entity сущность
     */
    void update(T entity);

    /**
     * Добавить Entity в БД
     *
     * @param entity сущность
     */
    void save(T entity);
}
