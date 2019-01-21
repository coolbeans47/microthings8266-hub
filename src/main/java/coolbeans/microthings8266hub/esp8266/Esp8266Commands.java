package coolbeans.microthings8266hub.esp8266;

public enum Esp8266Commands {
    ECHO(0), PINMODE(1), DIGITAL_READ(2), DIGITAL_WRITE(3);

    private int value;

    Esp8266Commands(int value) { this.value = value; }

    public int getValue() {
        return value;
    }
}
