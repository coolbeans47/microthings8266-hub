package coolbeans.microthings8266hub.bootstrap;

import coolbeans.microthings8266hub.model.Action;
import coolbeans.microthings8266hub.model.Pin;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"default","map"})
public class LoadTestData implements CommandLineRunner {

    private final ThingService thingService;

    public LoadTestData(ThingService thingService) {
        this.thingService = thingService;
    }


    @Override
    public void run(String... args) throws Exception {


        Thing thing = new Thing();
        thing.setDeviceId("80:7D:3A:29:86:B5");
        thing.setName(thing.getDeviceId());
        thing.setIpAddress("192.168.0.0");
        thing.setStartupActionName("LEDON");
        thing.addPin(createPin("LEDPIN", 14));
        thing.addAction(createAction("LEDON",
                "function run() {" +
                        "gpio.pinMode(LEDPIN, OUTPUT); gpio.digitalWrite(LEDPIN, HIGH)" +
                        "}"));
        thing.addAction(createAction("LEDOFF",
                "function run() {" +
                        "gpio.pinMode(LEDPIN, OUTPUT); gpio.digitalWrite(LEDPIN, LOW)" +
                        "}"));
        thingService.save(thing);
    }

    private Action createAction(String name, String script) {
        Action action = new Action();
        action.setName(name);
        action.setScript(script);
        return action;
    }

    private Pin createPin(String name, int pinNbr) {
        Pin pin = new Pin();
        pin.setName(name);
        pin.setPinNbr(pinNbr);

        return pin;
    }
}
