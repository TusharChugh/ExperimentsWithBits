package edu.cmu.cs.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Helper function to read words from a text file on disk
 * All words in file should be in the next line
 */
public final class DictionaryHelper {
    private static Set<String> words = new HashSet<>();

    /**
     * Private constructor
     */
    private DictionaryHelper(){
    }

    /**
     * Reads the file from the given path
     * @param filePath relative/absolute filePath on disk
     * @return Set of all the words in the file
     * @throws FileNotFoundException if file is not found on disk
     */
    public static Set<String> listOfWords(String filePath) throws FileNotFoundException {
        String path = Paths.get(".").toAbsolutePath().normalize().toString();
        try (Scanner scanner = new Scanner(new File(path + "/" + filePath))) {
            while (scanner.hasNext()) {
                words.add(scanner.nextLine());
            }
            return words;
        }
    }
}
