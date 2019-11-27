// Java program to illustrate reading data from file
// using nio.File

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

class ReadFile {
    static List<Integer> readFileByName(String fileName) {

        List<String> lines;
        List<Integer> result = new ArrayList<>(Collections.emptyList());
        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

            for (String s : lines) {
                if (!s.isBlank())
                    result.add(Integer.parseInt(s));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
