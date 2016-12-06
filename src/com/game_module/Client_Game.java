// @ref Joseph Anthony C. Hermocilla
package com.game_module;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client_Game extends JPanel implements Runnable, Game_Constants{
  int x=10,y=10,xspeed=2,yspeed=2,prevX,prevY;
  Thread t=new Thread(this);
  String name="Almer";
  String pname;
  String server="localhost";
  boolean connected=false;
  DatagramSocket socket = new DatagramSocket();
  String serverData;
  BufferedImage offscreen;
  int gwidth  = 1000;
  int gheight = 500; 

  public Client_Game (String server,String name) throws Exception{
    this.server=server;
    this.name=name;

    socket.setSoTimeout(100);

    this.setPreferredSize(new Dimension(gwidth, gheight));
    this.addKeyListener(new KeyHandler());
    this.addMouseMotionListener(new MouseMotionHandler());

    t.start();
  }

  public void send(String msg){
    try{
      byte[] buf = msg.getBytes();
        InetAddress address = InetAddress.getByName(server);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        socket.send(packet);
      } catch (Exception e) {
        // do nothing
      }
  }

  public void run(){
    while(true){
      try{
        Thread.sleep(1);
      }catch(Exception ioe){}

      byte[] buf = new byte[256];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try{
        socket.receive(packet);
      }catch(Exception ioe){
        // do nothing
      }

      serverData=new String(buf);
      serverData=serverData.trim();

      offscreen = (BufferedImage) this.createImage(gwidth, gheight);
      if (!connected && serverData.startsWith("CONNECTED")) {
        connected=true;
        System.out.println("Connected.");
      } else if (!connected) {
        System.out.println("Connecting..");
        send("CONNECT "+name);
      } else if (connected) {
        offscreen.getGraphics().clearRect(0, 0, gwidth, gheight);
        if (serverData.startsWith("PLAYER")) {
          String[] playersInfo = serverData.split(":");
          for (int i=0;i<playersInfo.length;i++) {
            String[] playerInfo = playersInfo[i].split(" ");
            String pname =playerInfo[1];
            int x = Integer.parseInt(playerInfo[2]);
            int y = Integer.parseInt(playerInfo[3]);
            int shapex = Integer.parseInt(playerInfo[4]);
            int shapey = Integer.parseInt(playerInfo[5]);

            offscreen.getGraphics().fillOval(x, y, 20, 20);
            offscreen.getGraphics().fillRect(shapex, shapey, 20, 20);
            offscreen.getGraphics().setColor(Color.BLUE);
            offscreen.getGraphics().drawString(pname,x-10,y+30);
          }
          this.repaint();
        }
      }
    }
  }

  public void paintComponent(Graphics g){
    g.drawImage(offscreen, 0, 0, null);
  }

  class MouseMotionHandler extends MouseMotionAdapter{
    public void mouseMoved(MouseEvent me){
      x=me.getX();y=me.getY();
      if (prevX != x || prevY != y){
        send("PLAYER "+name+" "+x+" "+y);
      }
    }
  }

  class KeyHandler extends KeyAdapter{
    public void keyPressed(KeyEvent ke){
      prevX=x;prevY=y;
      switch (ke.getKeyCode()){
        case KeyEvent.VK_DOWN:y+=yspeed;break;
        case KeyEvent.VK_UP:y-=yspeed;break;
        case KeyEvent.VK_LEFT:x-=xspeed;break;
        case KeyEvent.VK_RIGHT:x+=xspeed;break;
      }
      if (prevX != x || prevY != y){
        send("PLAYER "+name+" "+x+" "+y);
      }
    }
  }
}
