package pl.openthejar;

import pl.openthejar.dao.DatabaseProxy;
import pl.openthejar.misc.DatabaseService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class WebConfig extends Application {

    public WebConfig() {
        DatabaseProxy.initDatabase();
        new DatabaseService();
    }
}
