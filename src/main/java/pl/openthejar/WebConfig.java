package pl.openthejar;

import pl.openthejar.dao.DatabaseProxy;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class WebConfig extends Application {

    public WebConfig() {
        DatabaseProxy.initDatabase();
    }
}
