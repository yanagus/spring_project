package work.view.inputView;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import work.view.Views;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Класс офиса для десериализации из JSON
 */
@JsonPropertyOrder({"orgId", "id", "name", "address", "phone", "isActive"})
public class OfficeViewRequest {

    /**
     * Уникальный идентификатор организации
     */
    @NotEmpty(message = "введите id организации", groups = {Views.FilteredList.class})
    private String orgId;

    /**
     * Уникальный идентификатор офиса
     */
    @Null(message = "id должно быть не задано", groups = {Views.SaveView.class, Views.FilteredList.class})
    @NotEmpty(message = "введите id офиса", groups = {Views.UpdateView.class})
    private String id;

    /**
     * Название офиса
     */
    @Size(max = 50, message = "длина названия не должна превышать 50 символов",
            groups = {Views.SaveView.class, Views.FilteredList.class, Views.UpdateView.class})
    @NotEmpty(message = "введите название офиса", groups = {Views.SaveView.class, Views.UpdateView.class})
    private String name;

    /**
     * Телефон
     */
    @Size(max = 25, message = "длина телефона не должна превышать 25 символов",
            groups = {Views.SaveView.class, Views.FilteredList.class, Views.UpdateView.class})
    private String phone;

    /**
     * Адрес
     */
    @Null(message = "адрес должен быть не задан", groups = {Views.FilteredList.class})
    @Size(max = 100, message = "длина адреса не должна превышать 100 символов",
            groups = {Views.SaveView.class, Views.UpdateView.class})
    @NotEmpty(message = "введите адрес офиса", groups = {Views.UpdateView.class})
    private String address;

    /**
     * Статус
     */
    private String isActive;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
