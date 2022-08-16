import static exception.configuration.ErrorConfiguration.ARGUMENTS_ERROR;

import dto.FileDataType;
import dto.SortingType;
import exception.ParamNotExistException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import service.argument.ArgumentBuilder;
import service.manager.FileManager;
import service.parser.FileParser;
import service.sorting.MergeSorter;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static SortingType sortingType;
    private static FileDataType fileDataType;
    private static String outFileName;
    private static List<String> inFileNames;


    public static void main(String[] args) {

        logger.info("Начало работы программы...");
        if (args.length < 3) {
            logger.error(ARGUMENTS_ERROR + " Количество параметров меньше 3!");
            return;
        }

        logger.info("Формируем список параметров для дальнейшей работы.");
        try {
            parseArguments(args);
        } catch (ParamNotExistException ex) {
            logger.info("Завершение работы программы...");
            return;
        }

        logger.info("Получаем клиентский путь до файлов.");
        String path = getFilesPath();

        FileManager fileManager = new FileManager(path);
        FileParser parser = new FileParser();
        MergeSorter sort = new MergeSorter();

        List<File> files = fileManager.buildFileList(inFileNames);

        if (files.isEmpty()) {
            logger.error("Нет файлов для загрузки");
            return;
        }

        List<String> parsedStrings = new ArrayList<>();
        for (File file : files) {
            parsedStrings.addAll(parser.parseFile(file));
        }

        if (parsedStrings.isEmpty()) {
            logger.error(
                    "Нет данных для дальнейшей сортировки и записи в итоговый файл " + outFileName);
            return;
        }

        List<String> sortedList = sort.mergeSort(parsedStrings, fileDataType, sortingType);

        fileManager.createFile(outFileName, sortedList);

        logger.info("Количество ошибочных данных, которые не попали в итоговый файл: "
                + sort.getErrCnt());
        logger.info("Завершение работы программы...");
    }


    private static void parseArguments(String[] args) throws ParamNotExistException {
        ArgumentBuilder argumentBuilder = new ArgumentBuilder();
        sortingType = argumentBuilder.getSortingTypeParam(args[0]);
        fileDataType = argumentBuilder.getDataTypeParam(args);
        outFileName = argumentBuilder.getOutFileParam(args);
        inFileNames = argumentBuilder.getInFileParam(args);
    }

    private static String getFilesPath() {
        String path = null;
        logger.info("Введите путь до папки с файлами...");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (path == null || path.isEmpty()) {
                path = reader.readLine();
                if (path.isEmpty()) {
                    logger.error("Передана пустая строка! Укажите путь до папки!");
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка ввода пути до файлов");
            path = StringUtils.EMPTY;
        }
        return path;
    }

}
