package work.view;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class OrganizationView {

    public String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    public String name;

    @Size(max = 80)
    @NotEmpty(message = "full name cannot be null")
    public String fullName;

    @Size(max = 12)
    @NotEmpty(message = "inn cannot be null")
    public String inn;

    @Size(max = 9)
    @NotEmpty(message = "kpp cannot be null")
    public String kpp;

    @Size(max = 25)
    public String phone;

    @Size(max = 100)
    @NotEmpty(message = "address cannot be null")
    public String address;

    public Boolean isActive;

    public OrganizationView() {
    }

    //конструктор для заглушки
    public OrganizationView(String id, @Size(max = 50) @NotEmpty(message = "name cannot be null") String name, @Size(max = 80) @NotEmpty(message = "full name cannot be null") String fullName, @Size(max = 12) @NotEmpty(message = "inn cannot be null") String inn, @Size(max = 9) @NotEmpty(message = "kpp cannot be null") String kpp, @Size(max = 25) String phone, @Size(max = 100) @NotEmpty(message = "address cannot be null") String address, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.phone = phone;
        this.address = address;
        this.isActive = isActive;
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
}
