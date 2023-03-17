public class SharedStorage {

    private static SharedStorage instance = null;

    private SharedStorage() {

        // private constructor to prevent instantiation from outside the class
    }

    public static SharedStorage getInstance() {
        if (instance == null) {
            instance = new SharedStorage();
        }
        return instance;
    }

    // other methods and properties
}
