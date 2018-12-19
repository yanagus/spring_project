package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CountryView {

    @JsonIgnore
    public String id;

    @Size(max = 3)
    @NotEmpty(message = "code cannot be null")
    @JsonProperty("citizenshipCode")
    public String code;

    @Size(max = 50)
    @JsonProperty("citizenshipName")
    public String name;

    public CountryView() {
    }

    public CountryView(@Size(max = 3) @NotEmpty(message = "code cannot be null") String code, @Size(max = 50) String name) {
        this.code = code;
        this.name = name;
    }
}
