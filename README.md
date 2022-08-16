# Тестовое задание для курсов ШИФТ (ЦФТ)

## Версия Java
Java 11 corretto-11.0.8

## Система сборки
Apache Maven 3.6.1 

## Используемые билиотеки
- log4j, версия 1.2.17: https://mvnrepository.com/artifact/log4j/log4j/1.2.17 
- commons-lang, версия 2.5: https://mvnrepository.com/artifact/commons-lang/commons-lang/2.5

## Параметры программы для запуска
Параметры программы задаются при запуске через аргументы командной строки, по порядку:
1. режим сортировки (-a или -d), необязательный, по умолчанию сортируем по возрастанию;
2. тип данных (-s или -i), обязательный;
3. имя выходного файла, обязательное;
4. остальные параметры – имена входных файлов, не менее одного.

Примеры запуска из командной строки для Windows:
- java -Dfile.encoding=UTF-8 -jar MergeSortApp-1.0-jar-with-dependencies.jar -a -i out.txt in.txt in2.txt (для целых чисел по возрастанию)
- java -Dfile.encoding=UTF-8 -jar MergeSortApp-1.0-jar-with-dependencies.jar -a -s out.txt in.txt in2.txt  (для строк по возрастанию)
- java -Dfile.encoding=UTF-8 -jar MergeSortApp-1.0-jar-with-dependencies.jar -d -s out.txt in1.txt in2.txt (для строк по убыванию)

Для запуска приложения из командной строки скопируйте файл MergeSortApp-1.0-jar-with-dependencies.jar из директории jar в свою директорию или запустите прямо в директории jar через консоль.
Необходимый jar файл выберете в соответствии с вашей операционной системой: MacOs или Windows.
Предварительно установите на свою машину jdk Java 11 corretto-11.0.8: https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html 
После запуска программы вам будет предложено ввести путь до папки с файлами. Передайте абсолютный путь с закрывающим слэшем.
Например: /Users/IvanovVasya/IdeaProjects/ProjectCFT/src/main/resources/

## Рекомендации по развитию приложения

Для дальнейшего развития приложения предлагается переписать функционал с использованием многопоточной обработки.