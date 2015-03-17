package tools.events;

import org.springframework.context.ApplicationEvent;

/**
 * Created by Nick on 09/03/2015.
 */
public class ProcessEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public ProcessEvent(Object source) {
        super(source);
    }
}
