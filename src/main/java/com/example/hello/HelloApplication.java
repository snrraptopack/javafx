package com.example.hello;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class HelloApplication extends Application {
    GetDatabase getDatabase = new GetDatabase();

    @Override
    public void start(Stage stage) {
        LogInPage logInPage = new LogInPage();
        MainPage mainPage = new MainPage();
        handleLogIn(logInPage,stage,mainPage);

        Scene scene = new Scene(logInPage);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        handleLogIn(logInPage,stage,mainPage);
    }
    void handleLogIn(LogInPage logInPage, Stage stage,MainPage mainPage){
            EventHandler<ActionEvent> buttonEvent = event ->{
                if(!getDatabase.isValid(logInPage.email.getText(),logInPage.password.getText())){
                    System.out.println("no input found");
                    logInPage.isEmptyOrInvalid();
                }else{
                    Scene currentScene = stage.getScene();
                    Scene mainScene = new Scene(mainPage,currentScene.getWidth(), currentScene.getHeight());
                    stage.setScene(mainScene);
                }
            };
            logInPage.signIn.setOnAction(buttonEvent);
    }
    public static void main(String[] args) {
        launch(args);
    }
}


