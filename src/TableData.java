import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("unused")

public class TableData {
    SimpleStringProperty Keys;
    SimpleStringProperty Action;
    SimpleStringProperty Key2;

    TableData(String Keys, String Key2, String Action) {
        this.Keys = new SimpleStringProperty(Keys);
        this.Action = new SimpleStringProperty(Action);
        this.Key2 = new SimpleStringProperty(Key2);
    }

    public String getKey2() {
        return Key2.get();
    }

    public void setKey2(String key2) {
        Key2.set(key2);
    }

    public String getKeys() {
        return Keys.get();
    }

    public void setKeys(String key) {
        Keys.set(key);
    }

    public String getAction() {
        return Action.get();
    }

    public void setAction(String action) {
        Action.set(action);
    }
}
