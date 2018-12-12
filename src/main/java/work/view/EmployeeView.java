package work.view;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class EmployeeView {

    public String id;

    @Size(max = 50)
    @NotEmpty(message = "name cannot be null")
    public String first_name;

    @Size(max = 50)
    public String second_name;

    @Size(max = 50)
    public String middle_name;

    @Size(max = 50)
    public String last_name;

    @Size(max = 25)
    public String phone;

    public Boolean isIdentified;

    public String pos_id;

    public String country_id;

    public String office_id;

    public EmployeeView() {
    }

    //конструктор для заглушки
    public EmployeeView(String id, @Size(max = 50) @NotEmpty(message = "name cannot be null") String first_name,
                        @Size(max = 50) String second_name, @Size(max = 50) String middle_name, @Size(max = 50) String last_name,
                        @Size(max = 25) String phone, Boolean isIdentified, String pos_id, String country_id, String office_id) {
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.phone = phone;
        this.isIdentified = isIdentified;
        this.pos_id = pos_id;
        this.country_id = country_id;
        this.office_id = office_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeView that = (EmployeeView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(first_name, that.first_name) &&
                Objects.equals(second_name, that.second_name) &&
                Objects.equals(middle_name, that.middle_name) &&
                Objects.equals(last_name, that.last_name) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(isIdentified, that.isIdentified) &&
                Objects.equals(pos_id, that.pos_id) &&
                Objects.equals(country_id, that.country_id) &&
                Objects.equals(office_id, that.office_id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, first_name, second_name, middle_name, last_name, phone, isIdentified, pos_id, country_id, office_id);
    }
}
