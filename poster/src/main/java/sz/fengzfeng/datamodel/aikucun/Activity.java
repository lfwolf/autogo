package sz.fengzfeng.datamodel.aikucun;

import java.util.Date;

public class Activity {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getAftersaletime() {
		return aftersaletime;
	}
	public void setAftersaletime(Date aftersaletime) {
		this.aftersaletime = aftersaletime;
	}
	private String name;
	private Date aftersaletime;

}
