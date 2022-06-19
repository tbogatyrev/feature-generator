package ru.lanit.steps.types;

import java.util.HashMap;
import java.util.Map;

public class SmartMapContainer {

    private Map<String, String> smartMap = new HashMap<>();

    public Map<String, String> getSmartMap() {
        return smartMap;
    }

    public void setSmartMap(Map<String, String> smartMap) {
        this.smartMap = smartMap;
    }
}
