package work.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * Сервис интерфейс
 *
 * @param <V> класс представления
 */
@Validated
public interface IService<V, ID extends Number> {

    /**
     * Добавить view в БД
     *
     * @param view представление
     */
    void add(@Valid V view);

    /**
     * Обновить view в БД
     *
     * @param view представление
     */
    void update(@Valid V view);

    /**
     * Получить список view
     *
     * @return List<V>
     */
    List<V> findAll();

    /**
     * Найти view по id
     *
     * @param id идентификатор
     * @return представление
     */
    V findById(ID id);

    /**
     * Найти view по параметру
     *
     * @param parameter параметр
     * @return представление
     */
    V findByParameter(String parameter);

    /**
     * Найти список view по параметрам
     *
     * @param view представление с заданными параметрами
     * @return представление
     */
    List<V> findByParametersList(V view);
}
