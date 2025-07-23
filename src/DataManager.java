import java.io.*;

public class DataManager {

    private static final String FILENAME = "app_data.ser"; // The file to save to

    public static void saveData(ApplicationData data) {
        try (FileOutputStream fileOut = new FileOutputStream(FILENAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(data);
            System.out.println("Data saved successfully to " + FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ApplicationData loadData() {
        ApplicationData data = null;
        try (FileInputStream fileIn = new FileInputStream(FILENAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            data = (ApplicationData) in.readObject();
            System.out.println("Data loaded successfully from " + FILENAME);
        } catch (FileNotFoundException e) {
            System.out.println("No save file found. Starting with new data.");
            return new ApplicationData(); // Return a fresh instance
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data != null ? data : new ApplicationData();
    }
}