package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

public class DocumentDataView {

    @JsonIgnore
    public String id;

    public DocumentView document;

    @Size(max = 30)
    @NotEmpty(message = "number cannot be null")
    @JsonView(Views.GetByIdView.class)
    @JsonProperty("docNumber")
    public String number;

    @JsonView(Views.GetByIdView.class)
    @JsonProperty("docDate")
    public Date date;

    @JsonIgnore
    public EmployeeView employee;

}
