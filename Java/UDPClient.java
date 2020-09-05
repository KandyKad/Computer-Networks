//package udpclient;

/**
 * @author: Kunal Kanade
 */

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

class UDPClient 
{
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args)
    {
        // TODO code application logic here

        DatagramSocket socket = null;                         // Initialization same as Server
        DatagramPacket inPacket = null;                      // receiving Packet
        DatagramPacket outPacket = null;                     // Sending Packet
        byte[] inBuf, outBuf;
        final int PORT = 50000;
        String msg = null;
        Scanner src = new Scanner(System.in);

        try
        {
            InetAddress address = InetAddress.getByName("127.0.0.1");  //LocalHost
            socket = new DatagramSocket();                             //Create a socket on local Address

            msg = "";
            outBuf = msg.getBytes();
            outPacket = new DatagramPacket(outBuf, 0, outBuf.length, address, PORT);
            socket.send(outPacket);

            inBuf = new byte[65535];
            inPacket = new DatagramPacket(inBuf, inBuf.length);
            socket.receive(inPacket);

            String data = new String(inPacket.getData(), 0, inPacket.getLength());
            //Print file list
            System.out.println(data);

            //Send File name
            String filename = src.nextLine();
            outBuf = filename.getBytes();
            outPacket = new DatagramPacket(outBuf, 0, outBuf.length, address, PORT);
            socket.send(outPacket);

            //Receive File
            inBuf = new byte[100000];                           //Max size here is 64kb file
            inPacket = new DatagramPacket(inBuf, inBuf.length);
            socket.receive(inPacket);

            data = new String(inPacket.getData(), 0, inPacket.getLength());
            if(data.endsWith("ERROR"))                        //If file doesn't exist
            {
                System.out.println("File doesn't exist.\n");
                socket.close();
            }
            else
            {
                try
                {
                    BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
                    pw.write(data);

                    //Force write buffer to file
                    pw.close();                              // After writing close the buffer

                    System.out.println("File Write Successful. Closing Socket.");
                    socket.close();
                }

                catch(IOException ioe)
                {
                    System.out.println("File Error\n");
                    socket.close();
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("\nNetwork error, Please try again.\n");
        }

    }
}



