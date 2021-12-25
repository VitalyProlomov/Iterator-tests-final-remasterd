package fileUtils;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FileIterator implements Iterator<String>, AutoCloseable {
    private BufferedReader bufferedReader = null;

    public FileIterator(String path) throws FileNotFoundException {
        FileReader fr = new FileReader(path);
        bufferedReader = new BufferedReader(fr);
    }

    public String next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("End of File reached.");
            }

            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
    }

    public boolean hasNext() {
        try {
            return bufferedReader.ready();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }
}
