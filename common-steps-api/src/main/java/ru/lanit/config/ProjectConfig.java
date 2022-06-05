package ru.lanit.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:project.properties"
})
public interface ProjectConfig extends Config {

    @Key("base.url")
    String baseUrl();
}
