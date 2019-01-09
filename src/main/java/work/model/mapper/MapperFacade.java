package work.model.mapper;

import java.util.List;

/**
 * Фасад для преобразования между моделями БД и фронта
 */
public interface MapperFacade {

    /**
     * Преобразование sourceObject в экземпляр класса destinationClass
     *
     * @param sourceObject     исходный объект
     * @param destinationClass класс, в который надо преобразовать объект
     * @param <S>              тип исходного объекта
     * @param <D>              тип объекта, в который надо преобразовать исходный объект
     * @return экземпляр класса D с данными из sourceObject
     */
    <S, D> D map(S sourceObject, Class<D> destinationClass);

    /**
     * Запись данных из sourceObject в destinationObject
     *
     * @param sourceObject      исходный объект
     * @param destinationObject объект, в который надо преобразовать объект
     * @param <S>               тип исходного объекта
     * @param <D>               тип объекта, в который надо преобразовать исходный объект
     */
    <S, D> void map(S sourceObject, D destinationObject);

    /**
     * Преобразование коллекции объектов
     *
     * @param source            исходный объект
     * @param destinationClass  класс, в который надо преобразовать объект
     * @param <S>               тип исходного объекта
     * @param <D>               тип объекта, в который надо преобразовать исходный объект
     * @return
     */
    <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass);
}
