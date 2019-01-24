package coolbeans.microthings8266hub.model;

public class ThingConnectionRequest {
    private String deviceId;
    private String ipAddress;

    public ThingConnectionRequest() {}

    public ThingConnectionRequest(String deviceId, String ipAddress) {
        this.deviceId = deviceId;
        this.ipAddress = ipAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
                "deviceId='" + deviceId + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
