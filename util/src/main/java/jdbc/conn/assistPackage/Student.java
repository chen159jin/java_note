package jdbc.conn.assistPackage;

import java.sql.ResultSet;

public class Student {
	private String id;
	private String xh;
	private String xm;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String toString() {
		return "Student [id=" + id + ", xh=" + xh + ", xm=" + xm + "]";
	}
	




}
