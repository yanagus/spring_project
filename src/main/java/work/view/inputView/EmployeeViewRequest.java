package work.view.inputView;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import work.view.Views;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Класс для десериализации из JSON
 */
@JsonPropertyOrder({"officeId", "id", "firstName", "secondName", "lastName", "middleName", "position", "phone",
        "docCode", "docName", "docNumber", "docDate", "citizenshipCode", "isIdentified"})
public class EmployeeViewRequest {

    /**
     * Уникальный идентификатор офиса
     */
    @NotEmpty(message = "введите id офиса", groups = {Views.FilteredList.class})
    private String officeId;

    /**
     * Уникальный идентификатор работника
     */
    @Null(message = "id должно быть не задано", groups = {Views.SaveView.class})
    @NotEmpty(message = "введите id работника", groups = {Views.UpdateView.class})
    private String id;

    /**
     * Имя
     */
    @Size(max = 50, message = "длина имени не должна превышать 50 символов", groups = {Views.FilteredList.class,
            Views.UpdateView.class, Views.SaveView.class})
    @NotEmpty(message = "введите имя работника", groups = {Views.UpdateView.class, Views.SaveView.class})
    private String firstName;

    /**
     * Второе имя
     */
    @Null(message = "второе имя должно быть не задано", groups = {Views.FilteredList.class})
    @Size(max = 50, message = "длина второго имени не должна превышать 50 символов",
            groups = {Views.UpdateView.class, Views.SaveView.class})
    private String secondName;

    /**
     * Среднее имя
     */
    @Size(max = 50, message = "длина среднего имени не должна превышать 50 символов",
            groups = {Views.FilteredList.class, Views.UpdateView.class, Views.SaveView.class})
    private String middleName;

    /**
     * Фамилия
     */
    @Null(message = "Фамилия должна быть не задана", groups = {Views.UpdateView.class, Views.SaveView.class})
    @Size(max = 50, message = "длина фамилии не должна превышать 50 символов", groups = {Views.FilteredList.class})
    private String lastName;

    /**
     * Должность
     */
    @Size(max = 50, message = "длина должности не должна превышать 50 символов",
            groups = {Views.FilteredList.class, Views.UpdateView.class, Views.SaveView.class})
    @NotEmpty(message = "введите должность работника", groups = {Views.UpdateView.class, Views.SaveView.class})
    private String position;

    /**
     * Телефон
     */
    @Null(message = "телефон должен быть не задан", groups = {Views.FilteredList.class, Views.UpdateView.class})
    @Size(max = 25, message = "длина телефона не должна превышать 25 символов", groups = {Views.SaveView.class})
    private String phone;

    /**
     * Цифровой код документа
     */
    @Null(message = "код документа должен быть не задан", groups = {Views.UpdateView.class})
    @Size(max = 2, message = "длина кода документа должна быть 2 цифры",
            groups = {Views.FilteredList.class, Views.SaveView.class})
    private String docCode;

    /**
     * Номер документа
     */
    @Null(message = "номер документа должен быть не задан", groups = {Views.FilteredList.class})
    @Size(max = 30, message = "длина номера документа не должна превышать 30 символов",
            groups = {Views.UpdateView.class, Views.SaveView.class})
    private String docNumber;

    /**
     * Название документа
     */
    @Null(message = "название документа должно быть не задано", groups = {Views.FilteredList.class})
    @Size(max = 50, message = "длина названия документа не должна превышать 50 символов",
            groups = {Views.UpdateView.class, Views.SaveView.class})
    private String docName;

    /**
     * Дата документа
     */
    @Null(message = "дата документа должна быть не задана", groups = {Views.FilteredList.class})
    private Date docDate;

    /**
     * Цифровой код страны
     */
    @Size(max = 3, message = "длина кода страны должна быть 3 цифры",
            groups = {Views.FilteredList.class, Views.UpdateView.class, Views.SaveView.class})
    private String citizenshipCode;

    /**
     * Статус
     */
    @Null(message = "статус должен быть не задан", groups = {Views.FilteredList.class})
    private String isIdentified;


    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public String getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(String isIdentified) {
        this.isIdentified = isIdentified;
    }
}
