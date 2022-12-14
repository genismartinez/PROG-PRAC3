package InterficieGrafica;
import Dades.*;
import Exceptions.OutOfRangeException;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.AncestorListener;

import Aplicacio.CFitx;
/**
 * Classe de la interfície gràfica
 * @author grup 13
 *
 */
public class Interficie extends JFrame {
	private String colorPedres = "black";
	private String colorTrossos = "black";
	private int anyActual = 0;
	private static LlistaGeneric<Plantacions>llistaPlantacions;
	JPanel panel = new JPanel();
	JLabel info = new JLabel();
	JTextArea entradaAny = new JTextArea();
	JLabel negre = new JLabel();
	JLabel lila = new JLabel();
	JLabel roig = new JLabel();
	JLabel blau = new JLabel();
	JLabel verd = new JLabel();
	JButton botoTros = new JButton();
	JButton botopedres = new JButton();
	public static boolean first=false;
	private int opcio = 0;
	public Interficie(LlistaGeneric<Plantacions>llistaPlantacions) {
		this.llistaPlantacions=llistaPlantacions;
		
		llegirAny();
		//Establir tamany de pantalla
		this.setSize(500,500);
		
		//Li donem un nom a la finestra
		setTitle("Prac 3");

		//Posem la fienstra al centre de la pantalla
		setLocation(100,300);
		//setLocationRelativeTo(null);

		JLabel fondo = new JLabel(new ImageIcon("fondo.jpg"));
		fondo.setBounds(0, 0, 500, 500);
		
		panel.add(fondo);

		iniciarComponents();

		//Afegir el panel que acabem de crear
		this.getContentPane().add(panel);

		//Desactivem el diseny per a poder posar les coses on vulguem
		panel.setLayout(null);

		//Quan tanquis la finestra finalitza TOT el programa
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	/**
	 *Creem un action listener per a guardar l'arxiu
	 */
	ActionListener saveFile=new ActionListener() {
		@Override 
		public void actionPerformed(ActionEvent ae) {
			try {
				CFitx.escribirFicheroSerializado(CFitx.leerTerreno());
			}catch(FileNotFoundException e) {
				System.out.println("Fitxer no trobat");
			}
			catch(OutOfRangeException e) {
				System.out.println(e.getMessage());
			}

		}
	};
	// Override the dispose method.
	@Override
	public void dispose(){
		// Custom close operation here.
		String message="Vols guardar el fitxer serialitzat?";
		int reply = JOptionPane.showConfirmDialog(null, message, "Menu guardar", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			try {
				CFitx.escribirFicheroSerializado(CFitx.leerTerreno());
				JOptionPane.showMessageDialog(null, "Fitxer guardat correctament: terreny.dat");
				System.exit(1);
			}catch(FileNotFoundException e) {
				System.out.println("Fitxer no trobat");
			}
			catch(OutOfRangeException e) {
				System.out.println(e.getMessage());
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "No s'ha guardat el fitxer");
			System.exit(0);
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	
	private void iniciarComponents() {

		colocarPanels();
		colocarBotons();
	}
	/**
	 * Método que inicializa el panel y coloca las etiquetas corrsespondientes
	 */
	private void colocarPanels() {
		JLabel etiqueta = new JLabel();
		JPanel fondo = new JPanel();
		//Espai per a posar la etiqueta que mostrará info
		info.setBounds(0, 0, 500, 20);
		info.setBackground(Color.WHITE);
		info.setOpaque(true);
		info.setText("Practica 3 feta per Carlos, Genís i Joan");
		panel.add(info);

		/////////////////////////////////////////////////
		negre.setBackground(Color.black);
		negre.setBounds(0, 400, 10, 10);
		negre.setOpaque(true);
		panel.add(negre);
		lila.setBackground(Color.magenta);
		lila.setBounds(10, 400, 10, 10);
		lila.setOpaque(true);
		panel.add(lila);
		roig.setBounds(20, 400, 10, 10);
		roig.setBackground(Color.red);
		roig.setOpaque(true);
		panel.add(roig);
		blau.setBackground(Color.blue);
		blau.setBounds(30, 400, 10, 10);
		blau.setOpaque(true);
		panel.add(blau);
		verd.setBackground(Color.green);
		verd.setBounds(40, 400, 10, 10);
		verd.setOpaque(true);
		panel.add(verd);

		/////////////////////////////////////////////////
		entradaAny.setBounds(0, 30, 300, 50);

		entradaAny.setEditable(false);

		JLabel llegenda = new JLabel();
		llegenda.setBounds(0, 420, 500, 50);
		llegenda.setText("Negre->40K, Lila->1M, Blau->1M5k, Verd->Mes de 1M5K");
		panel.add(llegenda);
	}
	/**
	 *Disposem els botons a la finestra
	 */
	private void colocarBotons() {

		//Crear boto per a sortir
		JButton botoExit = new JButton();
		botoExit.setBounds (400,20, 110, 40);
		botoExit.setText("Exit");
		botoExit.setBackground(Color.RED);
		botoExit.setForeground(Color.WHITE);
		botoExit.setEnabled(true);
		panel.add(botoExit);

		ActionListener oyentdeDeExit = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//System.exit(0);
				dispose();
			}
		};
		botoExit.addActionListener(oyentdeDeExit);

		JButton switchYear=new JButton();
		switchYear.setBounds(380,60,110,40);

		switchYear.setText("Canviar d'any");
		switchYear.setEnabled(true);
		panel.add(switchYear);
		ActionListener canviarAny=new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent ae) {
				llegirAny();

			}
		};
		switchYear.addActionListener(canviarAny);

		//Crear boto per a mostrar que fa cada cosa
		
		botopedres.setBounds (0,100, 500, 40);
		
		botopedres.setText("Finca les pedres, creada el 2018");
		botopedres.setEnabled(true);
		canviacolors();
		
		panel.add(botopedres);

		botoTros.setBounds (0,140, 500, 40);
		botoTros.setText("Finca els trossos, creada el 2015");
		botoTros.setEnabled(true);
		canviacolors();
		panel.add(botoTros);
	}
	/**
	 * Getter
	 * @return opcio
	 */
	public int getOpcio() {
		System.out.println(opcio);
		return opcio;
	}
	/**
	 * Booleano comprueba si es un número o no
	 * @param str supuesto número
	 * @return true si es número, falso si no
	 */
	public static boolean esNumero(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}  
	}

	public void setColorPedres(String colorPedres) {
		this.colorPedres = colorPedres;
	}
	/**
	 * Setter
	 * @param colorTrossos nuevo color para el botón Trossos
	 */
	public void setColorTrossos(String colorTrossos) {
		this.colorTrossos = colorTrossos;
	}
	/**
	 * Getter
	 * @return anyActual
	 */
	public int getAny() {
		return anyActual;
	}
	/**
	 * Setter
	 * @param anyActual nuevo año
	 */
	public void setanyActual(int anyActual) {
		this.anyActual = anyActual;

	}
	/**
	 * Funció per llegir l'any'
	 */
	public int  llegirAny() {
		String nom = JOptionPane.showInputDialog("Indica a quin any et trobes:");
		
		while (nom == null || nom.equals("")||Integer.parseInt(nom)<0) {
			// Missatge d'error.
			JOptionPane.showMessageDialog(null, "Has d'introduir un any!", "ERROR", JOptionPane.ERROR_MESSAGE);
			nom = JOptionPane.showInputDialog("Indica a quin any et trobes: ");

		}
		this.anyActual = Integer.parseInt(nom);
		entradaAny.setText("Ara estas a l'any "+anyActual);
		entradaAny.setAlignmentX(400);
		panel.add(entradaAny);
		conditions();
		colocarBotons();
		return this.anyActual;
	}
	/**
	 *Condicions per a la coloració dels botons
	 */
	public void conditions() {
		int color = (int)CFitx.rodalAbsor(llistaPlantacions, "Finca les pedres", anyActual);
		if (color < 400000) {
			setColorPedres("black");
		}else if(color <1000000) {
			setColorPedres("magenta");
		}else if(color <1005000) {
			setColorPedres("red");
		}else if(color <1050000) {
			setColorPedres("blue");
		}else if(color >1050000) {
			setColorPedres("green");
		}
		color = (int)CFitx.rodalAbsor(llistaPlantacions, "Els trossos", anyActual);
		if (color < 400000) {
			setColorTrossos("black");
		}else if(color <1000000) {
			setColorTrossos("magenta");
		}else if(color <1005000) {
			setColorTrossos("red");
		}else if(color <1050000) {
			setColorTrossos("blue");
		}else if(color >1050000) {
			setColorTrossos("green");
		}
	}
	/**
	 *Mètode que modifica els colors
	 */
	public void canviacolors() {
		if(colorPedres.equalsIgnoreCase("black")) {
			botopedres.setBackground(Color.black);
			botopedres.setForeground(Color.WHITE);
		}else if(colorPedres.equalsIgnoreCase("magenta")) {
			botopedres.setBackground(Color.magenta);
		}else if(colorPedres.equalsIgnoreCase("red")) {
			botopedres.setBackground(Color.red);
		}else if(colorPedres.equalsIgnoreCase("blue")) {
			botopedres.setBackground(Color.blue);
		}else if(colorPedres.equalsIgnoreCase("green")) {
			botopedres.setBackground(Color.green);
			botopedres.setForeground(Color.black);
		}
		if(colorTrossos.equalsIgnoreCase("black")) {
			botoTros.setBackground(Color.black);
			botoTros.setForeground(Color.WHITE);
		}else if(colorTrossos.equalsIgnoreCase("magenta")) {
			botoTros.setBackground(Color.magenta);
		}else if(colorTrossos.equalsIgnoreCase("red")) {
			botoTros.setBackground(Color.red);
		}else if(colorTrossos.equalsIgnoreCase("blue")) {
			botoTros.setBackground(Color.blue);
		}else if(colorTrossos.equalsIgnoreCase("green")) {
			botoTros.setBackground(Color.green);
			botoTros.setForeground(Color.black);
		}
		
	}

} 