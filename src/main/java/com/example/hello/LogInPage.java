package com.example.hello;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogInPage extends GridPane {
    TextField FirstName = new TextField();
    TextField LastName = new TextField();
    TextField CompanyName = new TextField();
    TextField email = new TextField();
    PasswordField password = new PasswordField();
    Button signIn = new Button("Sign In");
    Label emailText = new Label("Email");
    Label passwordText = new Label("Password");
    Label message = new Label("Hi! Welcome Back 😀😀");
    CheckBox remember = new CheckBox("Remember me");
    Label forgotPassword = new Label("Forgot Password");
    Label createAnAccount = new Label("Create an account");
    Label firstNameText = new Label("First Name");
    Label lastNameText = new Label("Last Name");
    Label companyNameText = new Label("Company Name");
    LogInPage(){
        setAlignment(Pos.CENTER);

        CostumeStyle();

        addRow(0,message);
        addRow(1,costumeLogIn(optionalNode()));
        getStyleClass().add("background-color");
        SignInAndUpTextEvent();

    }

    private Node optionalNode(){
        return  new HBox(remember,forgotPassword);
    }

    private GridPane costumeLogIn(Node...optionalNode){

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.addRow(0,new VBox(emailText,email));
        grid.addRow(1,new VBox(passwordText,password));
        if(optionalNode.length > 0){
            grid.addRow(2,optionalNode[0]);
            grid.addRow(3,new VBox(new Label(),signIn));
            grid.addRow(4,createAnAccount);
        }else{
            grid.addRow(2,new VBox(new Label(),signIn));
            grid.addRow(3,createAnAccount);
        }
        GridPane.setHalignment(createAnAccount,HPos.CENTER);
        return grid;
    }

    private  GridPane costumeSignUp(){
       GridPane gridPane = new GridPane();
       gridPane.setAlignment(Pos.CENTER);
       gridPane.setVgap(10);
       gridPane.addRow(0,new VBox(firstNameText,FirstName));
       gridPane.addRow(1,new VBox(lastNameText,LastName));
       gridPane.addRow(2, new VBox(companyNameText,CompanyName));
       gridPane.addRow(3,costumeLogIn());

       return  gridPane;
    }

    private void SignInAndUpTextEvent(){
        createAnAccount.setOnMouseClicked(e->{
            String currentText = createAnAccount.getText();
            if(currentText.equals("Create an account")){
                message.setText("Hey! Happy to Have you😍😍");
                createAnAccount.setText("Already have an account?");
                signIn.setText("Sign Up");
                getChildren().remove(1);
                addRow(1,costumeSignUp());
            }
            else {
                createAnAccount.setText("Create an account");
                message.setText("Hi! Welcome Back😀😀");
                getChildren().remove(1);
                signIn.setText("Sign In");
                addRow(1,costumeLogIn(optionalNode()));
            }
        });

    }

    public boolean isValidEmail() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email.getText());
        return matcher.matches();
    }
    void isEmptyOrInvalid(){
        TextField[] items = {email,password,FirstName,LastName,CompanyName};
        for(TextField item : items){
            if(item.getText().isEmpty() || !isValidEmail())
                item.setStyle("-fx-border-color: red");
        }
    }

    private void CostumeStyle(){
        TextField[] items = {email,password,FirstName,LastName,CompanyName};
        for(TextField item : items){
           item.getStyleClass().add("login-input-field");
           item.getStyleClass().add("login-input-field");
        }

        Label[] fieldLabels = {emailText,passwordText,firstNameText,lastNameText,companyNameText};
        for(Label fieldLabel : fieldLabels){
            fieldLabel.setStyle("-fx-font-size: 16px; ");
        }

       signIn.getStyleClass().add("login-button");

        remember.setStyle("-fx-text-fill: #282828; -fx-font-size: 15px; -fx-font-weight:bold;");
        forgotPassword.setStyle("-fx-text-fill: #282828; -fx-font-size: 15px; -fx-font-weight: bold;-fx-padding: 0 0 0 100px; -fx-cursor: hand");
        message.setStyle("-fx-font-size: 50px; -fx-text-fill: black;");
        createAnAccount.setStyle("-fx-text-fill: #282828; -fx-font-size: 15px; -fx-font-weight:bold; -fx-cursor: hand");
    }
}

