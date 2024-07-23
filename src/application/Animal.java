package application;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javafx.scene.image.Image;

abstract class Animal {
	ArrayList<Medicine> medicines = new ArrayList<Medicine>();
	private int id;
    String name;
    String kind;
    String photo_url;
    int age;
    boolean gender;
    abstract String is_old();
    public Animal(int id,String name, String kind, String photo_url,int age, boolean gender) {
        this.id = id;
    	this.name = name;
        this.kind = kind;
        this.age = age;
        this.gender = gender;
        this.photo_url = photo_url;
    }
    public int get_id() {
    	return id;
    }
    public Image get_image() {
        try {
            URL imageURL = new URL(photo_url);
            URLConnection conn = imageURL.openConnection();
            InputStream inputStream = conn.getInputStream();
            Image image = new Image(inputStream);
            inputStream.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
class Cat extends Animal {
    public Cat(int id,String name, String kind, String photo_url, int age, boolean gender) {
        super(id, name, kind, photo_url, age, gender);
    }
    String is_old() {
        if (age < 1)
            return "Yavru";
        else if (age < 6)
            return "Genç";
        else if (age < 12)
            return "Orta yaþlý";
        else
            return "Yaþlý";
    }
}
class Dog extends Animal {
    public Dog(int id,String name, String kind, String photo_url, int age, boolean gender) {
        super(id, name, kind, photo_url, age, gender);
    }
    String is_old() {
        if (age < 1)
            return "Yavru";
        else if (age < 5)
            return "Genç";
        else if (age < 10)
            return "Orta yaþlý";
        else
            return "Yaþlý";
    }
}
//--------------------------------------------------------------------------------------------------------------
abstract class Medicine{
	String date;
	String name;
	abstract String get_type();
	public Medicine(String name,String date) {
		this.name = name;
		this.date = date;
	}
}
class Vaccine extends Medicine{
	public Vaccine(String name,String date) {
		super(name,date);
	}
	String get_type() {
		return "Aþý";
	}
}
class Pill extends Medicine{
	public Pill(String name,String date) {
		super(name,date);
	}
	String get_type() {
		return "Hap";
	}
}