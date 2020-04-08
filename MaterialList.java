
public class MaterialList {

	private String name;
	private String type;
	private double price;
	private int quantity;
	
	
	public MaterialList() {
		this.name = "";
		this.type = "";
		this.price = 0;
		this.quantity = 0;
	}
	
	public MaterialList(String name, String type, double price, int quantity) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}



