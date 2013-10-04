package Applet1;

import java.awt.*;
public class MiCanvas extends Canvas {
//anchura y altura del canvas
     int anchoCanvas, altoCanvas;
//origenes
     int orgY, orgX;
//escalas
     double escalaX, escalaY;
//masas de las moléculas en u.m.a
     String eX,eY;
     double a,b;
     int x = 810,y = 0;
//número de partículas
     final int N=10000;
//colores de las funciones
     int nGrafica=-1;
     static final Color color = new Color(128, 0, 64);

  public MiCanvas() {
     setBackground(Color.white);
     setSize(200,200);
  }
  void setEtiquetas(String ex, String ey )
  {
	  eX =ex;
	  eY =ey;
  }
  
  void setNuevo(int a , int b){
     nGrafica++;
     if(nGrafica>13){
          nGrafica=0;
     }
     this.a =a;
     this.b = b;
     dibujaFuncion();
  }

  void origen(Graphics g){
     anchoCanvas=180;
     altoCanvas=180;
     FontMetrics fm=g.getFontMetrics();
     int charAlto=fm.getHeight();
     int charAncho=fm.stringWidth("0");
//orígenes
     orgX=5*charAncho;
     orgY=altoCanvas-2*charAlto;
//escalas
     escalaX=(double)(anchoCanvas-orgX-3*charAncho)/30;
     escalaY=(double)(altoCanvas-3*charAlto)/0.2;
  }

  void dibujaEjes(Graphics g){
     FontMetrics fm=g.getFontMetrics();
     int descent=fm.getDescent();
     int charAlto=fm.getHeight();
     int charAncho=fm.stringWidth("0");
//borra el canvas
     g.setColor(getBackground());
     g.fillRect(0,0, anchoCanvas, altoCanvas);
     g.setColor(Color.black);
//eje horizontal
     g.drawLine(orgX-charAncho, orgY, anchoCanvas, orgY);
     g.drawString(eX, anchoCanvas-4*charAncho, orgY);
     int x1, y1;
     /*for(int i=0; i<=3; i++){
          x1=orgX+(int)(1000*i*escalaX);
          g.drawLine(x1, orgY+charAncho/2, x1, orgY-charAncho/2);
          String str=String.valueOf(i*1000);
          g.drawString(str, x1-fm.stringWidth(str)/2, orgY+charAlto);
          if(i==3) break;
          for(int j=1; j<5; j++){
               x1=orgX+(int)((1000*i+(double)(1000*j)/5)*escalaX);
               g.drawLine(x1, orgY+charAncho/4, x1, orgY-charAncho/4);
          }
     }*/

//eje vertical
     g.drawLine(orgX, 0, orgX, altoCanvas-charAlto);
     g.drawString(eY, orgX+charAncho, charAlto);
     /*for(int i=0; i<=20; i+=5){
          y1=orgY-(int)(i*escalaY);
          g.drawLine(orgX+charAncho/2, y1, orgX-charAncho/2, y1);
          String str=String.valueOf(i);
          g.drawString(str, orgX-fm.stringWidth(str)-charAncho/2, y1+charAlto/2-descent);
          if(i==20) break;
          /*for(int j=1; j<5; j++){
               y1=orgY-(int)((i+(double)(j))*escalaY);
               g.drawLine(orgX+charAncho/4, y1, orgX-charAncho/4, y1);
          }
     }*/
  }


  void dibujaFuncion(){
     Graphics g=getGraphics();
     int x1=orgX, y1=orgY, x2, y2;
     g.setColor(color);

     for(double v=0; v<30; v+=1){
               y2=orgY-(int)(a*escalaY);
               x2=orgX+(int)(b*escalaX);
               g.drawLine(x1, y1, x2, y2);
               x1=x2; y1=y2;
     }
     g.dispose();
  }
  
 public void setPos(int x,int y)
 {
	 this.x = x;
	 this.y = y;
	 
 }
 
 public void setParam(double a , double b)
 {
	 this.a = a;
	 this.b = b;
 }
 public void paint(Graphics g){
     origen(g);
     dibujaEjes(g);
     setSize(220,180);
     g.setColor(Color.blue);
	setLocation(x,y);
     //g.fillRect(800, 0, 200, 200);
 }
}