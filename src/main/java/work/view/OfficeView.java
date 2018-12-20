package work.view;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OfficeView {

    @JsonView(Views.GetByIdView.class)
    private String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    @JsonView(Views.GetByIdView.class)
    private String name;

    @Size(max = 25)
    @JsonView(Views.GetByIdView.class)
    private String phone;

    @Size(max = 100)
    @NotEmpty(message = "address cannot be null")
    @JsonView(Views.GetByIdView.class)
    private String address;

    @JsonView(Views.GetByIdView.class)
    private Boolean isActive;

    private OrganizationView organization;

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public OrganizationView getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationView organization) {
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
