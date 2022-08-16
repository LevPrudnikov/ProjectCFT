package service.argument;

import static dto.SortingType.ASC;
import static dto.SortingType.DESC;
import static exception.configuration.ErrorConfiguration.ARGUMENTS_ERROR;

import dto.FileDataType;
import dto.SortingType;
import exception.ParamNotExistException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

public class ArgumentBuilder {

    private static final Logger logger = Logger.getLogger(ArgumentBuilder.class.getName());

    public SortingType getSortingTypeParam(String sortingParam) {
        return sortingParam.equalsIgnoreCase("-d") ? DESC : ASC;
    }

    public FileDataType getDataTypeParam(String[] args) throws ParamNotExistException {
        Optional<String> opt = Arrays.stream(args)
                .filter(type -> type != null && FileDataType.findByParam(type) != null).findFirst();

        if (!opt.isPresent()) {
            logger.error(
                    ARGUMENTS_ERROR + "Не передан обязательный параметр тип данных (-s или -i)");
            throw new ParamNotExistException();
        }

        return FileDataType.findByParam(opt.get());
    }

    public String getOutFileParam(String[] args) throws ParamNotExistException {
        Optional<String> opt = Arrays.stream(args)
                .filter(arg -> arg != null && arg.endsWith(".txt")).findFirst();

        if (!opt.isPresent()) {
            logger.error(ARGUMENTS_ERROR
                    + "Не передан обязательный параметр имя выходного файла (outFileName.txt)");
            throw new ParamNotExistException();
        }

        return opt.get();
    }

    public List<String> getInFileParam(String[] args) throws ParamNotExistException {
        List<String> inFileArr;
        int fileCnt = (int) Arrays.stream(args).filter(arg -> arg.contains(".txt")).count();

        if (fileCnt < 2) {
            logger.error(ARGUMENTS_ERROR
                    + "Не переданы обязательные параметры \"Имя входного файла\" (inFileName1.txt, inFileName2.txt...)");
            throw new ParamNotExistException();
        }

        if (args.length == 3) {
            String inFileName = args[args.length - 1];
            inFileArr = Collections.singletonList(inFileName);
        } else {
            int arrEnd = args.length;
            int arrStart = arrEnd - fileCnt + 1;
            String[] inFileNamesParam = Arrays.copyOfRange(args, arrStart, arrEnd);
            inFileArr = Arrays.asList(inFileNamesParam);
        }
        return inFileArr;
    }

}
