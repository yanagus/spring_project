package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PositionView {

    @JsonIgnore
    public String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    @JsonProperty("position")
    public String name;

    public PositionView() {

    }

    public PositionView(@Size(max = 50) @NotEmpty(message = "name cannot be null") String name) {
        this.name = name;
    }
}
