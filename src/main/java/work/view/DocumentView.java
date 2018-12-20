package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DocumentView {

    @JsonIgnore
    private String id;

    @Size(max = 2)
    @NotEmpty(message = "code cannot be null")
    @JsonProperty("docCode")
    private String code;

    @Size(max = 50)
    @JsonProperty("docName")
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
}
