package com.company;

import java.io.*;
import java.util.Scanner;

public class TextFinder {
    public static void main(String[] args) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        StringBuilder builder = new StringBuilder();
        try {
            is = new FileInputStream("src/txt/text1.txt");
            os = new FileOutputStream("src/txt/text3.txt");
            for (int read = is.read(); read != -1; read = is.read()) {
                os.write(read);
                builder.append((char) read);
            }
            is = new FileInputStream("src/txt/text2.txt");
            for (int read = is.read(); read != -1; read = is.read()) {
                os.write(read);
                builder.append((char) read);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String textForSearch = getTextForSearch();
            if (isTextInFile(textForSearch, builder)) {
                System.out.println("Текст [%s] найден. " + textForSearch);
            } else {
                System.out.println("Текст [%s] не найден. " + textForSearch);
            }
        }
    }

    private static String getTextForSearch(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Text for search: ");
            if (scanner.hasNextLine()){
                String result = scanner.nextLine();
                if (!result.isEmpty()){
                    scanner.close();
                    return result;
                }
                else {
                    System.out.println("[Ошибка] Текстовое поле пустое.");
                    scanner = new Scanner(System.in);
                }
            }
        }
    }

    private static boolean isTextInFile(String searchedText, StringBuilder builder){
        if (searchedText == null || searchedText.isEmpty()){
            return false;
        }
        char[] searchedSymbols = searchedText.toCharArray();
        try {
            for (int i = 0; i < builder.length() - searchedSymbols.length + 1; i++) {
                if (builder.charAt(i) == searchedSymbols[0]) {
                    for (int symbolwiseMatchCounter = 0; symbolwiseMatchCounter < searchedSymbols.length;) {
                        symbolwiseMatchCounter++;
                        if (symbolwiseMatchCounter == searchedSymbols.length) {
                            return true;
                        }
                        if (builder.charAt(i + symbolwiseMatchCounter) != searchedSymbols[symbolwiseMatchCounter]) {
                            break;
                        }
                    }
                }
            }
            return false;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}