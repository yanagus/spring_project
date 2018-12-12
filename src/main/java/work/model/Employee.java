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
import javax.persistence.CascadeType;
import java.util.Objects;
import java.util.Set;

/**
 * Работник
 */
@Entity
public class Employee {

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
     * Имя
     */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * Второе имя
     */
    @Column(name = "second_name", length = 50)
    private String secondName;

    /**
     * Среднее имя
     */
    @Column(name = "middle_name", length = 50)
    private String middleName;

    /**
     * Фамилия
     */
    @Column(name = "last_name", length = 50)
    private String lastName;

    /**
     * Телефон
     */
    @Column(name = "phone", length = 25)
    private String phone;

    /**
     * Статус
     */
    @Column(name = "is_identified")
    private Boolean isIdentified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pos_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DocumentData> documentDataSet;

    /**
     * Конструктор для hibernate
     */
    public Employee() {

    }

    public Employee(String firstName, String secondName, String middleName, String lastName, String phone,
                    Boolean isIdentified, Position position, Country country, Office office) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.isIdentified = isIdentified;
        this.position = position;
        this.country = country;
        this.office = office;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Set<DocumentData> getDocumentDataSet() {
        return documentDataSet;
    }

    public void addDocumentData(DocumentData documentData) {
        getDocumentDataSet().add(documentData);
        documentData.setEmployee(this);
    }

    public void removeDocumentData(DocumentData documentData) {
        getDocumentDataSet().remove(documentData);
        documentData.setEmployee(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(secondName, employee.secondName) &&
                Objects.equals(middleName, employee.middleName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(isIdentified, employee.isIdentified) &&
                Objects.equals(position, employee.position) &&
                Objects.equals(country, employee.country) &&
                Objects.equals(office, employee.office);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, secondName, middleName, lastName, phone, isIdentified, position, country, office);
    }

}
