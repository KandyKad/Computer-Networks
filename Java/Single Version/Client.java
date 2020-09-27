/**
 * A UDP Client
 * @author Kunal Kanade
 
 Multi client-server Chat system
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Multiclient // Multiclient
{
    
    public static void main(String args[]) throws Exception
	{
		Socket sk = new Socket("127.0.0.1",5000);
		
		BufferedReader sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));

		PrintStream sout=new PrintStream(sk.getOutputStream());

		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));

		String s;
		while (true)
		{
			System.out.print("Client : ");

			s=stdin.readLine();

			sout.println(s);
            if ( s.equalsIgnoreCase("BYE") ) // Exit statement
            {
                System.out.println("Connection ended by client");

 				break;
            }

			s = sin.readLine(); // Keep sending msgs till exit

			System.out.print("Server : "+s+"\n");
  			
		}

		sk.close(); //Closing sockets

		sin.close();

		sout.close();

 		stdin.close();
	}
    
}
