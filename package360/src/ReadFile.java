// Java program to illustrate reading data from file
// using nio.File

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

class ReadFile {
    static List<Integer> readFileByName(String fileName) {

        List<String> lines;
        String[] elements = null;
        List<Integer> result = new ArrayList<>(Collections.emptyList());
        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

            for (String s : lines) {
                if (!s.isBlank())
                    if (!s.contains(",")) {
                        result.add(Integer.parseInt(s));
                    } else {
                        elements = s.split(",");
                        for (String i : elements) {
                            if (!i.isBlank())
                                result.add(Integer.parseInt(i));
                        }
                        elements = null;
                    }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid data detected in input file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
