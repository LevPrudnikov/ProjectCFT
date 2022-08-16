package service.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());
    private final String path;

    public FileManager(String path) {
        this.path = path;
    }

    public List<File> buildFileList(List<String> files) {
        logger.info("Формируем список файлов доступных в директории...");
        return files.stream().map(str -> new File(this.path +str))
                .filter(file -> file.exists())
                .collect(Collectors.toList());
    }

    public void createFile(String path, List<String> content) {
        logger.info("Создаем итоговый отсортированный файл...");
        try {
            Files.write(Paths.get(path), content, StandardOpenOption.CREATE);
        } catch (IOException ex) {
            logger.error("Ошибка во время сохранения отсортированного массива в файл", ex);
        }
    }

}