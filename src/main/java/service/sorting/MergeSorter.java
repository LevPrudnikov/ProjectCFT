package service.sorting;

import dto.FileDataType;
import dto.SortingType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static exception.configuration.ErrorConfiguration.PARSING_ERROR;
import static exception.configuration.ErrorConfiguration.RECORDING_ERROR;

public class MergeSorter {

    private static final Logger logger = Logger.getLogger(MergeSorter.class.getName());

    private int errCnt = 0; //запоминает кол-во ошибочных строк с пробелами

    public int getErrCnt() {
        return errCnt;
    }

    public List<String> mergeSort(List<String> strings, FileDataType fileType,
                                  SortingType sorting) {
        List<String> result;
        if (fileType.equals(FileDataType.INTEGER)) {
            result = intSort(strings, sorting);
        } else {
            result = stringSort(strings, sorting);
        }
        return result;
    }

    public List<String> intSort(List<String> strings, SortingType flag) {   //сортировка чисел
        List<String> listStr = new ArrayList<>();
        List<Integer> arrTmp = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String str = strings.get(i);
            try {
                arrTmp.add(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                if (str.contains(" ") || str.isBlank()) {
                    logger.error(RECORDING_ERROR + "Потеря ошибочной строки " + "[" + i + "]: "
                            + str + " - имеет пробел");
                    errCnt++;
                } else {
                    logger.error(PARSING_ERROR + "Строка " + "[" + i + "]: " + str
                            + " - не является числом");
                    listStr.add(str);
                }
            }
        }
        int[] array = new int[strings.size() - listStr.size() - errCnt];
        for (int i = 0; i < array.length;
             i++) {
            array[i] = arrTmp.get(i);
        }

        List<String> list = new ArrayList<>();

        int[] arrResult = intSortArray(array);

        for (int j : arrResult) {
            list.add(String.valueOf(j));
        }

        if (flag.equals(SortingType.ASC)) {
            list.addAll(Arrays.asList(strSortArray(listStr.toArray(new String[0]))));
        } else {
            Collections.reverse(list);
            list.addAll(Arrays.asList(strReverse(strSortArray(listStr.toArray(new String[0])))));
        }

        return list;
    }

    public List<String> stringSort(List<String> strings, SortingType flag) {    //сортировка строк
        List<String> listTemp = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            String str = strings.get(i);

            if (str.contains(" ") || str.isBlank()) {
                logger.error(
                        RECORDING_ERROR + "Потеря ошибочной строки " + "[" + i + "]: " + str
                                + " - имеет пробел");
                errCnt++;
            } else {
                listTemp.add(str);
            }
        }

        String[] array = listTemp.toArray(new String[0]);

        return flag.equals(SortingType.ASC) ? Arrays.asList(strSortArray(array))
                : Arrays.asList(strReverse(strSortArray(array)));
    }

    //функции для чисел

    public int[] intSortArray(int[] array) { //разделение массива на подмассивы рекурсией
        if (array == null) {
            return null;
        }

        if (array.length < 2) {
            return array;
        }

        int[] arrayL = Arrays.copyOfRange(array, 0, array.length / 2);
        int[] arrayR = Arrays.copyOfRange(array, array.length / 2, array.length);

        arrayL = intSortArray(arrayL);
        arrayR = intSortArray(arrayR);

        return intMergeArray(arrayL, arrayR);
    }

    public int[] intMergeArray(int[] arrayL, int[] arrayR) { //слияние
        int[] arrayC = new int[arrayL.length + arrayR.length];
        int posL = 0, posR = 0;
        for (int i = 0; i < arrayC.length; i++) {
            if (posL < arrayL.length && posR < arrayR.length) {
                if (arrayL[posL] < arrayR[posR]) {
                    arrayC[i] = arrayL[posL];
                    posL++;
                } else {
                    arrayC[i] = arrayR[posR];
                    posR++;
                }
            } else if (posL == arrayL.length && posR < arrayR.length) {
                arrayC[i] = arrayR[posR];
                posR++;
            } else {
                arrayC[i] = arrayL[posL];
                posL++;
            }
        }
        return arrayC;
    }

    //функции для строкового массива

    public boolean compStr(String str1, String str2) { //сравнивает буквы в словах
        int flag = 0;
        boolean res = true;
        String s1 = str1.toLowerCase();
        String s2 = str2.toLowerCase();
        int n = Math.min(s1.length(), s2.length());
        for (int i = 0; i < n && flag == 0; i++) {
            if (s1.codePointAt(i) < s2.codePointAt(i)) {
                res = true;
                flag++;
            } else if (s1.codePointAt(i) > s2.codePointAt(i)) {
                res = false;
                flag++;
            }
        }
        if (flag == 0 && s1.length() == n) {
            res = true;
        } else if (flag == 0) {
            res = false;
        }
        return res;
    }

    public String[] strSortArray(String[] array) { //разделение строкового массива для сортировки
        if (array == null) {
            return null;
        }

        if (array.length < 2) {
            return array;
        }

        String[] arrayL = Arrays.copyOfRange(array, 0, array.length/2);
        String[] arrayR = Arrays.copyOfRange(array, array.length/2, array.length);

        arrayL = strSortArray(arrayL);
        arrayR = strSortArray(arrayR);

        return strMergeArray(arrayL, arrayR);
    }

    public String[] strMergeArray(String[] arrayL, String[] arrayR) { //слияние строкового массива
        String[] arrayC = new String[arrayL.length + arrayR.length];
        int posL = 0, posR = 0;
        for (int i = 0; i < arrayC.length; i++) {
            if (posL < arrayL.length && posR < arrayR.length) {
                if (compStr(arrayL[posL], arrayR[posR])) {
                    arrayC[i] = arrayL[posL];
                    posL++;
                } else {
                    arrayC[i] = arrayR[posR];
                    posR++;
                }
            } else if (posL == arrayL.length && posR < arrayR.length) {
                arrayC[i] = arrayR[posR];
                posR++;
            } else {
                arrayC[i] = arrayL[posL];
                posL++;
            }
        }
        return arrayC;
    }

    public String[] strReverse(String[] array) { //реверс строкового массива
        int n = array.length;
        for (int i = 0; i < n / 2; i++) {
            String tmp = array[n - i - 1];
            array[n - i - 1] = array[i];
            array[i] = tmp;
        }
        return array;
    }
}
