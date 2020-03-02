import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {

	private String description;
	private int code;

	public Item(int code, String description) {
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String toString() {
		return "Code: " + code + ", Description: " + description;
	}

}
