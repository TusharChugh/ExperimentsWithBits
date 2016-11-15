package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class DictionaryTest {
    //CHECKSTYLE:OFF
    private static final String dictionaryPath1 = "src/main/resources/words.txt";
    private static final String dictionaryPath2 = "wrong path";
    private static final int DICTIONARY_SIZE = 64001;

    @Test
    public void dictionaryTest() {
        try {
            Set<String> dictionary = DictionaryHelper.listOfWords(dictionaryPath1);
            Assert.assertEquals(dictionary.size(), DICTIONARY_SIZE);
        }
        catch (IOException e) {
            //Test
        }
    }

    @Test (expected = FileNotFoundException.class)
    public void wrongFilePath() throws  IOException {
            Set<String> dictionary = DictionaryHelper.listOfWords(dictionaryPath2);
    }
}
