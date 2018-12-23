package work.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@JsonPropertyOrder({"id", "name", "fullName", "inn", "kpp", "address", "phone", "isActive"})
public class OrganizationView {

    private String id;

    @Size(max = 50, message = "длина названия не должна превышать 50 символов")
    @NotEmpty(message = "введите название организации")
    private String name;

    @Size(max = 80, message = "длина полного названия не должна превышать 80 символов")
    @NotEmpty(message = "введите полное название организации")
    private String fullName;

    @Size(min = 10, max = 12, message = "длина ИНН должна быть 10 или 12 цифр")
    @NotEmpty(message = "введите ИНН организации")
    private String inn;

    @Size(max = 9, message = "длина КПП должна быть 9 цифр")
    @NotEmpty(message = "введите КПП организации")
    private String kpp;

    @Size(max = 25, message = "длина телефона не должна превышать 25 символов")
    private String phone;

    @Size(max = 100, message = "длина адреса не должна превышать 100 символов")
    @NotEmpty(message = "введите адрес организации")
    private String address;

    private Boolean isActive = false;

    public OrganizationView() {
    }

    public OrganizationView(String id, @Size(max = 50) @NotEmpty(message = "name cannot be null") String name,
                            @Size(max = 80) @NotEmpty(message = "full name cannot be null") String fullName,
                            @Size(max = 12) @NotEmpty(message = "inn cannot be null") String inn,
                            @Size(max = 9) @NotEmpty(message = "kpp cannot be null") String kpp,
                            @Size(max = 25) String phone, @Size(max = 100) @NotEmpty(message = "address cannot be null") String address,
                            Boolean isActive) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.phone = phone;
        this.address = address;
        this.isActive = isActive;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationView that = (OrganizationView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(inn, that.inn) &&
                Objects.equals(kpp, that.kpp) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(address, that.address) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, fullName, inn, kpp, phone, address, isActive);
    }

    @Override
    public String toString() {
        return "OrganizationView{" +
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
