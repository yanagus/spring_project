package work.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@JsonPropertyOrder({"name", "code"})
public class DocumentView {

    private String id;

    @Size(max = 2)
    @NotEmpty(message = "code cannot be null")
    @JsonView(Views.ListView.class)
    private String code;

    @Size(max = 50)
    @JsonView(Views.ListView.class)
    private String name;

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
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentView() {
    }

    public DocumentView(String id, @Size(max = 2) @NotEmpty(message = "code cannot be null") String code, @Size(max = 50) String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentView that = (DocumentView) o;
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
        return "DocumentView{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
