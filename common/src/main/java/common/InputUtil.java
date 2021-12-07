package common;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import lombok.SneakyThrows;

public class InputUtil {

    @SneakyThrows
    public static List<String> readInput(String filename){
        File file = new File(InputUtil.class.getClassLoader().getResource(filename).toURI());
        return Files.readAllLines(file.toPath());
    }
}
