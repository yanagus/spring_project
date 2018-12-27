package work.view;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class DocumentDataView {

    private String id;

    private DocumentView document;

    @Size(max = 30)
    @NotEmpty(message = "number cannot be null")
    @JsonProperty("docNumber")
    private String number;

    @JsonProperty("docDate")
    private Date date;

    @JsonBackReference
    private EmployeeView employee;

    public DocumentDataView() {
    }

    public DocumentDataView(String id, DocumentView document, String number, Date date, EmployeeView employee) {
        this.id = id;
        this.document = document;
        this.number = number;
        this.date = date;
        this.employee = employee;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentDataView that = (DocumentDataView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(document, that.document) &&
                Objects.equals(number, that.number) &&
                Objects.equals(date, that.date) &&
                Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, document, number, date, employee);
    }

    @Override
    public String toString() {
        return "DocumentDataView{" +
                "id='" + id + '\'' +
                ", document=" + document +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", employee=" + employee +
                '}';
    }
}
