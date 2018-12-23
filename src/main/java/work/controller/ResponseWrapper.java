package work.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import work.view.Views;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Обработка ответов контроллеров
 */
@RestControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    /**
     * Обработчик ошибок о том, что данная сущность не найдена в базе данных
     *
     * @param ex ошибка класса EntityNotFoundException
     * @return экземпляр типа ResponseMessage с сообщением об ошибке
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    /**
     * Обработчик ошибок о том, что данная сущность уже существует в базе данных
     *
     * @param ex ошибка класса EntityAlreadyExistException
     * @return экземпляр типа ResponseMessage с сообщением об ошибке
     */
    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleEntityAlreadyExistException(EntityAlreadyExistException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    /**
     * Обработчик ошибок валидатора
     *
     * @param ex ошибка класса MethodArgumentNotValidException
     * @return экземпляр типа ResponseMessage с сообщением об ошибке или ошибках
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handleValidationException(MethodArgumentNotValidException ex) {
        return new ResponseMessage(ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", ")));
    }

    /**
     * Переопределённый метод, сообщающий о том, что должен быть вызван метод beforeBodyWrite
     *
     * @param returnType возвращаемый контроллером тип
     * @param converterType выбранный тип конвертера
     * @return true должен быть вызван метод beforeBodyWrite
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * Переопределённый метод, заворачивает данные от контроллера в класс-обёртку
     *
     * @param body объект, который должен быть завёрнут
     * @param returnType возвращаемый контроллером тип
     * @param selectedContentType выбранный MediaType
     * @param selectedConverterType выбранный тип конвертера
     * @param request текущий запрос
     * @param response текущий ответ
     * @return изменённый новый экземпляр
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof ResponseMessage) {
            return new ErrorWrapper<>(((ResponseMessage) body).getMessage());
        }
        return new Wrapper<>(body);
    }
}

/**
 * Класс-обёртка ответов контроллеров.
 * Все возвращаемые контроллерами типы данных представлений находятся в параметре "data"
 * @param <T> тип представления
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
class Wrapper<T> {

    @JsonView({Views.GetByIdView.class})
    private T data;

    public Wrapper() {
    }

    public Wrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wrapper<?> wrapper = (Wrapper<?>) o;
        return Objects.equals(data, wrapper.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "data=" + data +
                '}';
    }
}

/**
 * Класс-обёртка для сообщений об ошибке
 * @param <T> ResponseMessage
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
class ErrorWrapper<T> {
    private T error;

    public ErrorWrapper() {
    }

    public ErrorWrapper(T error) {
        this.error = error;
    }

    public T getError() {
        return error;
    }

    public void setError(T error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorWrapper<?> that = (ErrorWrapper<?>) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {

        return Objects.hash(error);
    }

    @Override
    public String toString() {
        return "ErrorWrapper{" +
                "error=" + error +
                '}';
    }
}
