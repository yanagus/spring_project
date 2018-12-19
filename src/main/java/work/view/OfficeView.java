package work.view;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OfficeView {

    public String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    public String name;

    @Size(max = 25)
    public String phone;

    @Size(max = 100)
    @NotEmpty(message = "address cannot be null")
    public String address;

    public Boolean isActive;

    public OrganizationView organization;

    public OfficeView() {
    }

    public OfficeView(String id, @Size(max = 50) @NotEmpty(message = "name cannot be null") String name,
                      @Size(max = 25) String phone, @Size(max = 100) @NotEmpty(message = "address cannot be null") String address,
                      Boolean isActive, OrganizationView organization) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.isActive = isActive;
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "OfficeView{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", organization=" + organization +
                '}';
    }
}
