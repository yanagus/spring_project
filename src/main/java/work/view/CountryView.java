package work.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CountryView {

    private String id;

    @Size(max = 3)
    @NotEmpty(message = "code cannot be null")
    @JsonProperty("citizenshipCode")
    private String code;

    @Size(max = 50)
    @JsonProperty("citizenshipName")
    private String name;

    public CountryView() {
    }

    public CountryView(@Size(max = 3) @NotEmpty(message = "code cannot be null") String code, @Size(max = 50) String name) {
        this.code = code;
        this.name = name;
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
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
