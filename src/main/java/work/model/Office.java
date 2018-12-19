package work.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Офис
 */
@Entity
public class Office implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Название
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * Телефон
     */
    @Column(name = "phone", length = 25)
    private String phone;

    /**
     * Адрес
     */
    @Column(name = "address", length = 100, nullable = false)
    private String address;

    /**
     * Статус
     */
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;

    @OneToMany(mappedBy = "office")
    private Set<Employee> employees;

    /**
     * Конструктор для hibernate
     */
    public Office() {

    }

    public Office(String name, String phone, String address, Boolean isActive, Organization organization) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.isActive = isActive;
        this.organization = organization;
    }

    public Integer getId() {
        return id;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        getEmployees().add(employee);
        employee.setOffice(this);
    }

    public void removeEmployee(Employee employee) {
        getEmployees().remove(employee);
        employee.setOffice(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Objects.equals(id, office.id) &&
                Objects.equals(name, office.name) &&
                Objects.equals(phone, office.phone) &&
                Objects.equals(address, office.address) &&
                Objects.equals(isActive, office.isActive) &&
                Objects.equals(organization, office.organization);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, phone, address, isActive, organization);
    }
}
