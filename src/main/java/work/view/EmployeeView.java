package work.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class EmployeeView {

    public String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    public String firstName;

    @Size(max = 50)
    public String secondName;

    @Size(max = 50)
    public String middleName;

    @Size(max = 50)
    public String lastName;

    @Size(max = 25)
    public String phone;

    public Boolean isIdentified;

    public PositionView position;

    public CountryView country;

    @JsonIgnore
    public OfficeView office;

    public Set<DocumentDataView> documentDataSet;

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
