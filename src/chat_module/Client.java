public class Client{
    public static void main (String [] args) { 	
        try {
        	Client_Chat chatClient = new Client_Chat();
        	chatClient.setVisible(true);
        }
        catch(ArrayIndexOutOfBoundsException e) { e.printStackTrace(); }
    }	
}
