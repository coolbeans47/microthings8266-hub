package coolbeans.microthings8266hub.controller;

import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/things")
public class ThingController {

    private final ThingService thingService;

    public ThingController(ThingService thingService) {
        this.thingService = thingService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Thing> findAll() {
        return thingService.findAll();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Thing getThing(@PathVariable Long id, HttpServletResponse response) {
        Thing existing = thingService.findById(id);
        if (existing != null) {
            response.setStatus(HttpStatus.OK.value());
            return existing;
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Thing post(@RequestBody Thing thing, HttpServletResponse response) {
        if (thing != null) {
            thingService.save(thing);
            response.setStatus(HttpStatus.CREATED.value());
        }
        return thing;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Thing post(@PathVariable Long id, @RequestBody Thing thing, HttpServletResponse response) {
        if (thing != null) {
            Thing existing = thingService.findById(id);
            if (existing != null) {
                thing.setId(id);
                response.setStatus(HttpStatus.OK.value());
                Thing result = thingService.save(thing);
                return result;
            }
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        Thing existing = thingService.findById(id);
        if (existing != null) {
            thingService.deleteById(id);
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

}
