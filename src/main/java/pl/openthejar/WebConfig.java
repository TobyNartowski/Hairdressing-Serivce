package pl.openthejar;

import pl.openthejar.dao.DatabaseProxy;
import pl.openthejar.misc.DatabaseService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Klasa zarzadzajaca konfiguracja webowa
 */
@ApplicationPath("/api")
public class WebConfig extends Application {

    /**
     * Glowna konfiguracja serwera
     */
    public WebConfig() {
        DatabaseProxy.initDatabase();
        new DatabaseService();
    }
}
