package work.view;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Класс должности для сериализации в JSON
 */
public class PositionView {

    /**
     * Уникальный идентификатор должности
     */
    private String id;

    /**
     * Название должности
     */
    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    private String name;

    public PositionView() {

    }

    public PositionView(String name) {
        setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name.trim();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionView that = (PositionView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "PositionView{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
