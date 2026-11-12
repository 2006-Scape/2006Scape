package com.rs2.event.impl;

import com.rs2.event.Event;
import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Event dispatched when configuration is loaded from the launch-defined JSON file.
 * Allows plugins to dynamically register groups and keys to read from the JSON,
 * with default values, legacy key fallback, and type-safe callbacks.
 */
public final class ServerConfigEvent implements Event {

    private final JSONObject config;
    private final Map<String, Object> registeredValues = new LinkedHashMap<>();

    public ServerConfigEvent(JSONObject config) {
        this.config = config != null ? config : new JSONObject();
    }

    public JSONObject getRawConfig() {
        return config;
    }

    public boolean has(String key) {
        return config.has(key);
    }

    public ConfigGroup group(String name) {
        return new ConfigGroup(name, config.optJSONObject(name), config, registeredValues);
    }

    public void group(String name, Consumer<ConfigGroup> consumer) {
        ConfigGroup g = group(name);
        if (consumer != null) {
            consumer.accept(g);
        }
    }

    public void registerString(String key, String defaultValue, Consumer<String> consumer) {
        String value = getString(key, defaultValue);
        registeredValues.put(key, value);
        if (consumer != null) {
            consumer.accept(value);
        }
    }

    public void registerInt(String key, int defaultValue, Consumer<Integer> consumer) {
        int value = getInt(key, defaultValue);
        registeredValues.put(key, value);
        if (consumer != null) {
            consumer.accept(value);
        }
    }

    public void registerBoolean(String key, boolean defaultValue, Consumer<Boolean> consumer) {
        boolean value = getBoolean(key, defaultValue);
        registeredValues.put(key, value);
        if (consumer != null) {
            consumer.accept(value);
        }
    }

    public void registerDouble(String key, double defaultValue, Consumer<Double> consumer) {
        double value = getDouble(key, defaultValue);
        registeredValues.put(key, value);
        if (consumer != null) {
            consumer.accept(value);
        }
    }

    public String getString(String key, String defaultValue) {
        return config.optString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return config.optInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return config.optBoolean(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        return config.optDouble(key, defaultValue);
    }

    public Map<String, Object> getRegisteredValues() {
        return Collections.unmodifiableMap(registeredValues);
    }

    public static class ConfigGroup {
        private final String groupName;
        private final JSONObject groupObj;
        private final JSONObject rootConfig;
        private final Map<String, Object> registeredValues;

        public ConfigGroup(String groupName, JSONObject groupObj, JSONObject rootConfig, Map<String, Object> registeredValues) {
            this.groupName = groupName;
            this.groupObj = groupObj;
            this.rootConfig = rootConfig;
            this.registeredValues = registeredValues;
        }

        public String getGroupName() {
            return groupName;
        }

        public JSONObject getRawGroup() {
            return groupObj;
        }

        public boolean has(String key) {
            return (groupObj != null && groupObj.has(key)) || rootConfig.has(key);
        }

        public void registerString(String key, String defaultValue, Consumer<String> consumer) {
            registerString(key, defaultValue, null, consumer);
        }

        public void registerString(String key, String defaultValue, String legacyKey, Consumer<String> consumer) {
            String value = getString(key, defaultValue, legacyKey);
            registeredValues.put(groupName + "." + key, value);
            if (consumer != null) {
                consumer.accept(value);
            }
        }

        public void registerInt(String key, int defaultValue, Consumer<Integer> consumer) {
            registerInt(key, defaultValue, null, consumer);
        }

        public void registerInt(String key, int defaultValue, String legacyKey, Consumer<Integer> consumer) {
            int value = getInt(key, defaultValue, legacyKey);
            registeredValues.put(groupName + "." + key, value);
            if (consumer != null) {
                consumer.accept(value);
            }
        }

        public void registerBoolean(String key, boolean defaultValue, Consumer<Boolean> consumer) {
            registerBoolean(key, defaultValue, null, consumer);
        }

        public void registerBoolean(String key, boolean defaultValue, String legacyKey, Consumer<Boolean> consumer) {
            boolean value = getBoolean(key, defaultValue, legacyKey);
            registeredValues.put(groupName + "." + key, value);
            if (consumer != null) {
                consumer.accept(value);
            }
        }

        public void registerDouble(String key, double defaultValue, Consumer<Double> consumer) {
            registerDouble(key, defaultValue, null, consumer);
        }

        public void registerDouble(String key, double defaultValue, String legacyKey, Consumer<Double> consumer) {
            double value = getDouble(key, defaultValue, legacyKey);
            registeredValues.put(groupName + "." + key, value);
            if (consumer != null) {
                consumer.accept(value);
            }
        }

        public String getString(String key, String defaultValue, String legacyKey) {
            if (groupObj != null && groupObj.has(key)) {
                return groupObj.optString(key, defaultValue);
            }
            if (legacyKey != null && rootConfig.has(legacyKey)) {
                return rootConfig.optString(legacyKey, defaultValue);
            }
            if (rootConfig.has(key)) {
                return rootConfig.optString(key, defaultValue);
            }
            return defaultValue;
        }

        public int getInt(String key, int defaultValue, String legacyKey) {
            if (groupObj != null && groupObj.has(key)) {
                return groupObj.optInt(key, defaultValue);
            }
            if (legacyKey != null && rootConfig.has(legacyKey)) {
                return rootConfig.optInt(legacyKey, defaultValue);
            }
            if (rootConfig.has(key)) {
                return rootConfig.optInt(key, defaultValue);
            }
            return defaultValue;
        }

        public boolean getBoolean(String key, boolean defaultValue, String legacyKey) {
            if (groupObj != null && groupObj.has(key)) {
                return groupObj.optBoolean(key, defaultValue);
            }
            if (legacyKey != null && rootConfig.has(legacyKey)) {
                return rootConfig.optBoolean(legacyKey, defaultValue);
            }
            if (rootConfig.has(key)) {
                return rootConfig.optBoolean(key, defaultValue);
            }
            return defaultValue;
        }

        public double getDouble(String key, double defaultValue, String legacyKey) {
            if (groupObj != null && groupObj.has(key)) {
                return groupObj.optDouble(key, defaultValue);
            }
            if (legacyKey != null && rootConfig.has(legacyKey)) {
                return rootConfig.optDouble(legacyKey, defaultValue);
            }
            if (rootConfig.has(key)) {
                return rootConfig.optDouble(key, defaultValue);
            }
            return defaultValue;
        }
    }
}
