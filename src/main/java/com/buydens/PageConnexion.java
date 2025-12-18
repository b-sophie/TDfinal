package com.buydens;
import com.buydens.Library.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PageConnexion extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new javafx.geometry.Insets(20));

        Label lblTitle = new Label("Connexion à la Bibliothèque");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Nom d'utilisateur");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Mot de passe");

        Button btnLogin = new Button("Se connecter");
        Button btnRegister = new Button("S'inscrire");
        HBox buttonBox = new HBox(10, btnLogin, btnRegister); 
buttonBox.setAlignment(Pos.CENTER);

        Label lblMessage = new Label();

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


        root.getChildren().addAll(lblTitle, txtUsername, txtPassword, buttonBox, lblMessage);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Page de Connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}
