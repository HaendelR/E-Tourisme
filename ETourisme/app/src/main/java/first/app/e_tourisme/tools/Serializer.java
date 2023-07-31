package first.app.e_tourisme.tools;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Class that serializes and deserializes objects, more precisely to store an object in a file
 */
public abstract class Serializer {

    /**
     * Serialization of object
     *
     * @param filename
     * @param object
     * @param context
     */
    public static void serialize(String filename, Object object, Context context) {
        try {
            FileOutputStream file = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(file);
                oos.writeObject(object);
                oos.flush();
                oos.close();
            } catch (Exception e) {
                // Error of serialisation
                e.printStackTrace();
            }
        } catch (Exception e) {
            // Error if file is not found
            e.printStackTrace();
        }
    }

    /**
     * Deserialization of object
     *
     * @param filename
     * @param context
     * @return
     */
    public static Object deSerialize(String filename, Context context) {
        try {
            FileInputStream file = context.openFileInput(filename);
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(file);
                Object object = new ObjectInputStream(file);
                ois.close();
                return object;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            // Error if file is not found
            e.printStackTrace();
        }
        return null;
    }
}
