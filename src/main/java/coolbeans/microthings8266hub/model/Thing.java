package coolbeans.microthings8266hub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String deviceId;
    private String ipAddress;
    private String startupActionName;

    @JsonIgnore
    @OneToMany(mappedBy = "thing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pin> pins = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "thing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Map<String, Action> actions = new HashMap<>();

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

    public Map<String, Action> getActions() {
        return actions;
    }

    public void setActions(Map<String, Action> actions) {
        this.actions = actions;
    }

    public void addPin(Pin pin) {
        pin.setThing(this);
        pins.add(pin);
    }

    public void addAction(Action action) {
        action.setThing(this);
        actions.put(action.getName(), action);
    }

    public int getPinCount() {
        return pins.size();
    }

    public int getActionCount() {
        return actions.size();
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
