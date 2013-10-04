package Applet1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Applet1.Proyectil.PanelDibujo;

public class Proyectil extends JApplet implements Runnable {

    private PanelDibujo panelDibujo;
    private PanelGraficas panelGraficas;
    private PanelBotones panelBotones;
    private PanelVariables panelVariables;
    private double z;
    private double posY;
    private double z0; 
    private double vz0; 
    private double x;
    private double posX;
    private double x0;
    private double vx0; 
    private double velocidadInicial;
    private double vfY;
    private double vf;
    private double tiempo;
    private boolean puedoDisparar = true;
    private double angulo;
    private int t;
    
    private MiCanvas canvaVT;
    private MiCanvas canvaPT;
    private MiCanvas canvaAT;

    
    public void init() {

        setSize(1200, 600);
        panelDibujo = new PanelDibujo();
        panelBotones = new PanelBotones();
        panelVariables = new PanelVariables();
        panelGraficas = new PanelGraficas();
        canvaVT = new MiCanvas();
        canvaPT = new MiCanvas();
        canvaAT = new MiCanvas();
        canvaVT.setPos(860,0);
        canvaVT.setEtiquetas("T(s)","V(m/s)");
        canvaPT.setPos(860,190);
        canvaPT.setEtiquetas("T(s)","P(m)");
        canvaAT.setPos(860,380);
        canvaAT.setEtiquetas("T(s)","A(m/s2)");
        setLayout(new BorderLayout());
        add(BorderLayout.SOUTH ,panelVariables);
        add(BorderLayout.CENTER, canvaVT);
        add(BorderLayout.CENTER, canvaPT);
        add(BorderLayout.CENTER, canvaAT);
        add(BorderLayout.CENTER, panelDibujo);
        add(BorderLayout.NORTH,panelBotones);
        iniciarVariables();
        
    }

    public void iniciarVariables() {
        z = 480;
        z0 = 0;
        posY = 0;
        vz0 = velocidadInicial * Math.sin(Math.toRadians(angulo));
        vx0 = velocidadInicial * Math.cos(Math.toRadians(angulo));
        x = 20;
        posX = 0;
        x0 = 0;
        tiempo = 0;
    }

    public void disparar() {
        if (puedoDisparar) {
            new Thread(this).start();
        }
    }

    public void update() {
        
        double incrementoTiempo = 0.02;
        tiempo += incrementoTiempo;
        
        x = vx0 *Math.cos(Math.toRadians(angulo))* tiempo;
       
        x = x + 20;
        posX = x-20;

        double g = -9.81;

        
        z =  vz0 * Math.sin(Math.toRadians(angulo)) * tiempo + 0.5 * g * tiempo * tiempo;
        z = 480 - z;
        vfY = vz0 + g*tiempo;
        t = (int) ((vfY-velocidadInicial*Math.sin(Math.toRadians(angulo)))/g);
        
        //System.out.println(t);
        
        vf = Math.sqrt(vfY*vfY + vx0*vx0);
        
        posY = 500-z;

    }

    public void run() {

        puedoDisparar = false;
        
        
        
        while (z < 500) {
            try {

                update();
                Thread.sleep(20);
                panelDibujo.repaint();
                panelVariables.actualizar();
                canvaAT.setParam(t,9.8);
                canvaAT.dibujaFuncion();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
        //Reinicia las varibles y activa el disparo
        iniciarVariables();
        panelDibujo.repaint();
        puedoDisparar = true;
    }

    class PanelDibujo extends JPanel {


		public PanelDibujo() {
            setSize(800, 600);
            setDoubleBuffered(true);
        }

      
        public void paint(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, 850, 600);
            g.setColor(Color.blue);
            g.drawLine(0, 500, 800, 500);
            g.setColor(Color.red);
            g.fillOval((int) x, (int) z, 20, 20);
        }
    }
    
    class PanelGraficas extends JPanel{
    	public PanelGraficas(){
    		setSize(200,600);
    		setDoubleBuffered(true);
    		
    	}
    	void dibujaEjes(Graphics g){
    	     FontMetrics fm=g.getFontMetrics();
    	     int descent=fm.getDescent();
    	     int charAlto=fm.getHeight();
    	     int charAncho=fm.stringWidth("0");
    	}
    	
    	public void paint(Graphics g) {
    		int orgY, orgX;
            g.setColor(Color.white);
            g.fillRect(810, 4, 200, 600);
            
            
            
        }
    }
    
    class PanelVariables extends JPanel{
    	JLabel labelVelocidad;
    	JTextField textVelocidad;
        JLabel labelVelocidadX;
        JTextField textVelocidadX;
        JLabel labelVelocidadY;
        JTextField textVelocidadY;
        JLabel labelPosicionX;
        JTextField textPosicionX;
        JLabel labelPosicionY;
        JTextField textPosicionY;
        JLabel labelTiempo;
        JTextField textTiempo;
        
    	public PanelVariables() {
            setSize(150, 500);
            setDoubleBuffered(true);
            labelTiempo = new JLabel("Tiempo (s): ");
        	labelVelocidad = new JLabel("Velocidad (m/s): ");
            labelVelocidadX = new JLabel("Velocidad en X (m/s): ");
            labelVelocidadY = new JLabel("Velocidad en Y (m/s)");
            labelPosicionX = new JLabel("Posicion en X (m)");
            labelPosicionY = new JLabel("Posicion en Y (m)");
            textTiempo = new JTextField("    "+String.valueOf(t)+"   ");
            textVelocidad = new JTextField("   "+String.valueOf(velocidadInicial)+"   ");
            textVelocidadX = new JTextField("   "+String.valueOf(vx0)+"   ");
            textVelocidadY = new JTextField("   "+String.valueOf(vz0)+"   ");
            textPosicionX = new JTextField("   "+String.valueOf(x0)+"   ");
            textPosicionY = new JTextField("   "+String.valueOf(z0)+"   ");
            
            add(labelVelocidad);
            add(textVelocidad);
            add(labelVelocidadX);
            add(textVelocidadX);
            add(labelVelocidadY);
            add(textVelocidadY);
            add(labelPosicionX);
            add(textPosicionX);
            add(labelPosicionY);
            add(textPosicionY);
            add(labelTiempo);
            add(textTiempo);
            
        }
    	
    	public void actualizar()
    	{
    		textVelocidadX.setText(String.valueOf((int)vx0));
    		textVelocidad.setText(String.valueOf((int)vf));
    		textVelocidadY.setText(String.valueOf((int)vfY));
    		textPosicionX.setText( String.valueOf((int)posX));
    		textPosicionY.setText( String.valueOf((int)posY));
    		textTiempo.setText(String.valueOf(t));
    	}

    }

    class PanelBotones extends JPanel implements ActionListener {

        JButton boton;
        JTextField textFieldVelocidad;
        JTextField textFieldAngulo;
        JLabel labelVelocidad;
        JLabel labelAngulo;

        public PanelBotones() {

            setSize(400, 50);
            boton = new JButton("disparar");
            labelAngulo = new JLabel("ángulo (degress) ");
            textFieldVelocidad = new JTextField("50 ");
            textFieldVelocidad.setColumns(3);
            textFieldAngulo = new JTextField("45");
            labelVelocidad = new JLabel("Velocidad (m/s) ");
            
            add(boton);
            add(labelAngulo);
            add(textFieldAngulo);
            add(labelVelocidad);
            add(textFieldVelocidad);

            boton.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            velocidadInicial = Double.valueOf(textFieldVelocidad.getText());
            angulo = Integer.valueOf(textFieldAngulo.getText());
            iniciarVariables();
            disparar();
            
        }
    }
}
