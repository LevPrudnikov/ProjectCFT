package service.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class FileParser {

    private static final Logger logger = Logger.getLogger(FileParser.class.getName());

    public List<String> parseFile(File file) {
        List<String> strings = new ArrayList<>();

        if (file != null && file.exists()) {
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    String str = sc.nextLine();
                    logger.info(str);
                    strings.add(str);
                }
            } catch (Exception ex) {
                logger.error("Возникла ошибка при парсинге файла", ex);
            }
        } else {
            logger.error("Файла " + file.getName() + " не существует!");
        }

        return strings;
    }
}