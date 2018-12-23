package work.controller;

/**
 * Ошибка о том, что такая сущность не найдена в базе данных
 */
public class EntityNotFoundException extends RuntimeException{

    /**
     * Конструктор
     *
     * @param message сообщение об ошибке
     */
    public EntityNotFoundException (String message) {
        super(message);
    }
}
