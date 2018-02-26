package es.studium.BlocNotas;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class BlocNotas extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	//nuevo comentario
	
	//Subir este comentario al repositorio online
	
	MenuBar barraMenu = new MenuBar();

	
	Menu archivo = new Menu("Archivo");
	Menu gestion = new Menu("Gestión");

	
	MenuItem archivoNuevo = new MenuItem("Nuevo");
	MenuItem archivoAbrir = new MenuItem("Abrir");
	MenuItem archivoGuardar = new MenuItem("Guardar");
	MenuItem archivoSalir = new MenuItem("Salir");

	
	MenuItem gestionContarPalabras = new MenuItem("Contar palabras");
	MenuItem gestionContarLetras = new MenuItem("Contar letras");
	MenuItem gestionContarVocales = new MenuItem("Contar vocales");

	
	TextArea ta = new TextArea(20,60);

	
	Dialog d = new Dialog(this, "", true);

	
	Label lblDialogo = new Label();

	public BlocNotas()
	{
		
		setTitle("Bloc de Notas");

		
		setMenuBar(barraMenu);

		
		archivo.add(archivoNuevo);
		archivo.add(archivoAbrir);
		archivo.add(archivoGuardar);
		archivo.addSeparator();
		archivo.add(archivoSalir);

		
		gestion.add(gestionContarPalabras);
		gestion.add(gestionContarLetras);
		gestion.add(gestionContarVocales);

		
		barraMenu.add(archivo);
		barraMenu.add(gestion);

		
		addWindowListener(this);

		
		d.addWindowListener(this);

		
		archivoNuevo.addActionListener(this);
		archivoAbrir.addActionListener(this);
		archivoGuardar.addActionListener(this);
		archivoSalir.addActionListener(this);

		gestionContarPalabras.addActionListener(this);
		gestionContarLetras.addActionListener(this);
		gestionContarVocales.addActionListener(this);

		
		this.add(ta);

		setSize(450,400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String[] args)
	{
		new BlocNotas();
	}

	public void actionPerformed(ActionEvent ae) 
	{
		Object a;
		a = ae.getSource();
		
		if(a.equals(archivoNuevo))
		{
			
			ta.selectAll();
			ta.setText("");
		}
		
		else if(a.equals(archivoAbrir))
		{
			
			FileDialog fd = new FileDialog(this, "Seleccionar archivo", FileDialog.LOAD);
			
			fd.setFile("*.txt");
			
			fd.setVisible(true);
			
			String filename = fd.getDirectory()+fd.getFile();

			
			if (filename == "")
			{
				
				d.setLayout(new FlowLayout());
				d.setTitle("Archivo No encontrado");
				d.addWindowListener(this);
				lblDialogo.setText("Archivo No encontrado");            
				d.add(lblDialogo);
				d.setLocationRelativeTo(null);
				d.setSize(250,150);
				d.setVisible(true);
			}
			else
			{
				try
				{
					
					BufferedReader entrada = new BufferedReader(new FileReader(filename));
					String s;
					
					ta.selectAll();
					ta.setText("");
					
					while((s=entrada.readLine())!=null)
					{
						
						ta.append(s);
						
						ta.append("\n");
					}
				
					entrada.close();
				}
				catch(IOException i)
				{
					
					d.setLayout(new FlowLayout());
					d.setTitle("Archivo No encontrado");
					d.addWindowListener(this);
					lblDialogo.setText("Archivo No encontrado");            
					d.add(lblDialogo);
					d.setLocationRelativeTo(null);
					d.setSize(250,150);
					d.setVisible(true);
				}
			}
		}
		else if(a.equals(archivoGuardar))
		{
			try
			{
				
				FileDialog fd = new FileDialog(this, "Seleccionar archivo", FileDialog.SAVE);
				
				fd.setFile("*.txt");
				
				fd.setVisible(true);
				
				String filename = fd.getDirectory()+fd.getFile();
				
				BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
				
				PrintWriter salida = new PrintWriter(bw);
				
				salida.println(ta.getText());
				
				bw.close();
				salida.close();

				
				lblDialogo.setText("Guardado archivo " + fd.getFile());

				
				d.setLayout(new FlowLayout());
				d.setTitle("Archivo guardado");
				lblDialogo.setText("Archivo guardado");    
				d.add(lblDialogo);
				d.setLocationRelativeTo(null);
				d.setSize(250,150);
				d.setVisible(true);
			}
			catch(IOException i)
			{
				
				d.setLayout(new FlowLayout());
				d.setTitle("Error al guardar Archivo");
				lblDialogo.setText("Error al guardar Archivo");            
				d.add(lblDialogo);
				d.setLocationRelativeTo(null);
				d.setSize(250,150);
				d.setVisible(true);
			}            
		}
		else if(a.equals(archivoSalir))
		{
			System.exit(0);
		}
		else if(a.equals(gestionContarPalabras))
		{
			
			String miCadena = ta.getText();

			
			StringTokenizer auxiliar1 = new StringTokenizer(miCadena, " ");
			StringTokenizer auxiliar2 = new StringTokenizer(miCadena, "\n");
			int numPalabras = auxiliar1.countTokens() + auxiliar2.countTokens();
			
			if(numPalabras!=0)
			{
				numPalabras--;
			}

			
			lblDialogo.setText("Hay " + numPalabras + " palabras en el texto.");

			
			d.setLayout(new FlowLayout());
			d.setTitle("Contador de Palabras");            
			d.add(lblDialogo);
			d.setSize(200,150);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}
		else if(a.equals(gestionContarLetras))
		{
			int numLetras = 0;
			char c = 0;

			
			String texto = ta.getText();

			
			for(int i=0; i<texto.length();i++)
			{
				c = texto.charAt(i);

				if(Character.isLetter(c))
					numLetras++;
			}
			
			lblDialogo.setText("Hay " + numLetras + " letras en el texto.");

			
			d.setLayout(new FlowLayout());
			d.setTitle("Contador de Letras");
			d.add(lblDialogo);
			d.setSize(200,150);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}
		else 
		{
			int numVocales = 0;
			char c = 0;

			
			String texto = ta.getText();

			for(int i=0; i<texto.length();i++)
			{
				
				c = texto.charAt(i);

				if(Character.isLetter(c))
				{
					
					if ((c=='a')||(c=='e')||(c=='i')||(c=='o')||(c=='u'))
					{
						numVocales++;
					}
					
					else if ((c=='á')||(c=='é')||(c=='í')||(c=='ó')||(c=='ú'))
					{
						numVocales++;
					}
					
					else if ((c=='A')||(c=='E')||(c=='I')||(c=='O')||(c=='U'))
					{
						numVocales++;
					}
				}
			}

			
			lblDialogo.setText("Hay " + numVocales + " vocales en el texto.");

			
			d.setLayout(new FlowLayout());
			d.setTitle("Contador de Vocales");
			d.add(lblDialogo);
			d.setSize(200,150);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		}        
	}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) 
	{
		
		if(d.hasFocus())
		{
			
			d.setVisible(false);
		}
		
		else
		{
			System.exit(0);
		}
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}