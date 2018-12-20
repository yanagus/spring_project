package work.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@JsonPropertyOrder({"id", "firstName", "secondName", "middleName", "lastName", "position", "phone", "docName", "docNumber",
"docDate", "citizenshipName", "citizenshipCode", "isIdentified"})
public class EmployeeView {

    @JsonView(Views.GetByIdView.class)
    private String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    @JsonView(Views.GetByIdView.class)
    private String firstName;

    @Size(max = 50)
    @JsonView(Views.GetByIdView.class)
    private String secondName;

    @Size(max = 50)
    @JsonView(Views.GetByIdView.class)
    private String middleName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 25)
    @JsonView(Views.GetByIdView.class)
    private String phone;

    @JsonView(Views.GetByIdView.class)
    private Boolean isIdentified;

    private PositionView position;

    private CountryView country;

    private OfficeView office;

    private Set<DocumentDataView> documentDataSet;

    public EmployeeView() {
    }

    public EmployeeView(String id, @Size(max = 50) @NotEmpty(message = "name cannot be null") String firstName,
                        @Size(max = 50) String secondName, @Size(max = 50) String middleName, @Size(max = 50) String lastName,
                        @Size(max = 25) String phone, Boolean isIdentified, PositionView position,
                        CountryView country, OfficeView office) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.isIdentified = isIdentified;
        this.position = position;
        this.country = country;
        this.office = office;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public PositionView getPosition() {
        return position;
    }

    @JsonView(Views.GetByIdView.class)
    @JsonProperty("position")
    public String getPositionName() {
        return position.getName();
    }

    public void setPosition(PositionView position) {
        this.position = position;
    }

    public CountryView getCountry() {
        return country;
    }

    @JsonView(Views.GetByIdView.class)
    public String getcitizenshipCode() {
        return country.getCode();
    }

    @JsonView(Views.GetByIdView.class)
    public String getcitizenshipName(){
        return country.getName();
    }

    public void setCountry(CountryView country) {
        this.country = country;
    }

    public OfficeView getOffice() {
        return office;
    }

    public void setOffice(OfficeView office) {
        this.office = office;
    }

    public String getOfficeId() {
        return office.getId();
    }

    public Set<DocumentDataView> getDocumentDataSet() {
        return documentDataSet;
    }

    public void setDocumentDataSet(Set<DocumentDataView> documentDataSet) {
        this.documentDataSet = documentDataSet;
    }

    // пока в documentDataSet только 1 шт.
    @JsonView(Views.GetByIdView.class)
    public String getDocName(){
        String docName = null;
        if (documentDataSet.iterator().hasNext()) {
            docName = documentDataSet.iterator().next().getDocument().getName();
        }
        return docName;
    }

    // пока в documentDataSet только 1 шт.
    @JsonView(Views.GetByIdView.class)
    public String getDocNumber() {
        String docNumber = null;
        if (documentDataSet.iterator().hasNext()){
            docNumber = documentDataSet.iterator().next().getNumber();
        }
        return docNumber;
    }

    @JsonView(Views.GetByIdView.class)
    public Date getDocDate() {
        Date docDate = null;
        if (documentDataSet.iterator().hasNext()){
            docDate = documentDataSet.iterator().next().getDate();
        }
        return docDate;
    }

    @Override
    public String toString() {
        return "EmployeeView{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", isIdentified=" + isIdentified +
                ", position=" + position +
                ", country=" + country +
                ", office=" + office +
                '}';
    }
}
