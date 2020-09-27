/**
 * A UDP Client
 * @author Kunal Kanade
 
 Single Message sent
 */

import java.io.*;
import java.net.*;

public class Client
{
	public static void main(String args[])throws Exception
	{
		BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket client_socket = new DatagramSocket(); // Datagram socket used to create the UDP socket
		
		InetAddress IP_add = InetAddress.getByName("localhost"); // Getting IP address of the local host
		
		byte in_data[]  = new byte[1024]; // Array which reserver the input datagetting from server
		
		
		byte out_data[] = new byte[1024]; // Creating buffer for sending the data
		
		String Str = user_input.readLine();
		
		out_data = Str.getBytes();
		
		DatagramPacket Packet1 = new DatagramPacket(out_data, out_data.length, IP_add, 1234); // Creating Datagram Packets where Data can be encapsulated
		
		client_socket.send(Packet1); // Sending packet to server
		
		DatagramPacket Packet4 = new DatagramPacket(in_data, in_data.length);		

		client_socket.receive(Packet4);
		
		String receive_str = new String(Packet4.getData()); // Get Server data in string and print it.
		
		System.out.println(receive_str);
				
		client_socket.close(); // Closing client
		
	}
}