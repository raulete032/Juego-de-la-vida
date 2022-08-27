package practica_28;

import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * 
 * @author Raúl Carneros Rodríguez
 *
 */
public class JuegoVidaMain28 {

	public static void main(String[] args) {

		
		
		JFrame ventana = new JFrame("Juego de la vida");

		Vista28 miVista = new Vista28();
		Controlador28 ctr = new Controlador28(miVista);
		
		miVista.añadeControl(ctr);
		
		
		ventana.setContentPane(miVista);
		ventana.setVisible(true);	
		ventana.setPreferredSize(new Dimension(500,550));
		ventana.pack();		
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		
	}

}
