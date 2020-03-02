import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4292061204831768711L;
	private ArrayList<Bin> bins;
	private String name;
	private boolean resetBin = false;

	public Room(String name) {
		this.name = name;
		bins = new ArrayList<Bin>();
		bins.add(new Bin("Pas placé"));
	}

	public boolean addBin(String newBinName) {
		if (newBinName.isBlank()) {
			return false;
		}
		if (newBinName.equals("Pas placé")) {
			return false;
		}
		for (Bin b : bins) {
			if (b.getName().equals(newBinName)) {
				return false;
			}
		}
		bins.add(new Bin(newBinName));
		return true;
	}
	public void addBin(Point p) {
		bins.add(new Bin(p));
	}

	public String getName() {
		return name;
	}

	public ArrayList<Bin> getBins() {
		return bins;
	}

	public Bin getBinByIndex(int index) {
		if (index < bins.size()) {
			return bins.get(index);
		}
		return null;
	}

	public Bin getBinByname(String name) {
		for (Bin b : bins) {
			if (b.getName().equals(name)) {
				return b;
			}
		}
		return null;
	}
	public int getBinIndexByName(String name) {
		int indexBin=0;
		for (Bin b : bins) {
			if (b.getName().equals(name)) {
				return indexBin;
			}
			indexBin++;
		}
		return (Integer)null;
	}

	public boolean removeBin(int indexBin) {
		if (indexBin < bins.size()) {
			bins.remove(indexBin);
			return true;
		}
		return false;
	}

	public int size() {
		return bins.size();
	}

	public String toString() {
		String binTostring = "";
		for (Bin b : bins) {
			binTostring+=name+": \n";
			for (int i = 0; i < b.size(); i++) {
				binTostring += b.getItem(i).toString() + "\n";
			}
		}
		return binTostring;
	}

	public ArrayList<Rectangle> getBinsRectangleCoordinate() {
		ArrayList<Rectangle> binsRectangleCoordinate = new ArrayList<Rectangle>();
		for (Bin b : bins) {
			binsRectangleCoordinate.add(b.getGraphics());
		}
		return binsRectangleCoordinate;
	}

	public ArrayList<Rectangle> searchRoomForItemByCode(int codeItem) {
		ArrayList<Rectangle> haveIt = new ArrayList<Rectangle>();
		for (Bin b : bins) {
			for (Item i: b.getItems()) {
				if (codeItem == i.getCode() && !resetBin) {
					haveIt.add(b.getGraphics());
					resetBin = true;
				}
			}
			resetBin = false;
		}
		return haveIt;
	}

	public ArrayList<String> getBinNames(){
		ArrayList<String>  binNames = new ArrayList<String>();
		for(Bin b : bins) {
			binNames.add(b.getName());
		}
		return binNames;
	}
}
