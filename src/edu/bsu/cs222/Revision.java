package edu.bsu.cs222;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Revision {

    private final SimpleStringProperty userName;
    private SimpleObjectProperty<java.io.Serializable> timeStamp;

    Revision(String userName, Date timeStamp){
        this.userName = new SimpleStringProperty(userName);
        this.timeStamp = new SimpleObjectProperty<>(timeStamp);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public Object getTimeStamp() {
        return timeStamp.get();
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp.set(timeStamp);
    }
}
