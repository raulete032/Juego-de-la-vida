package practica_28;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * 
 * @author Raúl Carneros Rodríguez
 *
 */
public class Controlador28 implements ActionListener {

	
	//Variables de instancia
	private Vista28 miVista;
	private int [][] matrizAux; //por defecto está todo a 0
	
	/**
	 * Constructor
	 */
	public Controlador28(Vista28 v) {
		
		this.miVista = v;
		
		this.matrizAux = new int[this.miVista.getMatriz().length][this.miVista.getMatriz()[0].length];
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource() == this.miVista.getCargar()) { //se ha puslado Cargar
			
			porDefecto();			
			ArrayList<Integer> al = coordenadas();
			if(al!=null)
				asignaCoordenadas (al);			
		}
		
		
		if(arg0.getSource() == this.miVista.getSimular()) { //se ha pulsado Simular
			
			recorreMatrizBotones();
			
		}	
		
	}
	
	
	
	/**
	 * Método que obtiene las coordenadas y las devuelve en un ArrayList
	 */
	private ArrayList<Integer> coordenadas() {
		
		JFileChooser chooser = new JFileChooser(".\\filesJuego");
		chooser.showOpenDialog(this.miVista);
		chooser.setVisible(true);
		
		if(chooser.getSelectedFile()==null) //si pulsa cancelar
			return null;
		
		//En este punto ha elegido un archivo
		
		File f = chooser.getSelectedFile();
		
		ArrayList<Integer> lista = new ArrayList<Integer>(); //creo una Colección para guardar los números
		
		try (Scanner sc = new Scanner(f).useDelimiter(",|\\r\\n")) {			
			
			while(sc.hasNextInt()) {
				
				int num = sc.nextInt();
				
				lista.add(num);
			}			
		} 
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this.miVista, "Archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		catch(InputMismatchException e2) {
			JOptionPane.showMessageDialog(this.miVista, "Archivo incorrecto", "Error", JOptionPane.ERROR_MESSAGE);

			return null;
		}		
		
		
		
		return lista;
		
	}//end coordenadas
	
	
	/**
	 * Método que asigna las coordenadas
	 * @param al
	 */
	private void asignaCoordenadas(ArrayList<Integer> al) {
		
		Iterator<Integer> it = al.iterator();
		
		if(al.size()%2!=0) { //las coordenadas DEBEN ser pares
			JOptionPane.showMessageDialog(this.miVista, "Archivo incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			return;		
		}
			
		
		while (it.hasNext()) { //cada 2 será una coordenada
			
			int fil = it.next();
			int col = it.next();
			
			this.miVista.getMatriz()[fil][col].setBackground(Color.RED);
		}
		
	}
	
	
	/**
	 * Método que comprueba si hay alguien (está roja) en la coordenada que se le pasa
	 * (COPIADO DEL VÍDEO)
	 */
	private boolean hayAlguien(int fil, int col) {		
		
		try {
			
			if(this.miVista.getMatriz()[fil][col].getBackground().equals(Color.RED))
				return true;			
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
		
		return false;
	}
	
	
	
	/**
	 * Método que recorre la matriz de botones y pregunta por cada una de las
	 * celdas que tiene alrededor la celda "actual"
	 */
	private void recorreMatrizBotones() {
		
		boolean sw1, sw2, sw3, sw4, sw5, sw6, sw7, sw8;
		
		for(int f=0; f<this.miVista.getMatriz().length; f++) {
			
			for(int c=0; c<this.miVista.getMatriz()[f].length; c++) {
				
				sw1= hayAlguien(f-1, c-1);
				sw2= hayAlguien(f-1, c);
				sw3= hayAlguien(f-1, c+1);
				sw4= hayAlguien(f, c-1);
				sw5= hayAlguien(f, c+1);
				sw6= hayAlguien(f+1, c-1);
				sw7= hayAlguien(f+1, c);
				sw8= hayAlguien(f+1, c+1);
				
				int vecinos = cuentaVecinos(sw1, sw2, sw3, sw4, sw5, sw6, sw7, sw8);
				
				reviveOmuere(vecinos, f, c);				
			}			
		}		
		resuelve();
	}
	
	
	/**
	 * Método que cuanta los vecinos que tiene la celda "actual"
	 */
	private int cuentaVecinos (boolean sw1, boolean sw2, boolean sw3, boolean sw4, boolean sw5, boolean sw6, boolean sw7, boolean sw8) {
		
		int contador=0;
		boolean [] arrayBoolean = new boolean[8];
		
		arrayBoolean[0] = sw1;
		arrayBoolean[1] = sw2;
		arrayBoolean[2] = sw3;
		arrayBoolean[3] = sw4;
		arrayBoolean[4] = sw5;
		arrayBoolean[5] = sw6;
		arrayBoolean[6] = sw7;
		arrayBoolean[7] = sw8;
		
		for(int i=0; i<arrayBoolean.length; i++) {
			
			if(arrayBoolean[i]) //si es true lo cuenta
				contador++;			
		}		
		
		return contador; //devuelvo el número de true (vecinos)		
	}
	
	
	/**
	 * Método que vuelca los datos en una matriz int[][] (variable de instancia)
	 * 
	 * 1 --> REVIVE
	 * 2 --> MUERE	 
	 */
	private void reviveOmuere(int vecinos, int fil, int col) {		
		
		Color c= this.miVista.getMatriz()[fil][col].getBackground();		
		
		if(c.equals(Color.RED)) { //es roja (VIVA)			
			
			if(vecinos<=1 || vecinos>=4) //tiene 1 o menos O tiene 4 o más
				this.matrizAux[fil][col]= 2; //MUERE
					
		}
		else { //es color por defecto (MUERTA)
			
			if(vecinos==3) //tiene 3 REVIVE
				this.matrizAux[fil][col]= 1;			
		}		
		
	}
	
	
	
	/**
	 * Método que recorre la matrizAux y cambia el color en la correspondiente de la matrizBotones
	 */
	private void resuelve() {		
		
		for(int f=0; f<this.matrizAux.length; f++) {
			
			for(int c=0; c<this.matrizAux[f].length; c++) {				
				
				if(matrizAux[f][c]==2)
					this.miVista.getMatriz()[f][c].setBackground(UIManager.getColor("Button.background")); //muere
				
				if(matrizAux[f][c]==1)
					this.miVista.getMatriz()[f][c].setBackground(Color.RED); //revive				
			}
		}		
	}
	
	
	
	/**
	 * Método que deja todo por defecto. Así cada vez que se pulsa cargar, se "reinicia" 
	 * tanto la matriz de los botones como la matrizAuxiliar
	 */
	private void porDefecto() {
		
		for(int f=0; f<matrizAux.length; f++) {
			
			for(int c=0; c<matrizAux[f].length; c++) {			
				
				this.miVista.getMatriz()[f][c].setBackground(UIManager.getColor("Button.background"));
				matrizAux[f][c]=0;				
			}
		}		
	}
	
	
	
	
	
	
	
	

}// end Controlador28
