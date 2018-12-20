package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PositionView {

    @JsonIgnore
    private String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    @JsonProperty("position")
    private String name;

    public PositionView() {

    }

    public PositionView(@Size(max = 50) @NotEmpty(message = "name cannot be null") String name) {
        this.name = name;
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
        this.name = name;
    }
}
