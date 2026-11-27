package studentfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

// Saves and loads the whole student list using object serialization.
public class StudentFileManager {

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    // serialization: object -> bytes -> file
    public static void save(List<Student> students, String path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            // ArrayList is serializable, so the whole list is written in one call
            out.writeObject(new ArrayList<>(students));
        }
    }

    // deserialization: file -> bytes -> object
    @SuppressWarnings("unchecked")
    public static List<Student> load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            return (List<Student>) in.readObject();
        }
    }
}