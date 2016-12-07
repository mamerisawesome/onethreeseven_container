// @ref Joseph Anthony C. Hermocilla
package com.game_module;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.project.Screen;

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
  String[] playersInfo;
  
  JPanel mainCont;
  
  static boolean CONNECTED_FLAG = false;
  static int TOTAL_TIME = 5;
  static int TIME_COUNTER = TOTAL_TIME;
  static TimerTask TIMER_TASK = new TimerTask () {
	public void run () {
		TIME_COUNTER -= 1;
	}
  };
  static Timer TIMER = new Timer("Game Timer");
  
  public Client_Game (String server,final String name, JPanel mainCont) throws Exception{
    this.server=server;
    this.name=name;
    this.mainCont = mainCont;

    socket.setSoTimeout(100);

    this.setPreferredSize(new Dimension(gwidth, gheight));
    
    t.start();
    this.addMouseMotionListener(new MouseMotionHandler());
    addMouseListener(new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
          for(int i = 0 ; i < r.length; i++){
        		if(r[i].contains(e.getX(),e.getY()) && colors[i]==playerColor){
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

      byte[] buf = new byte[1024];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      try{
        socket.receive(packet);
      }catch(Exception ioe){
        // do nothing
      }

      serverData=new String(buf);
      serverData=serverData.trim();

      Graphics2D g = offscreen.createGraphics();
      if (!connected && serverData.startsWith("CONNECTED")) {
        connected=true;
        System.out.println("Connected.");
      }
      else if (!connected) {
        System.out.println("Connecting..");
        send("CONNECT "+name);
      } else if (connected) {  
    	offscreen.getGraphics().clearRect(0, 0, gwidth, gheight);
        if (serverData.startsWith("PLAYER")) {
          playersInfo = serverData.split(":");
          if(!CONNECTED_FLAG){
            	CONNECTED_FLAG = true;
            	TIMER.scheduleAtFixedRate(TIMER_TASK, 0, TOTAL_TIME * 1000);
          }
          for (int i=0;i<playersInfo.length;i++) {
        	  System.out.println(playersInfo[i]);
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
            		System.out.println(z);
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
            
            int offsetx = 45;
            int offsety = 30;
            offscreen.getGraphics().drawString("Timer: ", 50-offsetx, 50-offsety);
            
            float timePercent = (((float)(TIME_COUNTER+1)/TOTAL_TIME) * 100);
            offscreen.getGraphics().drawString((int)timePercent+" % time remaining", 50-offsetx, 70-offsety);
            g.setColor(Color.GREEN);
            g.fillRect(95-offsetx, 41-offsety, TIME_COUNTER * 20, 10);
            
            int highest = -1;
            int h_index = 0;
            for (int ii=0;ii<playersInfo.length;ii++) {
            	String[] pinf = playersInfo[ii].split(" ");
            	int ps = Integer.parseInt(pinf[5]);
            	
            	if (ps > highest) {
            		highest = ps;
            		h_index = ii;
            	}
            }
            if (highest > -1) offscreen.getGraphics().drawString("Highest score: "+highest, 860, 480);
            else offscreen.getGraphics().drawString("Highest score: n/a", 860, 480);
            g.setColor(Color.GREEN);
            g.fillRect(95-offsetx, 41-offsety, TIME_COUNTER * 20, 10);
            
            this.repaint();
          }
        }
        
      }
      
      // check if end of counter
      if (TIME_COUNTER <= 0) {
    	  TIMER.cancel();
    	  offscreen.getGraphics().clearRect(0, 0, gwidth, gheight);
    	  
    	  mainCont.removeAll();
    	  mainCont.revalidate();
    	  mainCont.repaint();
    	  
    	  System.out.println("THIS IS THE END");
    	  int playerIndex = 1;
    	  mainCont.setLayout(new BorderLayout());
    	  
    	  String scoreboard = "";
    	  
    	  scoreboard += ""+
    			  "<html>"+
    			  "<head>"+
    			  "</head>"+
    			  "<body>"+
	    			  "<div style='width: 900px;text-align: center;'>"+
	    			  "<div style='display: inline-block;'>"+
	    			  	"<h1 style='font-size:25px; width: 900px;text-align: center;'>TOP SCORERS</h1"+
	    			  	"<br>"+
	    			  	"<br>";
    	  
    	  scoreboard += ""+
				  "<table class='table' style='font-size:10px;'>"+
				  	"<thead>"+
				  		"<tr>"+
				  			"<th>NAME</th>"+
				  			"<th>SCORE</th>"+
				  		"</tr>"+
				  	"</thead>"+
				  	"<tbody>";
    	  
    	  int [][] arr = new int[playersInfo.length][2];
    	  for (int jj = 0; jj < playersInfo.length; jj += 1) {
    		  arr[jj][0] = jj;
    		  arr[jj][1] = Integer.parseInt(playersInfo[jj].split(" ")[5]);
    	  }
    	  
    	  java.util.Arrays.sort(arr, new java.util.Comparator<int[]>() {
    		    public int compare(int[] a, int[] b) {
    		        return Double.compare(a[1], b[1]);
    		    }
    		});
    	  
    	  for(int kk = 0; kk < playersInfo.length; kk += 1){
    		  String name = playersInfo[arr[kk][0]].split(" ")[1];
    		  String score = arr[kk][1] + "";
    		  
    		  scoreboard += ""+
    				  "<tr>"+
    				  	"<td>" + name + "</td>"+
    				  	"<td>" + score + "</td>"+
    				  "</tr>";
    	  }
    	  
    	  scoreboard += ""+
    			  	"</tbody>"+
				  "</table>";
    	  
    	  scoreboard += ""+
    			  "</div>"+
    			  "</div>"+
    		"</body></html>";
    	  
    	  mainCont.add(new JLabel(scoreboard), BorderLayout.NORTH);
    	  mainCont.revalidate();
    	  mainCont.repaint();
    	  
    	  final JFrame frame = (JFrame)SwingUtilities.getRoot(mainCont);
    	  frame.revalidate();
    	  frame.repaint();
    	  
          break;
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
