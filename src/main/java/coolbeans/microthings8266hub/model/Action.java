package coolbeans.microthings8266hub.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Thing thing;

    private String name;
    private String script;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(id, action.id) &&
                Objects.equals(name, action.name) &&
                Objects.equals(script, action.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thing, name, script);
    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", actionName='" + name + '\'' +
                ", script='" + script + '\'' +
                '}';
    }
}
