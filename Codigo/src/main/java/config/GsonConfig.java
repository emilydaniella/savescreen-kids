package config;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonConfig
        implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext ctx) {
        return (src == null)
            ? JsonNull.INSTANCE
            : new JsonPrimitive(src.format(FMT));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext ctx)
            throws JsonParseException {
        if (json == null || json.isJsonNull()) return null;
        return LocalDateTime.parse(json.getAsString(), FMT);
    }
}
