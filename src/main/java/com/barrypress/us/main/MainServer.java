package com.barrypress.us.main;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import java.util.Arrays;
import java.util.HashSet;

public class MainServer extends Application {

    public static void main(String[] args) throws Exception {

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8111);

        CorsService corsService = new CorsService();
        corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
        corsService.setAllowedCredentials(true);

        Application application = new MainServer();
        application.getServices().add(corsService);
        component.getDefaultHost().attachDefault(application);
        component.start();
    }

    @Override
    public Restlet createInboundRoot() {

        Router router = new Router(getContext());

        router.attach("/game/{action}", GameResource.class);
        router.attach("/game/{action}/{id}/{type}", GameResource.class);

        return router;
    }
}
