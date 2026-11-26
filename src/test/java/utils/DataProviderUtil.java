package utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataProviderUtil {

    public static Object[][] getJsonDataToMap(String filePath) throws IOException {
        Path dataPath = Paths.get(filePath);
        if (!dataPath.isAbsolute()) {
            dataPath = Paths.get(System.getProperty("user.dir"))
                    .resolve(dataPath)
                    .normalize();
        }

        String jsonContent;
        try {
            jsonContent = Files.readString(dataPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Could not read test data file: " + dataPath, e);
        }

        Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();
        List<HashMap<String, String>> data;
        try {
            data = new Gson().fromJson(jsonContent, type);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Invalid JSON in test data file: " + dataPath, e);
        }

        if (data == null) {
            throw new IllegalArgumentException("Test data file is empty or contains null: " + dataPath);
        }

        Object[][] table = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            table[i][0] = data.get(i);
        }
        return table;
    }


}
