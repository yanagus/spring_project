package work.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Класс страны для сериализации в JSON
 */
@JsonPropertyOrder({"name", "code"})
public class CountryView {

    /**
     * Уникальный идентификатор
     */
    private String id;

    /**
     * Код страны
     */
    @Size(max = 3)
    @NotEmpty(message = "code cannot be null")
    @JsonView(Views.ListView.class)
    private String code;

    /**
     * Название страны
     */
    @Size(max = 50)
    @JsonView(Views.ListView.class)
    private String name;

    public CountryView() {
    }

    public CountryView(String code, String name) {
        setCode(code);
        this.name = name;
    }

    public CountryView(String code) {
        this(code, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code != null) {
            this.code = code.trim();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryView that = (CountryView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, name);
    }

    @Override
    public String toString() {
        return "CountryView{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
