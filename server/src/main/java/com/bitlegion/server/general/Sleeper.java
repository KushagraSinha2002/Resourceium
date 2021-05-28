package com.bitlegion.server.general;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sleeper {

    @Value("${spring-dev-mode}")
    private Boolean devMode;

    public void pause(int ms) {
        if (devMode) {
            try {
                System.out.println("Sleeping for " + ms + " milliseconds");
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                System.err.format("IOException: %s%n", e);
            }
        }
    }
}
