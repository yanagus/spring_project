package work.view;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import work.view.inputView.OfficeViewRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс офиса для сериализации в JSON
 */
@JsonPropertyOrder({"id", "name", "address", "phone", "isActive"})
public class OfficeView {

    /**
     * Уникальный идентификатор офиса
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String id;

    /**
     * Название офиса
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class, Views.SaveView.class})
    private String name;

    /**
     * Телефон
     */
    @JsonView(Views.GetByIdView.class)
    private String phone;

    /**
     * Адрес
     */
    @JsonView(Views.GetByIdView.class)
    private String address;

    /**
     * Статус
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String isActive;

    /**
     * Организация
     */
    private OrganizationView organization;

    /**
     * Работники
     */
    @JsonBackReference
    private Set<EmployeeView> employees;

    public OfficeView() {
    }

    public OfficeView(String id, String name, String phone, String address, String isActive, OrganizationView organization, Set<EmployeeView> employees) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        setIsActive(isActive);
        this.organization = organization;
        setEmployees(employees);
    }

    /**
     * Конструктор для преобразования из класса для десериализации
     * @param officeRequest класс для десериализации из JSON
     */
    public OfficeView(OfficeViewRequest officeRequest) {
        this(officeRequest.getId(), officeRequest.getName(), officeRequest.getPhone(), officeRequest.getAddress(), officeRequest.getIsActive(),
                null, Collections.emptySet());
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

    public void setIsActive(String active) {
        isActive = active;
    }

    public void setIsActive(Boolean active) {
        if (active != null) {
            isActive = active.toString();
        }
    }

    public OrganizationView getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationView organization) {
        this.organization = organization;
        if (organization != null) {
            organization.getOffices().add(this);
        }
    }

    public Set<EmployeeView> getEmployees() {
        if (employees == null) {
            employees = new HashSet<>();
        }
        return employees;
    }

    public void setEmployees(Set<EmployeeView> employees) {
        if (employees == null) {
            this.employees = Collections.emptySet();
        }
        this.employees = employees;
    }

    public void addEmployee(EmployeeView employee) {
        getEmployees().add(employee);
        employee.setOffice(this);
    }

    public void removeEmployee(EmployeeView employee) {
        getEmployees().remove(employee);
        employee.setOffice(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeView that = (OfficeView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(address, that.address) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, phone, address, isActive, organization);
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
