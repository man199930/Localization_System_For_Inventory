import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public class Bin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2373331347306535742L;
	private String name;
	private ArrayList<Item> bin = new ArrayList<Item>();
	private Point startingPoint, endingPoint;
	private Rectangle binRectangleCoordinate;

	public Bin(String nom) {
		this.name = nom;
		System.out.println();
	}

	public Bin(Point startingPoint) {
		this.startingPoint = startingPoint;
		binRectangleCoordinate = new Rectangle(startingPoint);
	}

	public void endDrawingBin(Point endingPoint) {
		this.endingPoint = endingPoint;
		binRectangleCoordinate = new Rectangle(startingPoint);
		binRectangleCoordinate.add(endingPoint);
	}

	public void setStartingpoint(Point p) {
		startingPoint = p;
	}

	public Rectangle getGraphics() {
		return binRectangleCoordinate;
	}

	public Item getItem(int IndexItem) {
		return bin.get(IndexItem);
	}

	public Item getItemByCode(int numArticleItem) {
		for (Item i : bin) {
			if (i.getCode() == numArticleItem) {
				return i;
			}
		}
		return null;
	}

	public boolean removeItem(int indexItem) {
		if (indexItem < bin.size()) {
			bin.remove(indexItem);
			return true;
		}
		return false;
	}

	public boolean addItem(int itemCode, String description) {
		for (Item i : bin) {
			if (i.getCode() == itemCode) {
				return false;
			}
		}
		bin.add(new Item(itemCode, description));
		return true;
	}

	public int size() {
		return bin.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Item> getItems() {
		return bin;
	}

}
