package coolbeans.microthings8266hub.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Size(min = 3, max = 100)
    private String deviceId;

    @NotNull
    private String ipAddress;
    private String startupActionName;

    @OneToMany(mappedBy = "thing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pin> pins = new ArrayList<>();

    @OneToMany(mappedBy = "thing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Action> actions = new ArrayList<>();

    public Thing() {}

    public Thing(String name, String deviceId, String ipAddress, String startupActionName) {
        this.name = name;
        this.deviceId = deviceId;
        this.ipAddress = ipAddress;
        this.startupActionName = startupActionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        if (name == null) return deviceId;
        return name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getStartupActionName() {
        return startupActionName;
    }

    public void setStartupActionName(String startupActionName) {
        this.startupActionName = startupActionName;
    }

    public List<Pin> getPins() {
        return pins;
    }

    public void setPins(List<Pin> pins) {
        this.pins = pins;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void addPin(Pin pin) {
        pin.setThing(this);
        pins.add(pin);
    }

    public void addAction(Action action) {
        action.setThing(this);
        actions.add(action);
    }


    @Override
    public String toString() {
        return "Thing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deviceId='" + deviceId+ '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", startupActionId=" + startupActionName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return Objects.equals(id, thing.id) &&
                Objects.equals(name, thing.name) &&
                Objects.equals(deviceId, thing.deviceId) &&
                Objects.equals(ipAddress, thing.ipAddress) &&
                Objects.equals(startupActionName, thing.startupActionName) &&
                Objects.equals(pins, thing.pins) &&
                Objects.equals(actions, thing.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deviceId, ipAddress, startupActionName, pins, actions);
    }


}
