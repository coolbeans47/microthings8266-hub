package coolbeans.microthings8266hub.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 0, max = 30)
    private Integer pinNbr;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    private PinMode pinMode;

    @ManyToOne
    private Thing thing;

    public Pin() {
    }

    public Pin(Integer pinNbr, String name, PinMode pinMode) {
        this.pinNbr = pinNbr;
        this.name = name;
        this.pinMode = pinMode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPinNbr() {
        return pinNbr;
    }

    public void setPinNbr(Integer pinNbr) {
        this.pinNbr = pinNbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PinMode getPinMode() {
        return pinMode;
    }

    public void setPinMode(PinMode pinMode) {
        this.pinMode = pinMode;
    }


    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }


    @Override
    public String toString() {
        return "Pin{" +
                "id=" + id +
                ", pinNbr=" + pinNbr +
                ", name='" + name + '\'' +
                ", pinMode=" + pinMode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pin pin = (Pin) o;
        return Objects.equals(id, pin.id) &&
                Objects.equals(pinNbr, pin.pinNbr) &&
                Objects.equals(name, pin.name) &&
                pinMode == pin.pinMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pinNbr, name, pinMode);
    }
}
