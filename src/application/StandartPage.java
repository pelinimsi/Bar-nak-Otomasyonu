package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class StandartPage {
	
	ArrayList<Animal> animals = new ArrayList<Animal>();
	String src = "";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView app_icon;
    
    @FXML
    private RadioButton Cat_selector;

    @FXML
    private RadioButton Dog_selector;

    @FXML
    private RadioButton Pill_selector;

    @FXML
    private RadioButton Vaccine_selector;
    
    @FXML
    private Label age_entry;

    @FXML
    private Label age_label;

    @FXML
    private Label gender_label;
    
    @FXML
    private ImageView image_entry;

    @FXML
    private Label kind_entry;

    @FXML
    private ListView<String> animal_list;
    
    @FXML
    private ListView<String> medicine_list;

    @FXML
    private Label name_entry;

    @FXML
    private Label username_label;
    
    @FXML
    private TextField search_entry;

    String getIdFromList() {
    	String selectedText = animal_list.getSelectionModel().getSelectedItem();
        if(selectedText != null) {
            int index = selectedText.indexOf(":");
            if (index != -1) {
                String idString = selectedText.substring(0, index);
                return idString;
            } else {
                System.err.println("':' karakteri bulunamadý");
            }
        } else {
            System.err.println("Seçili öðe yok");
        }
        return null;
    }
    
    @FXML
    void animal_list_clicked(MouseEvent event) {
    	int id = Integer.parseInt(getIdFromList().trim());
        Animal anim = MySQL.getAnimalById(id);
        set_UI(anim);
    	String query = "SELECT * FROM REQUESTS WHERE IS_APPROVED = TRUE AND ANIMAL_ID = "+id;
		ArrayList<request> r = MySQL.get_requests(query);
		if(r.size() != 0) {
			if(r.get(0).user_id == User.current.id)
				get_animal_button.setText("SAHÝPLENDÝNÝZ!");
			else
				get_animal_button.setText("SAHÝBÝ VAR");
		}
		else {
		    get_animal_button.setText("SAHÝPLEN");
		}
    }
    
    @FXML
    void Cat_selected(ActionEvent event) {
    	if(Dog_selector.isSelected())
    		Dog_selector.setSelected(false);
    	else
    		Cat_selector.setSelected(true);
        animals = MySQL.get_animals("SELECT * FROM ANIMALS WHERE IS_DOG = FALSE");
        src = "";
        search_entry.setText("");
        set_list();
    }

    @FXML
    void Dog_selected(ActionEvent event) {
    	if(Cat_selector.isSelected())
    		Cat_selector.setSelected(false);
    	else
    		Dog_selector.setSelected(true);
    	Cat_selector.setSelected(false);
        animals = MySQL.get_animals("SELECT * FROM ANIMALS WHERE IS_DOG = TRUE");
        src = "";
        search_entry.setText("");
        set_list();
    }
    
    @FXML
    void search_changed(ActionEvent event) {
    	src = search_entry.getText();
    	set_list();
    }
    
    void set_list() {
    	if(Dog_selector.isSelected())
    		animals = MySQL.get_animals("SELECT * FROM ANIMALS WHERE IS_DOG = TRUE AND NAME LIKE '%"+src+"%'");
    	else
    		animals = MySQL.get_animals("SELECT * FROM ANIMALS WHERE IS_DOG = FALSE AND NAME LIKE '%"+src+"%'");
        ArrayList<String> tmp = new ArrayList<String>();
        for (Animal i : animals) {
            String txt = Integer.toString(i.get_id())+" : "+i.name+"  Tür : "+i.kind+"  yaþ : "+i.age;
            tmp.add(txt);
        }
        ObservableList<String> observabletmp = FXCollections.observableArrayList(tmp);
        animal_list.setItems(observabletmp);
    }
    
    Animal current;
    void set_UI(Animal temp) {
    	if (temp != null) {
    		current = temp;
	        name_entry.setText(temp.name);
	        kind_entry.setText(temp.kind);
	        age_entry.setText(Integer.toString(temp.age));
	        age_label.setText(temp.is_old());
	        image_entry.setImage(temp.get_image());
	        if(temp.gender) {
	        	gender_label.setText("Erkek");
	        }
	        else {
	        	gender_label.setText("Diþi");
	        }
	        if(temp.medicines != null) {
		        ArrayList<String> meds = new ArrayList<String>();
		        for (Medicine i : temp.medicines) {
		            String txt = i.get_type() + " : " + i.name + " : " + i.date;
		            meds.add(txt);
		        }
		        ObservableList<String> observableMeds = FXCollections.observableArrayList(meds);
		        medicine_list.setItems(observableMeds);
	        }
	    }
    }
    @FXML
    private Button get_animal_button;
    @FXML
    void get_animal_click(ActionEvent event) {
    	try {
    	    int id = Integer.parseInt(getIdFromList().trim());
        	String query = "SELECT * FROM REQUESTS WHERE IS_APPROVED = TRUE AND ANIMAL_ID = "+id;
        	if(MySQL.get_requests(query).size() == 0) {
        	    query = "SELECT * FROM REQUESTS WHERE USER_ID = "+User.current.id+" and ANIMAL_ID = "+id;
        	    ArrayList<request> r = MySQL.get_requests(query);
        	    if(r.size() == 0) {
        	    	query = "INSERT INTO REQUESTS (USER_ID,ANIMAL_ID) VALUES ("+User.current.id+","+id+")";
        	    	MySQL.commit(query);
        	    	get_animal_button.setText("Ýstek Gönderildi!");
        	    }
        	    else {
        	    	if(r.get(0).is_pending) {
            	    	get_animal_button.setText("Zaten Ýstek Gönderdiniz!");
        	    	}
        	    	else {
        	    		if(r.get(0).is_approved) {
        	    			get_animal_button.setText("Zaten Sahiplendiniz!");
        	    		}
        	    		else {
        	    			get_animal_button.setText("Ýsteðiniz Reddedildi!");
        	    		}
        	    	}
        	    }
        	}
    	} catch (Exception  e) {
    	    System.out.println("Seçili öðe yok");
    	}
    }
    @FXML
    void initialize() {
    	
    	username_label.setText(User.current.username);
        Dog_selector.setSelected(true);
        Animal icon = new Dog(-1,"","","https://icons.iconarchive.com/icons/iconarchive/cute-animal/512/Cute-Dog-icon.png",0,true);
        app_icon.setImage(icon.get_image());
        set_list();
        
    }
    
    
}
