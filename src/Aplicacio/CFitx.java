package Aplicacio;

import Dades.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//import Dades.AbsorciÃ³;
import Dades.Arboria;
import Dades.LlistaGeneric;
import Dades.Plantacions;
import Dades.Rodals;
import Dades.Terreny;
import Exceptions.OutOfRangeException;

/**
 * Classe per tractar dades dels fitxers i fer cÃ lculs
 * @author grup 13
 *
 */
public class CFitx {
	private static Scanner teclado;
	
	/**
	 *MÃ¨tode per llegir plantacions
	 */
	public static LlistaGeneric<Plantacions> leerPlantaciones() throws FileNotFoundException, OutOfRangeException {
		LlistaGeneric<Terreny> terreno = leerTerreno();
		int nLines = 12;
		LlistaGeneric<Plantacions> plantacions = new LlistaGeneric<Plantacions>(2);
		teclado = new Scanner(new File("src/Plantacions.csv"));

		String text = teclado.nextLine();
		String[] split = null;
		Terreny terreny = null;
		int i = 0;
		boolean first = false;
		Plantacions plant = null;
		for (i = 0; i < nLines; i++) {
			split = teclado.nextLine().split(";");
			if (split[3].equalsIgnoreCase("CalcariSolana")) {
				terreny = terreno.consultatIessim(0);
			} else {
				if (split[3].equalsIgnoreCase("CalcariObaga"))
					terreny = terreno.consultatIessim(1);
				else
					terreny = new Terreny(split[3], "", 0);
			}
			if (!split[0].equalsIgnoreCase(" ")) {

				plant = new Plantacions(split[0], Integer.parseInt(split[1]),
						new Rodals(terreny, Float.parseFloat(split[4])));
				first = true;
			}
			if (!first) {
				plant.setTipusRodal(new Rodals(terreny, Float.parseFloat(split[4])));
			}
			first = false;
			if (i == 4 || i == 11) {
				plantacions.afegir(plant);
			}

		}
		return plantacions;
	}
	/**
	 *Mètode per llegir els terrenys de fitxer
	 */
	public static LlistaGeneric<Terreny> leerTerreno()
			throws FileNotFoundException, NumberFormatException, OutOfRangeException {
		int nLines = 10;
			
		LlistaGeneric<Terreny> terreno = new LlistaGeneric<Terreny>(2);
		LlistaGeneric<Arbustiva> arbusto = leerArbustos();
		LlistaGeneric<Arboria> arbol = leerArboles();

		teclado = new Scanner(new File("src/Terreny.csv"));
		teclado.nextLine();
		String[] split = null;

		/*
		 * split=teclado.nextLine().split(";"); String nom=split[0];
		 */
		Terreny<Planta> ter = null;
	
		

		boolean first = false;
		int i = 0;
		int arb = 0, arbus = 0;
		/*
		 * split[0]=Nom terreny split[1]=Nom planta split[2]=Unitats plantades
		 */
		for (i = 0; i < nLines; i++) {
			split = teclado.nextLine().split(";"); // separamos string
			arb = containsArbol(arbol, split[1]);
			arbus = containsArbust(arbusto, split[1]);
			if (!split[0].equalsIgnoreCase("/")) {

				if (arb != -1) {
					ter = new Terreny(split[0], arbol.consultatIessim(arb), Integer.parseInt(split[2]));
				} else {
					if (arbus != -1) {

						ter = new Terreny(split[0], arbusto.consultatIessim(arbus), Integer.parseInt(split[2]));
					} else {
						
						ter = new Terreny(split[0], new Arboria(), Integer.parseInt(split[2]));
					}
				}

				first = true;
			}
			if (!first) {
				if (arb != -1)
					ter.afegirPlanta(arbol.consultatIessim(arb), Integer.parseInt(split[2]));
				else {
					if (arbus != -1)
						ter.afegirPlanta(arbusto.consultatIessim(arbus), Integer.parseInt(split[2]));
					else
						ter.afegirPlanta(new Arboria(), arbus);
				}
			}
			first = false;
			if (i == 4 || i == 9) {
				terreno.afegir(ter);
				;
			}
		}
		return terreno;
	}
	/**
	 *Creem una llista de tipus genÃ¨ric d'arbres
	 */
	public static LlistaGeneric<Arboria> leerArboles() throws FileNotFoundException, OutOfRangeException {
		LlistaGeneric<Arboria> arbres = new LlistaGeneric<Arboria>(7);
		Arboria arbol = null;
		teclado = new Scanner(new File("src/Arbres.csv"));
		teclado.nextLine();
		String[] split = null;
		while (teclado.hasNextLine()) {
			split = teclado.nextLine().split(";");
			if (split[5].isBlank()) {
				split[5] = null;
				split[6] = "0";
			}
			arbol = new Arboria(split[0], 1, split[1], Float.parseFloat(split[2]), split[3], Float.parseFloat(split[4]),
					split[5], Float.parseFloat(split[6]));
			arbres.afegir(arbol);

		}
		return arbres;
	}
	/**
	 *Creem una llista de tipus genÃ¨ric d'arbustos
	 */
	public static LlistaGeneric<Arbustiva> leerArbustos() throws FileNotFoundException, OutOfRangeException {
		LlistaGeneric<Arbustiva> arbustos = new LlistaGeneric<Arbustiva>(4);
		Arbustiva arbusto = null;
		teclado = new Scanner(new File("src/Arbustos.csv"));
		teclado.nextLine();
		String[] split = null;
		while (teclado.hasNextLine()) {
			split = teclado.nextLine().split(";");
			arbusto = new Arbustiva(split[0], 2, Float.parseFloat(split[1]));
			arbustos.afegir(arbusto);
		}
		return arbustos;
	}
	/**
	 *Comrprova si Ã©s arbÃ²ria
	 */
	public static int containsArbol(LlistaGeneric<Arboria> texto, String text) {

		for (int i = 0; i < texto.length(); i++) {
			if (texto.consultatIessim(i).getNomCient().equalsIgnoreCase(text))
				return i;
		}
		return -1;
	}
	/**
	 *Comrprova si Ã©s arbustiva
	 */
	public static int containsArbust(LlistaGeneric<Arbustiva> arbust, String text) {
		for (int i = 0; i < arbust.length(); i++) {
			if (arbust.consultatIessim(i).getNomCient().equalsIgnoreCase(text))
				return i;
		}
		return -1;
	}
	/**
	 *Comprova l'absorciÃ³ donada una espÃ¨cie i un any'
	 */
	public static float absorEspecie(LlistaGeneric<Plantacions>llistaPlantacions,String especie,int any) {
		float absorc=0;
		boolean found=false;
		
			for (int i = 0; i<llistaPlantacions.nElems()&&!found; i++) {
				for (int j = 0; j < llistaPlantacions.consultatIessim(i).getNelems()&&!found; j++) {
					for (int k = 0; k < llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getNelems()&&!found&&
							llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k)!=null; k++) {
						if (llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k).getNomCient()
								.equalsIgnoreCase(especie)) {
							try{
							absorc=llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k).getAbsor(any)*
									llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getUnits(k);
							found=true;
							}catch(NumberFormatException e) {
								System.out.println(e.getMessage());
							}
						}
					}
				}
		}
			return absorc;
	}
	public static float absort(LlistaGeneric<Plantacions> llistaPlantacions, String especie, int edat) {
		boolean printed = false;
		float absor=0;
		// float
		// uno=llistaPlantacions.consultatIessim(0).getTipusTerreny(0).getPlanta(3).getAbs(edat);
		try {
			for (int i = 0; printed == false; i++) {
				for (int j = 0; j < llistaPlantacions.consultatIessim(i).getNelems() && !printed; j++) {
					for (int k = 0; k < llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getNelems()
							&& !printed; k++) {
						if (llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k).getNomCient()
								.equalsIgnoreCase(especie)) {
							 return llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k).getAbsor(edat);
							// System.out.println(llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k).getAbs(edat));
							
							

						}
					}
				}
			}
		} catch (ClassCastException E) {
			System.out.println("No s'ha trobat l'element buscat");
		}
		catch(NullPointerException e) {
			System.out.println("No hi ha hagut cap coincidÃ¨ncia");
		}
		return 0;
	}
	/**
	 * CÃ¡lculo de la absorciÃ³n total de un rodal dada una planta y un aÃ±o
	 * @param llistaPlantacions lista de las plantaciones leÃ­da de fichero
	 * @param plant nombre de la planta
	 * @param any aÃ±o para realizar el cÃ¡lculo
	 * @return absorciÃ³n del rodal dada la planta y el aÃ±o
	 */
	public static float rodalAbsor(LlistaGeneric<Plantacions> llistaPlantacions, String plant, int any) {
		float total = 0;
		float total1 = 0;
		float absortion;
		for (int i = 0; i < llistaPlantacions.nElems(); i++) {
			if (llistaPlantacions.consultatIessim(i).getNomPlantacio().equalsIgnoreCase(plant))
				if (any > llistaPlantacions.consultatIessim(i).getAnyPlantacio()) {
					System.out.println("\nPlantaciÃ³ " + llistaPlantacions.consultatIessim(i).getNomPlantacio() + ": ");
					for (int j = 0; j < llistaPlantacions.consultatIessim(i).getNelems(); j++) {

						System.out.println("RODAL " + j + ", "+llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getNomTerreny()+" :");
						for (int k = 0; k < llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getNelems(); k++) {
							int age = any - llistaPlantacions.consultatIessim(i).getAnyPlantacio();
							if (age < 0) {
								age = 0;
							}
							try {
							 absortion = llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k)
									.getAbsor(age);
							}catch(NullPointerException e) {absortion=0;}
							// System.out.println(llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getPlanta(k).getNomCient());
							int units = llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getUnits(k);
							total = total + units * absortion;
							/*
							 * total=total+llistaPlantacions.consultatIessim(i).getTipusTerreny(j).getUnits(
							 * k)*llistaPlantacions.consultatIessim(i)
							 * .getTipusTerreny(j).getPlanta(k).getAbs(any-llistaPlantacions.consultatIessim
							 * (i) .getAnyPlantacio());
							 */
						}
						System.out.println("AbsorciÃ³ total del rodal: " + total);
						total1 += total;
						total = 0;

					}
				} else
					System.out.println("L'any introduit Ã©s anterior a la la plantaciÃ³ "
							+ llistaPlantacions.consultatIessim(i).getNomPlantacio());
		}
		System.out.println();
		return total1;
	}
	/**
	 * MÃ©todo para serializar clases y guardarlo en un fichero .dat
	 * @param fitxer objeto a serializar
	 */
	public static void escribirFicheroSerializado(LlistaGeneric fitxer){

		FileOutputStream fichero = null;
		ObjectOutputStream salida = null;
		
		Arbustiva arb=new Arbustiva("Name",2,3);
		try {
			// AquÃ­ creo el fichero
			fichero = new FileOutputStream("src/terreny.dat");
			salida = new ObjectOutputStream(fichero);
			
				salida.writeObject(fitxer);

		} catch (FileNotFoundException e) {
			System.out.println("1" + e.getMessage());
		} catch (IOException e) {
			System.out.println("2" + e.getMessage());
		} finally {
			try {
				if (fichero != null) {
					fichero.close();
				}
				if (salida != null) {
					salida.close();
				}
			} catch (IOException e) {
				System.out.println("3" + e.getMessage());
			}
		}
	}
	/**
	 * Lee un fichero con objeto serializado previamente y lo carga en una variable que imprimimos despuÃ©s
	 */
	public static void leerSerializado() {
		ObjectInputStream inputFile=null;
		Object llista=new LlistaGeneric<>(2);
		try {
			try {
			inputFile=new ObjectInputStream(new FileInputStream("src/terreny.dat"));
			}catch(FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
			llista= inputFile.readObject();
			System.out.println(llista);
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		catch(ClassCastException e) {
			System.out.println(e.getMessage());
		}
		catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
}
