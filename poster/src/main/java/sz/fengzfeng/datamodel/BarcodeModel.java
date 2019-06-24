package sz.fengzfeng.datamodel;

public class BarcodeModel {
	
	public BarcodeModel() {
		this.setText_down("GREE  ");
		this.setFontsize(33);
	}
	private String name;
	private int replace_start_x;
	private int replace_start_y;
	private String text_up;
	private String text_down;
	private String barcode;
	private float shear;
	private String bgpath;
	private int fontsize ;
	public int getReplace_start_x() {
		return replace_start_x;
	}
	public void setReplace_start_x(int replace_start_x) {
		this.replace_start_x = replace_start_x;
	}
	public int getReplace_start_y() {
		return replace_start_y;
	}
	public void setReplace_start_y(int replace_start_y) {
		this.replace_start_y = replace_start_y;
	}
	public String getText_up() {
		return text_up;
	}
	public void setText_up(String text_up) {
		this.text_up = text_up;
	}
	public String getText_down() {
		return text_down;
	}
	public void setText_down(String text_dawn) {
		this.text_down = text_dawn;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public float getShear() {
		return shear;
	}
	public void setShear(float shear) {
		this.shear = shear;
	}
	public String getBgpath() {
		return bgpath;
	}
	public void setBgpath(String bgpath) {
		this.bgpath = bgpath;
	}
	public int getFontsize() {
		return fontsize;
	}
	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
