package coolbeans.microthings8266hub.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Trigger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    private Action action;


    @ManyToMany(mappedBy = "trigger", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TriggerAction> triggerActions = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private int repeatCount;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }


    public List<TriggerAction> getTriggerActions() {
        return triggerActions;
    }

    public void addTriggerAction(TriggerAction triggerAction) {
        triggerAction.setTrigger(this);
        triggerActions.add(triggerAction);
    }

    public void setTriggerActions(List<TriggerAction> triggerActions) {
        this.triggerActions = triggerActions;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public int getTriggerActionCount() {
        return triggerActions.size();
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trigger trigger = (Trigger) o;
        return repeatCount == trigger.repeatCount &&
                Objects.equals(id, trigger.id) &&
                Objects.equals(triggerActions, trigger.triggerActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, triggerActions, repeatCount);
    }

    @Override
    public String toString() {
        return "Trigger{" +
                "id=" + id +
                ", triggerActions=" + triggerActions +
                ", repeatCount=" + repeatCount +
                '}';
    }
}
