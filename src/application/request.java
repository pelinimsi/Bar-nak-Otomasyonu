package application;

import java.sql.Date;

public class request {
	public static request current;
	public int user_id;
	public int animal_id;
	public Date req_date;
	public boolean is_pending;
	public boolean is_approved;
	request(int user_id,int animal_id,boolean is_pending,boolean is_approved,Date req_date){
		this.user_id = user_id;
		this.animal_id = animal_id;
		this.is_pending = is_pending;
		this.is_approved = is_approved;
		this.req_date = req_date;
	}
}
