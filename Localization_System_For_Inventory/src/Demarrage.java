import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Demarrage {

	private JFrame frmInventoryLocation;
	private JTextField txtFldSearch;
	private JButton btnRechercher;
	private JLabel lblDescription;
	private JTextField txtFldDescription;
	private JButton btnAjouterItem;
	private JButton btnPrint;
	private JComboBox<String> cmbBoxRoomNames;
	private JButton btnAjouterSalle;

	private Inventaire inventory = new Inventaire();
	private String fileName = "inventory.bin";
	private MapShop mapShop = new MapShop();
	private Room currentRoom;
	private ArrayList<String> roomsInCmbBox = new ArrayList<String>();
	private ArrayList<String> binsInCmbBox = new ArrayList<String>();
	private boolean firstEntry = true;
	private ArrayList<Rectangle> binCoordinate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demarrage window = new Demarrage();
					window.frmInventoryLocation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Demarrage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			inventory = (Inventaire) is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Le fichier inventory.bin n'a pas été détecté!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frmInventoryLocation = new JFrame();
		frmInventoryLocation.getContentPane().setBackground(new Color(102, 153, 255));
		frmInventoryLocation.setTitle("Location for Inventory");
		frmInventoryLocation.setBounds(100, 100, 1200, 680);
		frmInventoryLocation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInventoryLocation.getContentPane().setLayout(null);

		JLabel lblCode = new JLabel("Code article: ");
		lblCode.setBounds(10, 14, 88, 14);
		frmInventoryLocation.getContentPane().add(lblCode);

		txtFldSearch = new JTextField();
		txtFldSearch.setColumns(10);
		txtFldSearch.setBounds(97, 11, 227, 20);
		frmInventoryLocation.getContentPane().add(txtFldSearch);

		btnRechercher = new JButton("Rechercher");
		btnRechercher.setBounds(97, 39, 227, 23);
		frmInventoryLocation.getContentPane().add(btnRechercher);

		lblDescription = new JLabel("Description: ");
		lblDescription.setBounds(10, 73, 88, 14);
		frmInventoryLocation.getContentPane().add(lblDescription);

		txtFldDescription = new JTextField();
		txtFldDescription.setEditable(false);
		txtFldDescription.setColumns(10);
		txtFldDescription.setBounds(97, 73, 262, 76);
		frmInventoryLocation.getContentPane().add(txtFldDescription);

		btnAjouterItem = new JButton("Ajouter Item");
		btnAjouterItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAjouterItem.setBounds(97, 158, 227, 23);
		frmInventoryLocation.getContentPane().add(btnAjouterItem);

		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPrint.setBounds(97, 192, 227, 23);
		frmInventoryLocation.getContentPane().add(btnPrint);

		cmbBoxRoomNames = new JComboBox<String>();
		cmbBoxRoomNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentRoom = inventory.getRoomByName(cmbBoxRoomNames.getSelectedItem().toString());
				binCoordinate = currentRoom.getBinsRectangleCoordinate();
				mapShop.drawBins(binCoordinate);

			}
		});
		cmbBoxRoomNames.setBounds(568, 14, 227, 22);
		frmInventoryLocation.getContentPane().add(cmbBoxRoomNames);

		btnAjouterSalle = new JButton("Ajouter Salle");
		btnAjouterSalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newRoom = JOptionPane.showInputDialog("Nom de la nouvelle salle");
				if (inventory.addRoom(newRoom)) {
					save();
					setCmbBoxRoomNames(newRoom);
					JOptionPane.showMessageDialog(null, "Salle rajouté à l'inventaire!");
				} else
					JOptionPane.showMessageDialog(null, "L'ajout de la salle a échoué.");
			}
		});
		btnAjouterSalle.setBounds(805, 14, 227, 23);
		frmInventoryLocation.getContentPane().add(btnAjouterSalle);

		mapShop = new MapShop();
		mapShop.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				firstEntry = true;
				currentRoom = inventory.getRoomByName(cmbBoxRoomNames.getSelectedItem().toString());
				String newBin = JOptionPane
						.showInputDialog("Nom de la nouvelle bin dans la salle " + currentRoom.getName() + ": ");
				if (newBin == null || (newBin != null && ("".equals(newBin)))) {
					currentRoom.removeBin(currentRoom.size() - 1);
					mapShop.drawBins(currentRoom.getBinsRectangleCoordinate());
					return;
				}
				if (newBin.isBlank()) {
					currentRoom.removeBin(currentRoom.size() - 1);
					mapShop.drawBins(currentRoom.getBinsRectangleCoordinate());
					JOptionPane.showMessageDialog(null, "Vous ne pouvez pas mettre un nom vide!");
					return;
				}
				for (Bin b : currentRoom.getBins()) {
					if (newBin.equals(b.getName())) {
						currentRoom.removeBin(currentRoom.size() - 1);
						mapShop.drawBins(currentRoom.getBinsRectangleCoordinate());
						JOptionPane.showMessageDialog(null,
								"Vous ne pouvez pas créer la bin " + b.getName() + " car elle existe déjà");
						return;
					}
				}
				currentRoom.getBinByIndex(currentRoom.size() - 1).setName(newBin);
				mapShop.drawBins(currentRoom.getBinsRectangleCoordinate());
				save();
			}
		});
		mapShop.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				currentRoom = inventory.getRoomByName(cmbBoxRoomNames.getSelectedItem().toString());
				if (!firstEntry) {
					currentRoom.getBinByIndex(currentRoom.size() - 1).endDrawingBin(new Point(e.getX(), e.getY()));
					mapShop.drawBins(currentRoom.getBinsRectangleCoordinate());
					return;
				}
				firstEntry = false;
				currentRoom.addBin(new Point(e.getX(), e.getY()));
				binCoordinate = currentRoom.getBinsRectangleCoordinate();
				mapShop.drawBins(binCoordinate);
			}
		});
		mapShop.setBounds(369, 43, 807, 589);
		frmInventoryLocation.getContentPane().add(mapShop);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmInventoryLocation.setLocation(dim.width / 2 - frmInventoryLocation.getSize().width / 2,
				dim.height / 2 - frmInventoryLocation.getSize().height / 2);

		setCmbBoxRoomNames();
		if (inventory.size() > 0) {
			currentRoom = inventory.getRoomByName(cmbBoxRoomNames.getSelectedItem().toString());
			mapShop.drawBins(currentRoom.getBinsRectangleCoordinate());
		}

	}

	protected void save() {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
			os.writeObject(inventory);
			os.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void setCmbBoxRoomNames() {
		roomsInCmbBox = inventory.getRoomNames();
		roomsInCmbBox.sort(String::compareToIgnoreCase);
		cmbBoxRoomNames.setModel(new DefaultComboBoxModel<String>(roomsInCmbBox.toArray(new String[0])));

	}

	private void setCmbBoxRoomNames(String selectedItem) {
		roomsInCmbBox = inventory.getRoomNames();
		roomsInCmbBox.sort(String::compareToIgnoreCase);
		cmbBoxRoomNames.setModel(new DefaultComboBoxModel<String>(roomsInCmbBox.toArray(new String[0])));
		cmbBoxRoomNames.setSelectedItem(selectedItem);

	}
}
