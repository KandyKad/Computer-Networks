/**
 * A UDP Server
 * @author Kunal Kanade

 Stop and Wait ARQ(Automatic Repeat Request)
 */

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

class UDPStopAndWaitServer
{
	private static final int BUFFER_SIZE = 1024;
	private static final int PORT = 6789;

	public static void main(String[] args) throws IOException 
  {
		// Create a server socket
		DatagramSocket serverSocket = new DatagramSocket( PORT );

		// Set up byte arrays for sending/receiving data
    byte[] receiveData = new byte[ BUFFER_SIZE ];
    byte[] dataForSend = new byte[ BUFFER_SIZE ];

        // Infinite loop to check for connections 
        while(true)
        {

        	// Get the received packet
        	DatagramPacket received = new DatagramPacket( receiveData, receiveData.length );
          serverSocket.receive( received );

          // Get the message from the packet
          int message = ByteBuffer.wrap(received.getData( )).getInt();

          Random random = new Random( );             // As simulating the reliable protocol.. So, there is only two possibility that a packet will be transfered or not transfered..
          int chance = random.nextInt( 100 );        // So taking a randon int in 1 to 100 and if its even assume that it would be transferred else not transferred.


          // 1 in 2 chance of responding to the message. (Very Vague assumption..)
          if( ((chance % 2) == 0) )
          {
              // Get packet's IP and port
              InetAddress IPAddress = received.getAddress();
              int port = received.getPort();

              System.out.println("FROM CLIENT: " + message + " from: " + IPAddress);
              // Convert message to uppercase 
              dataForSend = ByteBuffer.allocate(4).putInt( message ).array();

              // Send the packet data back to the client
              DatagramPacket packet = new DatagramPacket( dataForSend, dataForSend.length, IPAddress, port );
              serverSocket.send( packet ); 
          } 

          else 
          {
              System.out.println( "Oops, packet with sequence number "+ message + " was dropped");
          }
        }
  }
}