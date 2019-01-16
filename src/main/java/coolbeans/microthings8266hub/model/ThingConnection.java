package coolbeans.microthings8266hub.model;

public class ThingConnection {
    private String name;
    private String ipAddress;

    public ThingConnection() {}

    public ThingConnection(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
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


    @Override
    public String toString() {
        return "ThingConnection{" +
                "name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
