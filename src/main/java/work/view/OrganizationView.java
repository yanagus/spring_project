package work.view;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import work.view.inputView.OrganizationViewRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс организации для сериализации в JSON
 */
@JsonPropertyOrder({"id", "name", "fullName", "inn", "kpp", "address", "phone", "isActive"})
public class OrganizationView {

    /**
     * Уникальный идентификатор организации
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String id;

    /**
     * Название
     */
    @JsonView({Views.GetByIdView.class, Views.FilteredList.class})
    private String name;

    /**
     * Полное название
     */
    @JsonView(Views.GetByIdView.class)
    private String fullName;

    /**
     * ИНН
     */
    @JsonView(Views.GetByIdView.class)
    private String inn;

    /**
     * КПП
     */
    @JsonView(Views.GetByIdView.class)
    private String kpp;

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
     * Множество офисов, относящихся к данной организиции
     */
    @JsonBackReference
    private Set<OfficeView> offices;

    public OrganizationView() {
    }

    public OrganizationView(String id, String name, String fullName, String inn, String kpp, String address, String phone, String isActive, Set<OfficeView> offices) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.setInn(inn);
        this.kpp = kpp;
        this.phone = phone;
        this.address = address;
        setIsActive(isActive);
        setOffices(offices);
    }

    /**
     * Конструктор для преобразования из класса для десериализации
     * @param orgRequest
     */
    public OrganizationView(OrganizationViewRequest orgRequest) {
        this(orgRequest.getId(), orgRequest.getName(), orgRequest.getFullName(), orgRequest.getInn(), orgRequest.getKpp(),
                orgRequest.getAddress(), orgRequest.getPhone(), orgRequest.getIsActive(), Collections.emptySet());
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
        if(inn != null) {
            this.inn = inn.trim();
        }
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

    public void setOffices(Set<OfficeView> offices) {
        if (offices == null) {
            this.offices = Collections.emptySet();
        }
        this.offices = offices;
    }

    public Set<OfficeView> getOffices() {
        if (offices == null) {
            offices = new HashSet<>();
        }
        return offices;
    }

    public void addOffice(OfficeView officeView) {
        getOffices().add(officeView);
        officeView.setOrganization(this);
    }

    public void removeOffice(OfficeView officeView) {
        getOffices().remove(officeView);
        officeView.setOrganization(null);
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
                ", isActive='" + isActive + '\'' +
                ", offices=" + offices +
                '}';
    }
}
