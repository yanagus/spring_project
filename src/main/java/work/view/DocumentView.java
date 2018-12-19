package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DocumentView {

    @JsonIgnore
    public String id;

    @Size(max = 2)
    @NotEmpty(message = "code cannot be null")
    @JsonProperty("docCode")
    public String code;

    @Size(max = 50)
    @JsonProperty("docName")
    public String name;

}
