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

public class MainPage {
	
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
    private TextField age_entry;

    @FXML
    private Label age_label;

    @FXML
    private ImageView image_entry;

    @FXML
    private TextField kind_entry;

    @FXML
    private TextField medicine_date;

    @FXML
    private ListView<String> animal_list;
    
    @FXML
    private ListView<String> medicine_list;

    @FXML
    private TextField medicine_name;

    @FXML
    private TextField name_entry;

    @FXML
    private TextField search_entry;

    @FXML
    private TextField url_entry;
    
    @FXML
    private RadioButton female_selector;

    @FXML
    private Label username_label;
    
    @FXML
    private RadioButton male_selector;
    
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
		btn11.setText("Onayla");
		btn12.setText("Reddet");
        get_last_req();
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
    void Pill_select(ActionEvent event) {
    	if(Vaccine_selector.isSelected())
    		Vaccine_selector.setSelected(false);
    	else
    		Pill_selector.setSelected(true);
    	Vaccine_selector.setSelected(false);
    }

    @FXML
    void Vaccine_select(ActionEvent event) {
    	if(Pill_selector.isSelected())
    		Pill_selector.setSelected(false);
    	else
    		Vaccine_selector.setSelected(true);
    	Pill_selector.setSelected(false);
    }

    @FXML
    void famale_select(ActionEvent event) {
    	if(male_selector.isSelected())
    		male_selector.setSelected(false);
    	else
    		female_selector.setSelected(true);
    	male_selector.setSelected(false);
    }

    @FXML
    void male_select(ActionEvent event) {
    	if(female_selector.isSelected())
    		female_selector.setSelected(false);
    	else
    		male_selector.setSelected(true);
    	female_selector.setSelected(false);
    }
    
    @FXML
    void add_animal_button(ActionEvent event) {
    	String query = "INSERT INTO ANIMALS (NAME,KIND,AGE,GENDER,IS_DOG,URL) VALUE "
    	+"('"+name_entry.getText()+"','"+kind_entry.getText()+"',"+age_entry.getText()+","+male_selector.isSelected()+","+Dog_selector.isSelected()+",'"+url_entry.getText()+"')";
    	MySQL.commit(query);
    	set_list();
    }

    @FXML
    void delete_animal_button(ActionEvent event) {
    	String query = "DELETE FROM ANIMALS WHERE ID = "+getIdFromList();
    	MySQL.commit(query);
        set_list();
    }

    @FXML
    void edit_animal_button(ActionEvent event) {

    	String query = "UPDATE ANIMALS SET NAME = '"+name_entry.getText()+"', KIND = '"+kind_entry.getText()+"',AGE = "+age_entry.getText()+",GENDER = "
    			+male_selector.isSelected()+",URL = '"+url_entry.getText()+"'" +" WHERE ID = "+getIdFromList();
    	MySQL.commit(query);
    	set_list();
    	
    }

    @FXML
    void medicine_add_button(ActionEvent event) {
    	String query = "INSERT INTO MEDICINES VALUE("+getIdFromList()+",'"+medicine_name.getText()+"','"+medicine_date.getText()+"',"+Vaccine_selector.isSelected()+")";
    	MySQL.commit(query);
    	if(Vaccine_selector.isSelected())
    		current.medicines.add(new Vaccine(medicine_name.getText(),medicine_date.getText()));
    	else
        	current.medicines.add(new Pill(medicine_name.getText(),medicine_date.getText()));
    	set_UI(current);
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
	        url_entry.setText(temp.photo_url);
	        if(temp.gender) {
	        	male_selector.setSelected(true);
	        	female_selector.setSelected(false);
	        }
	        else {
	        	male_selector.setSelected(false);
	        	female_selector.setSelected(true);
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
    void get_last_req() {
    	int id = Integer.parseInt(getIdFromList().trim());
    	String query = "SELECT * FROM REQUESTS WHERE IS_APPROVED = TRUE AND ANIMAL_ID = "+id;
    	if(MySQL.get_requests(query).size() == 0) {
        	query = "SELECT * FROM REQUESTS WHERE IS_PENDING = TRUE AND ANIMAL_ID = "+id+" ORDER BY REQ_DATE";
            ArrayList<request> r = MySQL.get_requests(query);
        	if(r.size() == 0) {
        		request.current = null;
        		req_name.setText("-");
        		req_date.setText("-");
        		req_id.setText("0");
        	}
        	else {
        		request.current = r.get(0);
        		req_date.setText(r.get(0).req_date.toString());
        		String user_id = ""+r.get(0).user_id;
        		req_id.setText(user_id);
        		req_name.setText(MySQL.getUsernameById(r.get(0).user_id)); 
        	}
    	}
    	else {
    		ArrayList<request> r = MySQL.get_requests(query);
    		btn11.setText("SAHÝPLÝ");
    		btn12.setText("SAHÝPLÝ");
    		request.current = r.get(0);
    		req_date.setText(r.get(0).req_date.toString());
    		String user_id = ""+r.get(0).user_id;
    		req_id.setText(user_id);
    		req_name.setText(MySQL.getUsernameById(r.get(0).user_id)); 
    	}
    }
    @FXML
    private Label req_name;

    @FXML
    private Label req_date;

    @FXML
    private Label req_id;
    
    @FXML
    private Button btn11;

    @FXML
    private Button btn12;
    
    @FXML
    void req_approve(ActionEvent event) {
    	String query = "UPDATE REQUESTS SET IS_PENDING = FALSE";
    	MySQL.commit(query);
    	query = "UPDATE REQUESTS SET IS_APPROVED = TRUE WHERE USER_ID = "+request.current.user_id;
    	MySQL.commit(query);
    	get_last_req();
    }

    @FXML
    void req_deny(ActionEvent event) {
    	String query = "UPDATE REQUESTS SET IS_PENDING = FALSE WHERE USER_ID = "+request.current.user_id;
    	MySQL.commit(query);
    	get_last_req();
    }
    
    @FXML
    void initialize() {

    	username_label.setText(User.current.username);
        Dog_selector.setSelected(true);
        Pill_selector.setSelected(true);
        Animal icon = new Dog(-1,"","","https://icons.iconarchive.com/icons/iconarchive/cute-animal/512/Cute-Dog-icon.png",0,true);
        app_icon.setImage(icon.get_image());
        set_list();
        
    }
    
    
}
