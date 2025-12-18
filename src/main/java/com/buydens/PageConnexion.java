package com.buydens;
import com.buydens.Library.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PageConnexion extends Application {
    @Override
    public void start(Stage primaryStage) {

        // Main container: split white/blue
        HBox mainContainer = new HBox();
        mainContainer.setPrefSize(800, 500);
        mainContainer.setStyle("-fx-background-color: #fff;");

        // Left: white card with logo + form
        VBox leftBox = new VBox(30);
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setPadding(new javafx.geometry.Insets(40, 40, 40, 60));
        leftBox.setPrefWidth(400);
        leftBox.setStyle("-fx-background-color: #fff; -fx-effect: dropshadow(gaussian, #e3e7ff, 16, 0.2, 0, 0);");

        // Logo top left (use blue logo for white bg)
        javafx.scene.image.ImageView logo = new javafx.scene.image.ImageView("file:img/plot_twist_library_white.png");
        logo.setFitHeight(60);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        

        Label lblTitle = new Label("Welcome !");
        lblTitle.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #4850FF; -fx-padding: 8 0 0 0; -fx-alignment: center; -fx-text-alignment: center;");
        lblTitle.setMaxWidth(Double.MAX_VALUE);
        lblTitle.setAlignment(javafx.geometry.Pos.CENTER);
        
        Label lblSubTitle = new Label("Please enter your username and password \nto connect or register a new account.");
        lblSubTitle.setStyle("-fx-font-size: 15px; -fx-text-fill: #4850FF; -fx-padding: 4 0 0 0;");
        lblSubTitle.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        VBox formBox = new VBox(18);
        formBox.setAlignment(Pos.CENTER_LEFT);

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setStyle("-fx-prompt-text-fill: #BDC0FF;");
        txtUsername.setStyle("-fx-background-radius: 10; -fx-background-color: #ffffff; -fx-border-color: #4850FF; -fx-border-width: 2; -fx-border-radius: 20;-fx-padding: 10 14; -fx-font-size: 15px;");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.setStyle("-fx-background-radius: 10; -fx-background-color: #ffffff; -fx-border-color: #4850FF; -fx-border-width: 2; -fx-padding: 10 14; -fx-border-radius: 20; -fx-font-size: 15px;");
        
        Button btnLogin = new Button("Login");
        btnLogin.setStyle("-fx-background-color: #4850FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 16; -fx-padding: 10 32; -fx-font-size: 16px; -fx-cursor: hand;");

        Button btnRegister = new Button("Register");
        btnRegister.setStyle("-fx-background-color: white; -fx-border-radius: 16; -fx-text-fill: #4850FF; -fx-font-weight: bold; -fx-border-color: #4850FF; -fx-border-width: 2; -fx-background-radius: 12; -fx-padding: 10 32; -fx-font-size: 16px; -fx-cursor: hand;");

        HBox buttonBox = new HBox(16, btnLogin, btnRegister);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill: #FFD600; -fx-font-size: 14px; -fx-padding: 6 0 0 0;");

        formBox.getChildren().addAll(txtUsername, txtPassword, buttonBox, lblMessage, logo);

        // leftBox.getChildren().addAll(logo, lblTitle, lblSubTitle, formBox);
        leftBox.getChildren().clear();
        leftBox.getChildren().add(logo);
        VBox.setMargin(lblTitle, new javafx.geometry.Insets(4, 0, 0, 0));
        leftBox.getChildren().add(lblTitle);
        VBox.setMargin(lblSubTitle, new javafx.geometry.Insets(0, 0, 0, 0));
        leftBox.getChildren().add(lblSubTitle);
        leftBox.getChildren().add(formBox);

        // Right: blue background with illustration
        VBox rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPrefWidth(400);
        rightBox.setStyle("-fx-background-color: #4850FF;");
        javafx.scene.image.ImageView illustration = new javafx.scene.image.ImageView("file:img/login_page_illustration.png");
        illustration.setFitWidth(320);
        illustration.setPreserveRatio(true);
        illustration.setSmooth(true);
        rightBox.getChildren().add(illustration);

        mainContainer.getChildren().addAll(leftBox, rightBox);

        // Logic for login/register
        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();

            User user = MainController.authenticateUser(username, password);
            if (user != null) {
                App app = new App();
                app.setCurrentUser(user);
                try {
                    app.start(new Stage());
                    primaryStage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                lblMessage.setText("Échec de la connexion. Veuillez réessayer.");
            }
        });
        btnRegister.setOnAction(e -> {
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                lblMessage.setText("Veuillez remplir tous les champs.");
                return;
            }

            if (!MainController.userExists(username)) {
                User newUser = MainController.createUser(username, password);
                App app = new App();
                app.setCurrentUser(newUser);
                lblMessage.setText("Inscription réussie ! Vous êtes connecté.");
            } else {
                lblMessage.setText("L'utilisateur existe déjà. Veuillez vous connecter.");
            }
        });

        Scene scene = new Scene(mainContainer, 800, 500);
        primaryStage.setTitle("Page de Connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}
