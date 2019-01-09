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
 * Вид документа, удостоверяющего личность физического лица
 */
@Entity
public class Document implements Serializable {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Byte id;

    /**
     * Служебное поле Hibernate
     */
    @Version
    private Integer version;

    /**
     * Цифровой код
     */
    @Column(name = "code", length = 2, unique = true, nullable = false)
    private String code;

    /**
     * Название
     */
    @Column(name = "name", length = 50)
    private String name;

    /**
     * Конструктор для Hibernate
     */
    public Document() {

    }

    /**
     * Конструктор
     *
     * @param code цифровой код
     * @param name название
     */
    public Document(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
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
        Document document = (Document) o;
        return Objects.equals(id, document.id) &&
                Objects.equals(code, document.code) &&
                Objects.equals(name, document.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, name);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
