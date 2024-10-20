package de.hbrs.ia.hooks;

import de.hbrs.ia.db.MongoConnection;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ExitHook {

    @PreDestroy
    public void onExit() {
        // close the connection to the database
        MongoConnection.getInstance().close();
    }
}
