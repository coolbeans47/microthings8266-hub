package coolbeans.microthings8266hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import coolbeans.microthings8266hub.model.Thing;
import coolbeans.microthings8266hub.service.repositories.ActionService;
import coolbeans.microthings8266hub.service.repositories.PinService;
import coolbeans.microthings8266hub.service.repositories.ThingService;
import coolbeans.microthings8266hub.service.repositories.map.ThingMapService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThingControllerTest {


    ThingController thingController;

    ThingService thingService;

    @Mock
    PinService pinService;

    @Mock
    ActionService actionService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        thingService = new ThingMapService(pinService, actionService);
        thingController = new ThingController(thingService);

        Thing thing = new Thing();
        thing.setName("Test Thing1");
        thing.setDeviceId("TEST-DEV1");
        thing.setIpAddress("127.0,0,1");
        thing.setStartupActionName("STARTUPACTION1");
        thingService.save(thing);

        Thing thing2 = new Thing();
        thing2.setName("Test Thing2");
        thing2.setDeviceId("TEST-DEV2");
        thing2.setIpAddress("127.0,0,2");
        thing2.setStartupActionName("STARTUPACTION2");
        thingService.save(thing2);

        mockMvc = MockMvcBuilders.standaloneSetup(thingController).build();
    }

    @Test
    public void getThings() throws Exception {
        mockMvc.perform(get("/things"))
                .andExpect(status().is(200))
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].id", is(1)))
                        .andExpect(jsonPath("$[1].deviceId", is("TEST-DEV2"))
                        );
    }

    @Test
    public void getThing() throws Exception {
        mockMvc.perform(get("/things/2"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("deviceId", is("TEST-DEV2"))
                );
    }

    @Test
    public void getThingNotFound() throws Exception {
        mockMvc.perform(get("/things/123"))
                .andExpect(status().is(404));
    }

    @Test
    public void postThing() throws Exception {
        Thing thing = new Thing();
        thing.setName("Test Thing3");
        thing.setDeviceId("TEST-DEV2");
        thing.setIpAddress("127.0,0,3");
        thing.setStartupActionName("STARTUPACTION3");


        mockMvc.perform(post("/things")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(thing))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(3)))
            ;

        assertEquals(3, thingService.findAll().size());
    }

    @Test
    public void putThing() throws Exception {

        Thing thing = thingService.findById(1L);
        thing.setName("UPDATED");

        mockMvc.perform(put("/things/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(thing))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("UPDATED")))
        ;

        assertEquals(2, thingService.findAll().size());
        Thing updated = thingService.findById(1L);
        assertEquals(thing.getName(), "UPDATED");
    }

    @Test
    public void putThingNotFound() throws Exception {

        Thing thing = thingService.findById(1L);

        mockMvc.perform(put("/things/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(thing))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteThing() throws Exception {
        assertNotNull(thingService.findById(2L));
        mockMvc.perform(delete("/things/2"))
                .andExpect(status().is(200));
        assertNull(thingService.findById(2L));
    }


    @Test
    public void deleteThingNotFound() throws Exception {
        mockMvc.perform(delete("/things/123"))
                .andExpect(status().is(404));
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}