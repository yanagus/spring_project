package work.view;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Objects;

/**
 * Класс для сериализации ответа об успешном сохранении или изменении данных
 */
public class ResponseView {

    /**
     * Сообщение результата
     */
    @JsonView({Views.SaveView.class, Views.UpdateView.class})
    private String result;

    public ResponseView() {
    }

    public ResponseView(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseView that = (ResponseView) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {

        return Objects.hash(result);
    }

    @Override
    public String toString() {
        return "ResponseView{" +
                "result='" + result + '\'' +
                '}';
    }
}
