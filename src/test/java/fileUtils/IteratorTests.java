package fileUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class IteratorTests {
    private final static String emptyFilePath = "/emptyFile.txt";
    private final static String correctPath = "/someFile.txt";
    private final static String nonExistingPath = "/awesfgdfwerwef.txt";

    @Test
    public void shouldThrowNoSuchElementExceptionIfReachedEOF() {
        assertThrows(
                NoSuchElementException.class,
                () -> {
                    new FileIterator(Objects.requireNonNull(getClass().getResource(emptyFilePath)).getPath()).next();
                }
        );
    }

    @Test
    public void shouldThrowFileNotFoundExceptionOnNonExistingFile() {
        assertThrows(
                FileNotFoundException.class,
                () -> {
                    new FileIterator(nonExistingPath);
                }
        );
    }

    @Test
    public void shouldThrowNullPointerExceptionOnNonExistingResourceFile() {
        assertThrows(NullPointerException.class,
                () -> {
                    new FileIterator(getClass().getResource(nonExistingPath).getPath());
                }
        );
    }

    @Test
    public void shouldReadCorrectLineFromExistingFile() throws IOException {
        String path = Objects.requireNonNull(getClass().getResource(correctPath)).getPath();
        String actualLineRead = new FileIterator(path).next();

        String correctFirstFileLine = "Hi, it`s first line.";
        assertEquals(actualLineRead, correctFirstFileLine);
        System.out.println(actualLineRead);
    }

    @Test
    public void shouldThrowExceptionWhenReachedEndOfNotEmptyFile() throws IOException {
        String path = Objects.requireNonNull(getClass().getResource(correctPath)).getPath();
        FileIterator fi = new FileIterator(path);

        String lastLine = "";

        while (fi.hasNext()) {
            lastLine = fi.next();
        }
        assertThrows(NoSuchElementException.class, fi::next);
    }

    @Test
    public void shouldGetCorrectLastLineOfCorrectFile() throws IOException {
        String path = Objects.requireNonNull(getClass().getResource(correctPath)).getPath();
        FileIterator fi = new FileIterator(path);
        String lastLine = "";
        while (fi.hasNext()) {
            lastLine = fi.next();
        }
        String correctLastFileLine = "and this is the fourth.";
        assertEquals(lastLine, correctLastFileLine);
    }

    @Test
    public void shouldNotSkipTheEmptyLineInFile() throws IOException {
        String path = Objects.requireNonNull(getClass().getResource(correctPath)).getPath();
        FileIterator fi = new FileIterator(path);
        fi.next();
        fi.next();
        assertEquals(fi.next(), "");
    }

    @Test
    public void shouldFindEmptyFileWithCorrectPath() throws FileNotFoundException {
        String path =Objects.requireNonNull(getClass().getResource(emptyFilePath)).getPath();

        assertDoesNotThrow(() -> {new FileIterator(path);});
    }
}
