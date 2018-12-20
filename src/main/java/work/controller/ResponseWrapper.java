package work.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import work.view.Views;

/**
 * Обработка ответов контроллеров
 */
@RestControllerAdvice
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * Обработчик ошибок
     *
     * @param ex ошибка класса EntityNotFoundException
     * @return сообщение ошибки типа ResponseMsg
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseMsg handleNotFoundException(EntityNotFoundException ex) {
        return new ResponseMsg(ex.getMessage());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ResponseMsg) {
            return new ErrorWrapper<>(((ResponseMsg) body).getMessage());
        }
        return new Wrapper<>(body);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonSerialize
    private class Wrapper<T> {

        @JsonView(Views.GetByIdView.class)
        private final T data;

        public Wrapper(T data) {
            this.data = data;
        }
    }


    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonSerialize
    private class ErrorWrapper<T> {
        private final T error;

        public ErrorWrapper(T error) {
            this.error = error;
        }
    }
}
