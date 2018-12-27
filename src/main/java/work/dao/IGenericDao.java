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
     * @return List<T> список сущностей
     */
    List<T> all();

    /**
     * Получить Entity по идентификатору
     *
     * @param id уникальный идентификатор
     * @return T сущность
     */
    T loadById(ID id);

    /**
     * Получить Entity по параметру
     *
     * @param param параметр
     * @return T
     */
    T loadByParameter(String param);

    /**
     * Получить список Entity
     *
     * @param entity сущность
     * @return List<T> список сущностей
     */
    List<T> loadByParametersList(T entity);

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
