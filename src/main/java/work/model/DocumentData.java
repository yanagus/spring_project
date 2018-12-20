package work.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Персональные данные работника
 */
@Entity
@Table(name = "Document_Data")
public class DocumentData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private Document document;

    /**
     * Номер документа
     */
    @Column(name = "number", length = 30, unique = true, nullable = false)
    private String number;

    /**
     * Дата документа
     */
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empl_id")
    private Employee employee;

    /**
     * Конструктор для hibernate
     */
    public DocumentData() {

    }

    public DocumentData(Document document, String number, Date date, Employee employee) {
        this.document = document;
        this.number = number;
        this.date = date;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentData that = (DocumentData) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(document, that.document) &&
                Objects.equals(number, that.number) &&
                Objects.equals(date, that.date) &&
                Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, document, number, date, employee);
    }
}
