package work.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@JsonPropertyOrder({"id", "name", "fullName", "inn", "kpp", "address", "phone", "isActive"})
public class OrganizationView {

    private String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    private String name;

    @Size(max = 80)
    @NotEmpty(message = "full name cannot be null")
    private String fullName;

    @Size(max = 12)
    @NotEmpty(message = "inn cannot be null")
    private String inn;

    @Size(max = 9)
    @NotEmpty(message = "kpp cannot be null")
    private String kpp;

    @Size(max = 25)
    private String phone;

    @Size(max = 100)
    @NotEmpty(message = "address cannot be null")
    private String address;

    private Boolean isActive;

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
