package coolbeans.microthings8266hub.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ipAddress;
    private Long startupActionId;

    @OneToMany(mappedBy = "thing", cascade = CascadeType.ALL)
    private List<Pin> pins = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getStartupActionId() {
        return startupActionId;
    }

    public void setStartupActionId(Long startupActionId) {
        this.startupActionId = startupActionId;
    }

    public List<Pin> getPins() {
        return pins;
    }

    public void setPins(List<Pin> pins) {
        this.pins = pins;
    }

    public void addPin(Pin pin) {
        pin.setThing(this);
        pins.add(pin);
    }

    @Override
    public String toString() {
        return "Thing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", startupActionId=" + startupActionId +
                ", pins=" + pins +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return Objects.equals(id, thing.id) &&
                Objects.equals(name, thing.name) &&
                Objects.equals(ipAddress, thing.ipAddress) &&
                Objects.equals(startupActionId, thing.startupActionId) &&
                Objects.equals(pins, thing.pins);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ipAddress, startupActionId, pins);
    }
}
