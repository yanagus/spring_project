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
    private String id;

    @JsonUnwrapped
    private DocumentView document;

    @Size(max = 30)
    @NotEmpty(message = "number cannot be null")
    @JsonProperty("docNumber")
    private String number;

    @JsonProperty("docDate")
    private Date date;

    @JsonBackReference
    private EmployeeView employee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentView getDocument() {
        return document;
    }

    public void setDocument(DocumentView document) {
        this.document = document;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EmployeeView getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeView employee) {
        this.employee = employee;
    }
}
