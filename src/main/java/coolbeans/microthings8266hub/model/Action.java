package coolbeans.microthings8266hub.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Thing thing;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Trigger actionCompleteTrigger;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @Lob
    private String script;

    public Action() {}

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

    public Trigger getActionCompleteTrigger() {
        return actionCompleteTrigger;
    }

    public void setActionCompleteTrigger(Trigger actionCompleteTrigger) {
        actionCompleteTrigger.setAction(this);
        this.actionCompleteTrigger = actionCompleteTrigger;
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
        return Objects.hash(id, name, script);
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
