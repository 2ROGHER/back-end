package vollmed.models.dog;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity(name="Dog")
@Table(name="Dogs")
@Getter
@EqualsAndHashCode(of = "id")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    private String color;

    public Dog() {}

    public Dog(DataDog d) {
        this.id = d.id();
        this.name = d.name();
        this.age = d.age();
        this.color =d.color();
    }
}
