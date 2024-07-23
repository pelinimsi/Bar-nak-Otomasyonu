package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class LoginPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView app_icon;

    @FXML
    private ImageView app_icon11;

    @FXML
    private Button change_button1;

    @FXML
    private TextField password_entry;

    @FXML
    private TextField username_entry;

    @FXML
    private TextField username_entry0;

    @FXML
    private TextField username_entry01;

    @FXML
    private TextField password_entry1;

    @FXML
    private TextField password_entry0;

    @FXML
    private TextField password_entry01;

    @FXML
    private TextField password_entry02;

    @FXML
    private TextField password_entry021;
    
    @FXML
    private ImageView app_icon1;

    @FXML
    private Button register_button;
    
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void change_click(ActionEvent event) {
        String username = username_entry01.getText().toLowerCase();
        String password = password_entry01.getText();
    	if(MySQL.checkUser(username)) {
    		if(password_entry02.getText().equals(password_entry021.getText())) {
    	        User t = MySQL.getUser(username, password);
    	        if(t == null)
    	        	change_button1.setText("Eski parola yanlýþ!");
    	        else {
    	        	String query = "UPDATE USERS SET USERPASSWORD = '"+password_entry02.getText()+"' WHERE USERNAME = '"+username+"'";
    	    		change_button1.setText("Þifre Deðiþtirildi!");
    	        	MySQL.commit(query);
    	        }
    		}
    		else {
    			change_button1.setText("Þifreler uyuþmuyor!");
    		}
    	}
    	else {
    		change_button1.setText("Kullanýcý mevcut deðil!");
    	}
    }

    @FXML
    void register_click(ActionEvent event) {
    	if(MySQL.checkUser(username_entry0.getText())) {
    		register_button.setText("Bu kullanýcý adý alýnmýþ!");
    	}
    	else {
    		if(password_entry0.getText().equals(password_entry1.getText())) {
    			String QUERY = "INSERT INTO users (USERNAME,USERPASSWORD,IS_ADMIN) VALUES ('"+username_entry0.getText()+"','"+password_entry0.getText()+"',0)";
    			MySQL.commit(QUERY);
    			
    		}
    	}
    }
    
    @FXML
    void login_click(ActionEvent event) throws IOException {
        String username = username_entry.getText().toLowerCase();
        String password = password_entry.getText();
        User t = MySQL.getUser(username, password);
        if(t == null)
        	System.out.print("None");
        else if (t.is_admin) {
            showMainPage();
        } 
        else
        {
            showStandartPage();
        }
    }

    private void showStandartPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StandartPage.fxml"));
        Parent root = loader.load();
        Stage standartStage = new Stage();
        standartStage.setScene(new Scene(root));
        standartStage.setTitle("StandartPage");
        standartStage.show();
        stage.close();
    }

    private void showMainPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = loader.load();
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("MainPage");
        mainStage.show();
        stage.close();
    }

    @FXML
    void initialize() {
        assert app_icon != null : "fx:id=\"app_icon\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert app_icon1 != null : "fx:id=\"app_icon1\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert app_icon11 != null : "fx:id=\"app_icon11\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert password_entry != null : "fx:id=\"password_entry\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert password_entry0 != null : "fx:id=\"password_entry0\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert password_entry1 != null : "fx:id=\"password_entry1\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert username_entry != null : "fx:id=\"username_entry\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert username_entry0 != null : "fx:id=\"username_entry0\" was not injected: check your FXML file 'LoginPage.fxml'.";
        Animal icon = new Dog(-1,"","","https://icons.iconarchive.com/icons/iconarchive/cute-animal/512/Cute-Dog-icon.png",0,true);
        app_icon.setImage(icon.get_image());
        app_icon1.setImage(icon.get_image());
        app_icon11.setImage(icon.get_image());
    }

}
