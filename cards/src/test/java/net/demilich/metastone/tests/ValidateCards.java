package net.demilich.metastone.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import net.demilich.metastone.game.cards.CardParser;
import net.demilich.metastone.utils.ResourceInputStream;
import net.demilich.metastone.utils.UserHomeMetastone;

/**
 * This test will iterate through all the cards in the cards resources dir
 * and invoke the CardParser.parseCard(cardFile) method to ensure that
 * each card is well formed and can be parsed.
 */
public class ValidateCards {

    private static final String CARDS_DIR = "src/main/resources/cards/"; // relative path from module root
    private static final CardParser CARD_PARSER = new CardParser();
    private static final List<File> ALL_CARD_FILES;

    static {

        // recursively crawl the cards dir and pull out all the files
        ALL_CARD_FILES = (List<File>)FileUtils.listFiles(
                new File(CARDS_DIR),
                new RegexFileFilter("^(.*json)"),
                DirectoryFileFilter.DIRECTORY);
        // also pull in the user's custom cards dir
        if (new File(UserHomeMetastone.getPath() + File.separator + "cards").exists()) {
            ALL_CARD_FILES.addAll(
                    FileUtils.listFiles(
                        new File(UserHomeMetastone.getPath() + File.separator + "cards"),
                        new RegexFileFilter("^(.*json)"),
                        DirectoryFileFilter.DIRECTORY)
            );
        }
    }

    @DataProvider(name = "CardProvider")
    public static Object[][] getCardFiles() {

        int size =  ALL_CARD_FILES.size();
        File file;
        Object [][] matrix = (Object[][]) Array.newInstance(Object.class, size, 1);
        for (int i = 0; i < size; i++) {
            file = ALL_CARD_FILES.get(i);
            matrix[i][0] = file;
        };

        return matrix;
    }

    @Test(dataProvider = "CardProvider")
    public void validateCard(File cardFile) throws FileNotFoundException {
        try {
            CARD_PARSER.parseCard(new ResourceInputStream(cardFile.getName(), new FileInputStream(cardFile), true));
        } catch (Exception ex) {
            System.err.println(ex);
            Assert.fail(cardFile.getName(), ex);
        }
    }
}
