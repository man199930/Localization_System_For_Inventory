import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

public class Inventaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8830383729268204429L;
	private ArrayList<Room> rooms;
	private boolean resetBin = false;
	private ArrayList<Item> items;

	public Inventaire() {
		rooms = new ArrayList<Room>();
	}

	public int addItem(int codeItem) {
		if (itemExist(codeItem)) {
			JOptionPane.showInputDialog("In which bin do you want to put the item? ");
			return 1;
		}
		return 0;
	}

	public boolean itemExist(int codeItem) {
		if (items.isEmpty()) {
			return false;
		}
		for (Item i : items) {
			if (i.getCode() == codeItem) {
				return true;
			}
		}
		return false;
	}

	public boolean addRoom(String newRoomName) {
		if (newRoomName.isBlank()) {
			return false;
		}
		for (Room r : rooms) {
			if (r.getName().equals(newRoomName)) {
				return false;
			}
		}
		rooms.add(new Room(newRoomName));
		return true;
	}

	public int size() {
		return rooms.size();
	}

	public int getRoomIndexByName(String name) {
		int indexRoom = 0;
		for (Room r : rooms) {
			if (r.getName().equals(name)) {
				return indexRoom;
			}
			indexRoom++;
		}
		return (Integer) null;
	}

	public ArrayList<String> getRoomNames() {
		ArrayList<String> roomNames = new ArrayList<String>();
		for (Room r : rooms) {
			roomNames.add(r.getName());
		}
		return roomNames;
	}

	public Room getRoomByIndex(int indexRoom) {
		return rooms.get(indexRoom);
	}

	public Room getRoomByName(String roomName) {
		for (Room r : rooms) {
			if (r.getName().equals(roomName)) {
				return r;
			}
		}
		return null;
	}

	public String toString() {
		String inventory = "";
		for (Room r : rooms) {
			inventory += "In room " + r.getName() + "\n";
			for (Bin b : r.getBins()) {
				inventory += "In bin " + b.getName() + "\n";
				for (Item i : b.getItems()) {
					inventory += i.toString();
				}
				inventory += "\n";
			}
			inventory += "\n";
		}
		return inventory;
	}

}
