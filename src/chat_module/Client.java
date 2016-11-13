public class Client{
    public static void main (String [] args) { 	
        try {
        	Chat chatClient = new Chat();
        	chatClient.setVisible(true);
        }
        catch(ArrayIndexOutOfBoundsException e) { e.printStackTrace(); }
    }	
}
