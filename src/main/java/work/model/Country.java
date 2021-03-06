package work.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;

/**
 * Страна
 */
@Entity
public class Country implements Serializable {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    /**
     * Служебное поле Hibernate
     */
    @Version
    private Integer version;

    /**
     * Цифровой код
     */
    @Column(name = "code", length = 3, unique = true, nullable = false)
    private String code;

    /**
     * Название
     */
    @Column(name = "name", length = 50)
    private String name;

    /**
     * Конструктор для Hibernate
     */
    public Country() {

    }

    /**
     * Конструктор
     *
     * @param code цифровой код
     * @param name название
     */
    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(code, country.code) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
