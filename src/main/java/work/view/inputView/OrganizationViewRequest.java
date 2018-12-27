package work.view.inputView;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import work.view.Views;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Класс для десериализации из JSON
 */
@JsonPropertyOrder({"id", "name", "fullName", "inn", "kpp", "address", "phone", "isActive"})
public class OrganizationViewRequest {

    /**
     * Уникальный идентификатор организации
     */
    @Null(groups = {Views.SaveView.class})
    @NotEmpty(message = "введите id организации", groups = {Views.UpdateView.class})
    @JsonView(Views.UpdateView.class)
    private String id;

    /**
     * Название
     */
    @Size(max = 50, message = "длина названия не должна превышать 50 символов", groups = {Views.UpdateView.class, Views.SaveView.class, Views.FilteredList.class})
    @NotEmpty(message = "введите название организации", groups = {Views.UpdateView.class, Views.SaveView.class, Views.FilteredList.class})
    @JsonView({Views.UpdateView.class, Views.SaveView.class, Views.FilteredList.class})
    private String name;

    /**
     * Полное название
     */
    @Size(max = 80, message = "длина полного названия не должна превышать 80 символов", groups = {Views.UpdateView.class, Views.SaveView.class})
    @NotEmpty(message = "введите полное название организации", groups = {Views.UpdateView.class, Views.SaveView.class})
    @JsonView({Views.UpdateView.class, Views.SaveView.class})
    private String fullName;

    /**
     * ИНН
     */
    @Size(min = 10, max = 12, message = "длина ИНН должна быть 10 или 12 цифр", groups = {Views.UpdateView.class, Views.SaveView.class})
    @NotEmpty(message = "введите ИНН организации", groups = {Views.UpdateView.class, Views.SaveView.class})
    @JsonView({Views.UpdateView.class, Views.SaveView.class, Views.FilteredList.class})
    private String inn;

    /**
     * КПП
     */
    @Size(max = 9, message = "длина КПП должна быть 9 цифр", groups = {Views.UpdateView.class, Views.SaveView.class})
    @NotEmpty(message = "введите КПП организации", groups = {Views.UpdateView.class, Views.SaveView.class})
    @JsonView({Views.UpdateView.class, Views.SaveView.class})
    private String kpp;

    /**
     * Телефон
     */
    @Size(max = 25, message = "длина телефона не должна превышать 25 символов", groups = {Views.UpdateView.class, Views.SaveView.class})
    @JsonView({Views.UpdateView.class, Views.SaveView.class})
    private String phone;

    /**
     * Адрес
     */
    @Size(max = 100, message = "длина адреса не должна превышать 100 символов", groups = {Views.UpdateView.class, Views.SaveView.class})
    @NotEmpty(message = "введите адрес организации", groups = {Views.UpdateView.class, Views.SaveView.class})
    @JsonView({Views.UpdateView.class, Views.SaveView.class})
    private String address;

    /**
     * Статус
     */
    @JsonView({Views.UpdateView.class, Views.SaveView.class, Views.FilteredList.class})
    private Boolean isActive = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "OrganizationViewRequest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
