package work.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import work.view.inputView.EmployeeViewRequest;

import java.util.Date;
import java.util.Objects;

/**
 * Класс работника для сериализации в JSON
 */
@JsonPropertyOrder({"id", "firstName", "secondName", "middleName", "lastName", "position", "phone", "docName", "docNumber",
"docDate", "citizenshipName", "citizenshipCode", "isIdentified"})
public class EmployeeView {

    /**
     * Уникальный идентификатор работника
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String id;

    /**
     * Имя
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String firstName;

    /**
     * Второе имя
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String secondName;

    /**
     * Среднее имя
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String middleName;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Телефон
     */
    @JsonView(Views.GetByIdView.class)
    private String phone;

    /**
     * Статус
     */
    @JsonView(Views.GetByIdView.class)
    private String isIdentified;

    /**
     * Должность
     */
    private PositionView position;

    /**
     * Гражданство
     */
    private CountryView country;

    /**
     * Офис
     */
    private OfficeView office;

    /**
     * Данные документа
     */
    @JsonManagedReference
    private DocumentDataView documentData;

    public EmployeeView() {
    }

    public EmployeeView(String id, String firstName, String secondName, String middleName, String lastName, String phone,
                        String isIdentified, PositionView position, CountryView country, OfficeView office, DocumentDataView documentData) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        setIsIdentified(isIdentified);
        this.position = position;
        this.country = country;
        this.office = office;
        this.documentData = documentData;
    }

    /**
     * Конструктор для преобразования из класса для десериализации
     * @param employeeRequest класс для десериализации
     */
    public EmployeeView(EmployeeViewRequest employeeRequest) {
        this(employeeRequest.getId(), employeeRequest.getFirstName(), employeeRequest.getSecondName(), employeeRequest.getMiddleName(),
                employeeRequest.getLastName(), employeeRequest.getPhone(), employeeRequest.getIsIdentified(), null, null, null, null);
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

    public String getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(String identified) {
        isIdentified = identified;
    }

    public void setIsIdentified(Boolean identified) {
        if (identified != null) {
            isIdentified = identified.toString();
        }
    }

    public PositionView getPosition() {
        return position;
    }

    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    @JsonProperty("position")
    public String getPositionName() {
        if (position == null) {
            return null;
        }
        return position.getName();
    }

    public void setPosition(PositionView position) {
        this.position = position;
    }

    public CountryView getCountry() {
        return country;
    }

    @JsonView(Views.GetByIdView.class)
    public String getCitizenshipCode() {
        if (country == null) {
            return null;
        }
        return country.getCode();
    }

    @JsonView(Views.GetByIdView.class)
    public String getCitizenshipName(){
        if (country == null) {
            return null;
        }
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

    public DocumentDataView getDocumentData() {
        return documentData;
    }

    public void setDocumentData(DocumentDataView documentData) {
        this.documentData = documentData;
    }

    @JsonView(Views.GetByIdView.class)
    public String getDocName(){
        if (documentData == null) {
            return null;
        }
        return documentData.getDocument().getName();
    }

    @JsonView(Views.GetByIdView.class)
    public String getDocNumber() {
        if (documentData == null) {
            return null;
        }
        return documentData.getNumber();
    }

    @JsonView(Views.GetByIdView.class)
    public Date getDocDate() {
        if (documentData == null) {
            return null;
        }
        return documentData.getDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeView that = (EmployeeView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(isIdentified, that.isIdentified) &&
                Objects.equals(position, that.position) &&
                Objects.equals(country, that.country) &&
                Objects.equals(office, that.office) &&
                Objects.equals(documentData, that.documentData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, secondName, middleName, lastName, phone, isIdentified, position, country, office);
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
