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

}
