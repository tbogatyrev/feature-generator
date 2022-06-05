package ru.lanit.config;

import org.aeonbits.owner.ConfigFactory;

public class ProjectConfigHelper {

    private static ProjectConfig projectConfig() {
        return ConfigFactory.create(ProjectConfig.class, System.getProperties());
    }

    public static String getBaseUrl() {
        return projectConfig().baseUrl();
    }
}
