// @ref Joseph Anthony C. Hermocilla
package com.game_module;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
  int gwidth  = 1000;
  int gheight = 500;
  BufferedImage offscreen = new BufferedImage(gwidth,gheight,BufferedImage.TYPE_INT_RGB);
  Rectangle[] r = new Rectangle[10];
  int[] colors = new int[10];
  int playerColor;
  int score;
  int counter = 0;
  
  static boolean CONNECTED_FLAG = false;
  static int TOTAL_TIME = 5;
  static int TIME_COUNTER = 0;
  static TimerTask TIMER_TASK = new TimerTask () {
	public void run () {
		TIME_COUNTER += 1;
	} 
  };
  static Timer TIMER = new Timer("Game Timer");
  
  public Client_Game (String server,final String name) throws Exception{
    this.server=server;
    this.name=name;

    socket.setSoTimeout(100);

    this.setPreferredSize(new Dimension(gwidth, gheight));
    
    t.start();
    this.addMouseMotionListener(new MouseMotionHandler());
    addMouseListener(new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
          for(int i = 0 ; i < r.length; i++){
        		if(r[i].contains(e.getX(),e.getY()) && colors[i]==playerColor){
        			//score++;
        			//System.out.println("HIT HIT MOTHER FUCKER " + (score++));
        			Random rand = new Random();
        			int newX = rand.nextInt(1000);
        			int newY = rand.nextInt(500);
        			send("PLAYER "+name+" "+x+" "+y+" "+(score+1)+" "+i+"/"+newX+"/"+newY);
        		}
          }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
    });
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

      Graphics2D g = offscreen.createGraphics();
      //offscreen = (BufferedImage) this.createImage(gwidth,gheight);
      if (!connected && serverData.startsWith("CONNECTED")) {
        connected=true;
        System.out.println("Connected.");
      }
      else if (!connected) {
        System.out.println("Connecting..");
        send("CONNECT "+name);
      } else if (connected) {
        if(!CONNECTED_FLAG){
        	CONNECTED_FLAG = true;
        	TIMER.scheduleAtFixedRate(TIMER_TASK, 0, TOTAL_TIME * 1000);
        }
    	 offscreen.getGraphics().clearRect(0, 0, gwidth, gheight);
        if (serverData.startsWith("PLAYER")) {
          String[] playersInfo = serverData.split(":");
          for (int i=0;i<playersInfo.length;i++) {
            String[] playerInfo = playersInfo[i].split(" ");
            String pname =playerInfo[1];
            int x = Integer.parseInt(playerInfo[2]);
            int y = Integer.parseInt(playerInfo[3]);
            if(name.equals(pname)){
            	playerColor = Integer.parseInt(playerInfo[4]);
            	score = Integer.parseInt(playerInfo[5]);
            }
            int color = Integer.parseInt(playerInfo[4]);
            int pscore = Integer.parseInt(playerInfo[5]);
            String[] shapes = playerInfo[6].split("_");
            String[] temp = null;
            for(int z = 0 ; z < shapes.length; z++){
            	
            	temp = shapes[z].split("/");
            	colors[z] = Integer.parseInt(temp[0]);
            	if(counter<10){
                	System.out.println(shapes.length);
            		r[z] = new Rectangle(Integer.parseInt(temp[1]),Integer.parseInt(temp[2]),20,20);
            		counter++;
            	}else{
            		r[z].setBounds(Integer.parseInt(temp[1]),Integer.parseInt(temp[2]),20,20);
            	}
            	switch(Integer.parseInt(temp[0])){
    			case 0: g.setColor(Color.BLUE);  break;
    			case 1: g.setColor(Color.RED); break;
    			case 2: g.setColor(Color.ORANGE); break;
    			case 3: g.setColor(Color.GREEN); break;
    			case 4: g.setColor(Color.YELLOW); break;
    			case 5: g.setColor(Color.MAGENTA); break;
    			case 6: g.setColor(Color.ORANGE); break;
    			case 7: g.setColor(Color.PINK); break;
            	}
            	g.fillRect(Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), 20, 20);
            }

            //int shapex = Integer.parseInt(playerInfo[4]);
            //int shapey = Integer.parseInt(playerInfo[5]);
            //offscreen.getGraphics().fillRect(shapex, shapey, 20, 20);
            switch(color){
			case 0: g.setColor(Color.BLUE);  break;
			case 1: g.setColor(Color.RED); break;
			case 2: g.setColor(Color.ORANGE); break;
			case 3: g.setColor(Color.GREEN); break;
			case 4: g.setColor(Color.YELLOW); break;
			case 5: g.setColor(Color.MAGENTA); break;
			case 6: g.setColor(Color.ORANGE); break;
			case 7: g.setColor(Color.PINK); break;
        	}
            g.fillOval(x-10, y-10, 15, 15);
            offscreen.getGraphics().drawString(pname+" | "+Integer.toString(pscore),x-10,y+20);
            this.repaint();
          }
        }
      }
      
      // check if end of counter
      if (TIME_COUNTER >= TOTAL_TIME) {
    	  System.out.println("TIME ENDED!");
    	  TIMER.cancel();
    	  offscreen.getGraphics().clearRect(0, 0, gwidth, gheight);
          break;
      } else {
    	  System.out.print("TOTAL TIME: ");
    	  System.out.println(TOTAL_TIME);
    	  System.out.println("TIME IS RUNNING BITCHES!");
    	  System.out.println(TIME_COUNTER);
      }
	}
  }

  
  public void paintComponent(Graphics g){
	super.paintComponent(g);
    g.drawImage(offscreen, 0, 0, Color.WHITE, null);
  }

  class MouseMotionHandler extends MouseMotionAdapter{
    public void mouseMoved(MouseEvent me){
      x=me.getX();y=me.getY();
      if (prevX != x || prevY != y){
        send("PLAYER "+name+" "+x+" "+y+" "+score);
      }
    }
  }

}
