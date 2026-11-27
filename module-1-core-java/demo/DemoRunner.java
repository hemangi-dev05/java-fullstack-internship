import java.io.*;
import java.nio.file.*;
import java.util.List;

public class DemoRunner {


    static class TypingInput extends InputStream {
        private final List<String> lines;
        private int index = 0;
        private byte[] current = new byte[0];
        private int pos = 0;

        TypingInput(List<String> lines) { this.lines = lines; }

        @Override
        public int read() throws IOException {
            if (pos >= current.length) {
                if (index >= lines.size()) return -1;
                String line = lines.get(index++);
                try { Thread.sleep(600); } catch (InterruptedException e) { }
                System.out.println(line);
                current = (line + "\n").getBytes();
                pos = 0;
            }
            return current[pos++];
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int c = read();
            if (c == -1) return -1;
            b[off] = (byte) c;
            return 1;
        }

        @Override
        public int available() { return 0; }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(args[0]));
        System.setIn(new TypingInput(lines));
        Class.forName(args[1])
             .getMethod("main", String[].class)
             .invoke(null, (Object) new String[0]);
    }
}