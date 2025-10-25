package infra;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/** Utilitário central de leitura/escrita JSON */
public class JsonDb {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static synchronized <T> List<T> readList(String path, Class<T> clazz) {
        try {
            Path p = Paths.get(path);
            if (!Files.exists(p)) {
                Files.createDirectories(p.getParent());
                Files.writeString(p, "[]", StandardCharsets.UTF_8);
            }
            String json = Files.readString(p, StandardCharsets.UTF_8);
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            List<T> list = gson.fromJson(json, listType);
            return (list == null) ? new ArrayList<>() : list;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler " + path, e);
        }
    }

    public static synchronized <T> void writeList(String path, List<T> data) {
        try {
            Path p = Paths.get(path);
            if (!Files.exists(p.getParent())) {
                Files.createDirectories(p.getParent());
            }
            String json = gson.toJson(data);
            Files.writeString(p, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao gravar " + path, e);
        }
    }
}
