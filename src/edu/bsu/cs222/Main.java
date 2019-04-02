package edu.bsu.cs222;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainWindow();
    }

    private void showMainWindow(){

        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainMenuView.fxml"));
            AnchorPane pane = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setMain(this);

            Scene scene = new Scene(pane);

            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    void close(){
        primaryStage.close();
    }
}
