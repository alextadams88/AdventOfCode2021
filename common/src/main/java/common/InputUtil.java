package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import lombok.SneakyThrows;

public class InputUtil {

    @SneakyThrows
    public static List<String> readInput(String filename){
        File file = new File(InputUtil.class.getClassLoader().getResource(filename).toURI());
        return Files.readAllLines(file.toPath());
    }

    @SneakyThrows
    public static Scanner getFileReader(String filename){
        File file = new File(InputUtil.class.getClassLoader().getResource(filename).toURI());
        return new Scanner(new FileReader(file));
    }
}
