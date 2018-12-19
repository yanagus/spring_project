package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

public class DocumentDataView {

    @JsonIgnore
    public String id;

    @JsonUnwrapped
    public DocumentView document;

    @Size(max = 30)
    @NotEmpty(message = "number cannot be null")
    @JsonProperty("docNumber")
    public String number;

    @JsonProperty("docDate")
    public Date date;

    @JsonBackReference
    public EmployeeView employee;

}
