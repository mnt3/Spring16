package lt.itakademija.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Jsons {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Jsons() {}

    public static String toJsonString(Object object) {
        if (object == null) {
            return "null";
        }

        try {
            return objectMapper.writeValueAsString(object).replace("\"", "'");
        } catch (Exception e) {
            throw new RuntimeException("Could not convert object to JSON", e);
        }
    }

}
