package work.controller;

/**
 * Ошибка о том, что такая сущность уже существует в базе данных
 */
public class EntityAlreadyExistException extends RuntimeException{

    /**
     * Конструктор
     *
     * @param message сообщение об ошибке
     */
    public EntityAlreadyExistException (String message) {
        super(message);
    }
}
