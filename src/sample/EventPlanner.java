package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EventPlanner {
    IntegerProperty event_planner_id = new SimpleIntegerProperty();
    IntegerProperty fk_planner_id = new SimpleIntegerProperty();
    IntegerProperty fk_event_id = new SimpleIntegerProperty();

    public EventPlanner(IntegerProperty event_planner_id, IntegerProperty fk_planner_id, IntegerProperty fk_event_id) {
        this.event_planner_id = event_planner_id;
        this.fk_planner_id = fk_planner_id;
        this.fk_event_id = fk_event_id;
    }

    public EventPlanner() {
    }

    public int getEvent_planner_id() {
        return event_planner_id.get();
    }

    public IntegerProperty event_planner_idProperty() {
        return event_planner_id;
    }

    public void setEvent_planner_id(int event_planner_id) {
        this.event_planner_id.set(event_planner_id);
    }

    public int getFk_planner_id() {
        return fk_planner_id.get();
    }

    public IntegerProperty fk_planner_idProperty() {
        return fk_planner_id;
    }

    public void setFk_planner_id(int fk_planner_id) {
        this.fk_planner_id.set(fk_planner_id);
    }

    public int getFk_event_id() {
        return fk_event_id.get();
    }

    public IntegerProperty fk_event_idProperty() {
        return fk_event_id;
    }

    public void setFk_event_id(int fk_event_id) {
        this.fk_event_id.set(fk_event_id);
    }
}
