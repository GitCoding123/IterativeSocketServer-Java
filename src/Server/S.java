import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author bgerk
 *
 */
public class S {
	private static int PORT;
	private static BufferedReader in;
	private static PrintWriter out;
	private static int clientNum = 0;;
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException{
		String data = "";
		
		PORT = Integer.parseInt(args[0]);
		System.out.println("\nConnecting to port " + PORT);
		ServerSocket  ss = new ServerSocket(PORT);
		System.out.println("\nConnection successful!");
		System.out.println("\n[SERVER] Waiting for client connection...");
		Socket client = ss.accept();
		clientNum++;
		System.out.println("\n[SERVER] Successful connection with client " + clientNum + ": " + client.getRemoteSocketAddress().toString());
		System.out.println("\n[SERVER] Waiting for client connection...");
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			while(true) {
				String menuOption = in.readLine(); // read in menuOptio
//				if (menuOption.equals("7")) {
//						client.close();
//						System.out.println("\n[SERVER] Waiting for client connection...");
//						continue;
//					} // end if
				if(!menuOption.equals("7")) {
					data = getProcess(menuOption); //retrieve data
				}
				else {
					System.out.println("\n[SERVER] Client has left.");
					client.close();
					continue;
				}
				
				out.println(data + "[END]");  //write data to client
				out.flush();				
			}//end while
			} catch(IOException e) {
				System.out.println("This is IOException for SThread.run()");
			} // end catch		
	} // end main
	
	/**
	 * 
	 * @param menuOption
	 * @return
	 * @throws IOException
	 */
	public static String getProcess(String menuOption) throws IOException {
		switch(menuOption) {
			case "1":
				System.out.println("\n[SERVER] Sending date to client...");
				System.out.println("\n[SERVER] Waiting for client connection...");
				return getData("date");
				
			case "2":
				System.out.println("\n[SERVER] Sending uptime to client...");
				System.out.println("\n[SERVER] Waiting for client connection...");
				return getData("uptime");
				
			case "3":
				System.out.println("\n[SERVER] Sending memory use to client...");
				System.out.println("\n[SERVER] Waiting for client connection...");
				return getData("cat /proc/meminfo");
				
			case "4":
				System.out.println("\n[SERVER] Sending netstat...");
				System.out.println("\n[SERVER] Waiting for client connection...");
				return getData("netstat");
				
			case "5":
				System.out.println("\n[SERVER] Sending current users...");
				System.out.println("\n[SERVER] Waiting for client connection...");
				return getData("w");
				
			case "6":
				System.out.println("\n[SERVER] Sending running processes...");
				System.out.println("\n[SERVER] Waiting for client connection...");
				return getData("ps -A");
			case "7":
				System.out.println("\n[SERVER] Client has left");
			default: 
				return "Incorrect Entry. Please choose ";
		}  //end switch case
	}  //end method
	
	/**
	 * 
	 * @param command
	 * @param numCommands
	 * @return
	 * @throws IOException 
	 */
	private static String getData(String command) {
		//int numR = Integer.parseInt(numRequests);
		String data = "";
		
		String nextLine;
		Process process;
		BufferedReader reader = null;
		
		try { 

			process = Runtime.getRuntime().exec(command);

			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			while ((nextLine = reader.readLine()) != null) {
				data = data + nextLine + "\n";
			}

		} catch (IOException e) {
			System.out.println("Oops, We've encountered an error :(");
			e.printStackTrace();
		} // end try/catch
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}// end getInput

} // end class