package de.hbrs.ia.hooks;

import de.hbrs.ia.db.MongoConnection;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ExitHook {

    @PreDestroy
    public void onExit() {
        MongoConnection.getInstance().close();
    }
}
