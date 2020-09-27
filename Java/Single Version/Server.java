/**
 * A UDP Server
 * @author Kunal Kanade
 
 Single Message sent
 */

import java.io.*;
import java.net.*;

public class Server
{
	public static void main(String args[]) throws Exception
	{
		DatagramSocket server_socket = new DatagramSocket(1234);
		
		BufferedReader server_input = new BufferedReader(new InputStreamReader(System.in)); // Create object in server to input from keyboard
		
		InetAddress IP_add = InetAddress.getByName("localhost"); // Getting IP address of localhost
		
		byte out_data[] = new byte[1024]; //Creating Buffer for sending the data
		
		byte in_data[] = new byte[1024];
		
		while(true)
		{
			DatagramPacket Packet2 = new DatagramPacket(in_data, in_data.length); // Create Datagram Packet
			
			server_socket.receive(Packet2); //Data from the client received in the Packet
			
			String Str = new String(Packet2.getData()); // Using getData() method returns data containing in the datagram which is stored in array of bytes
			
			InetAddress IP_add1 = Packet2.getAddress(); // get the IP address of client
			
			int port = Packet2.getPort();
			
			System.out.println(Str);
			
			String send_str = server_input.readLine(); // Create String& read server input
			
			out_data = send_str.getBytes(); // To send data
			
			DatagramPacket Packet3 = new DatagramPacket(out_data, out_data.length, IP_add1, port); // Creating Datagram Packet which data encapsulated
			
			server_socket.send(Packet3); // Send Packet to Client
			
			
		}
	}
}