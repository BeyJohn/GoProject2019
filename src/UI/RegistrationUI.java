package UI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import entity.Player;
import utils.DatabaseConnection;

public class RegistrationUI
{
	
	public void starting(Stage stage, MainUI mUI)
	{
		
		TextField NameInput = new TextField();
		TextField PassWordInput = new TextField();
		Label NameLabel = new Label("UserName:");
		Label PassWordLabel = new Label("Password:");
		Label ConfirmPassWord = new Label("Confirm Password: ");
		TextField ConfirmPassWordInput = new TextField();
		Button RegisterButton = new Button("Register");
		
		GridPane LoginPane = new GridPane();
		LoginPane.setAlignment(Pos.CENTER);
		
		LoginPane.add(NameLabel, 1, 1); // setRowIndex(NameLabel,1);
		LoginPane.add(NameInput, 2, 1);
		LoginPane.add(PassWordLabel, 1, 2);
		LoginPane.add(PassWordInput, 2, 2);
		LoginPane.add(ConfirmPassWord, 1, 3);
		LoginPane.add(ConfirmPassWordInput, 2, 3);
		LoginPane.add(RegisterButton, 4, 4);
		Scene scene = new Scene(LoginPane, 400, 300);
		stage.setScene(scene);
		RegisterButton.setOnAction(e ->
		{
			Player newUser = new Player();
			String name = NameInput.getText();
			String password = PassWordInput.getText();
			DatabaseConnection db = new DatabaseConnection();
			boolean checkpoint = true;
			
			List<Player> players = db.getPlayers();
			for (Player player : players)// check list for input name
			{
				String listName = player.playerName;
				if (listName.equals(name))// check to see if username is already been created
				{
					checkpoint = false;
					Platform.runLater(() ->
					{
						Alert scoreAlert = new Alert(Alert.AlertType.INFORMATION, "This Username is already being used",
								ButtonType.CANCEL);
						scoreAlert.showAndWait();
					});
					break;
				}
			}
			if (checkpoint)
			{
				System.out.println("here");
				newUser.setUserName(name);
				newUser.setPassword(password);
				DatabaseConnection connection = new DatabaseConnection();
				connection.insertPlayer(newUser);
				mUI.mainScreen(stage);
			}
			
		});
		
	}
	
}
