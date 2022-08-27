package practica_28;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Raúl Carneros Rodríguez
 *
 */
public class Vista28 extends JPanel {

	private static final long serialVersionUID = -8261338752356200073L;
	
	
	//Variables de instancia
	private JButton [][] matriz;
	private JButton cargar, simular;
	
	/**
	 * Constructor
	 */
	public Vista28() {
		
		this.setLayout(new BorderLayout());
		
	//	JPanel pPrincipal = new JPanel(new BorderLayout());
		
		//Panel del título
		JPanel titulo = titulo();
			
		//Panel del centro
		JPanel pCentro = preparaPanelCentro();
		
		//Panel del sur
		JPanel pSur = preparaPanelSur();
		
		
		
	//	pPrincipal.add(titulo, BorderLayout.NORTH);
	//	pPrincipal.add(pCentro, BorderLayout.CENTER);
	//	pPrincipal.add(pSur, BorderLayout.SOUTH);
		
		
	//	this.add(pPrincipal);
		
		this.add(titulo, BorderLayout.NORTH);
		this.add(pCentro, BorderLayout.CENTER);
		this.add(pSur, BorderLayout.SOUTH);
		
		
	}//end contructor
	
	
	//GETTERS
	public JButton[][] getMatriz() {return matriz;}
	public JButton getCargar() {return cargar;}
	public JButton getSimular() {return simular;}



	/**
	 * Método que prepara el panel del título
	 * @return
	 */
	private JPanel titulo() {
		
		JPanel p = new JPanel();
		
		JLabel titulo = new JLabel("JUEGO DE LA VIDA");
		
		Font fuente = new Font("Comic Sans MS", Font.BOLD, 25);
		
		titulo.setForeground(Color.RED);
		
		titulo.setFont(fuente);
		
		p.add(titulo);
		
		return p;
		
	}
	

	
	/**
	 * Método que prepara el panel con la matriz de JButton
	 * @param matriz
	 * @return
	 */
	private JPanel preparaPanelCentro() {
		
		this.matriz = new JButton[20][20];
		
		//********************
		//RELLENO LA MATRIZ CON JBUTTON
		//********************
		for(int f=0; f<matriz.length; f++) {

			for(int c=0; c<matriz[f].length; c++) {

				matriz[f][c]= new JButton();
			}
		}
				
			
		JPanel p = new JPanel(new GridLayout(20, 20));
		
		//********************
		//PONGO LOS JBUTTON EN EL PANEL GRIDLAYOUT
		//********************	
		
		for (int f= 0; f<matriz.length; f++) {
			
			for(int c=0; c<matriz[f].length; c++) {
				
				JButton b = matriz[f][c];
				
				p.add(b);				
			}			
		}	
		
		p.setBorder(new EmptyBorder(10,10,10,10));
		
		
		
		return p;
	}
	
	
	/**
	 * Método que prepara el panel del sur
	 * @return
	 */
	private JPanel preparaPanelSur() {
		
		JPanel p = new JPanel();
		
		this.cargar = new JButton("Cargar patrón");
		this.simular = new JButton("Simular juego");
		
		p.add(cargar);
		p.add(simular);
		
		return p;	
		
	}
	
	
	/**
	 * Método que añade control a los botones
	 */
	public void añadeControl(Controlador28 ctr) {
		
		this.cargar.addActionListener(ctr);
		this.simular.addActionListener(ctr);
		
	}
	
	
	
	
	
	
	
}
