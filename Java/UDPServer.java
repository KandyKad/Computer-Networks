//package udpserver;

//Sends all types of files within UDP Packet size limit.

/**
 * @author: Kunal Kanade
 */

import java.io.*;
import java.net.*;
import java.util.Arrays;

class UDPServer 
{
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) 
    {
        // TODO code application logic here
 
        DatagramSocket socket = null;           // Sending or receiving point (Multiple packets from one machine may be routed differently)
        DatagramPacket inPacket = null;         // Receiving Packet (Used for connection-less packet delivery)
        DatagramPacket outPacket = null;        // Sending Packet
        byte[] inBuf, outBuf;                   // get the data from the packet as bytes
        String msg;
        final int PORT = 50000;                 // Where(server) the data will be sent (Any port from 1024 to 65536)

        try
        {
            socket = new DatagramSocket(PORT);
            while(true)                         // Keeps running infinitely until closure
            {
                System.out.println("\nRunning...\n");
                
                inBuf = new byte[100];                               // To accept msg's coming from client (byte array)
                inPacket = new DatagramPacket(inBuf, inBuf.length);  // Packet of inBuf
                socket.receive(inPacket); 

                int source_port = inPacket.getPort();
                InetAddress source_address = inPacket.getAddress();
                msg = new String(inPacket.getData(), 0, inPacket.getLength()); // Get msg from 0th position till end length.
                System.out.println("Client: " + source_address + ":" + source_port); //Shows where the client has connected

                // Contains txt files to be sent to the client when requested 
                String dirname = "D:\\Kunal\\IIIT PUNE\\SEM 5\\Computer Networks - YN Singh (IIT Kanpur)\\Lab\\20.08.2020";  

                File f1 = new File(dirname);
                File fl[] = f1.listFiles();                          //List of files in the directory

                StringBuilder sb = new StringBuilder("\n");
                int c = 0;

                for(int i = 0; i < fl.length; i++)
                {
                    if(fl[i].canRead())                      // No. of files that can be actually read. Hidden files cannot be read
                        c++;
                }

                sb.append(c + " file found.\n\n");

                for(int i = 0; i < fl.length; i++)           // Append name and size of files to StringBuilder
                {
                    sb.append(fl[i].getName() + " " + fl[i].length() + " Bytes\n");
                }

                sb.append("\nEnter file name for download");      // Req. file name for download
                outBuf = (sb.toString()).getBytes();                // As msg needs to be sent in form of bytes
                outPacket = new DatagramPacket(outBuf, 0, outBuf.length, source_address, source_port);
                socket.send(outPacket);                           // Packet sent

                inBuf = new byte[100];
                inPacket = new DatagramPacket(inBuf, inBuf.length);   // File name to be downloaded
                socket.receive(inPacket);
                String filename = new String(inPacket.getData(), 0, inPacket.getLength());

                System.out.println("Requested File: " + filename);

                boolean flis = false;
                int index = -1;
                sb = new StringBuilder("");
                for(int i = 0; i < fl.length; i++)
                {
                    if(((fl[i].getName()).toString()).equalsIgnoreCase(filename))
                    {
                        index = i;
                        flis = true;             // If file exist set true
                    }
                }

                if(!flis)                        // If file not found, then resp. error
                {
                    System.out.println("ERROR");
                    sb.append("ERROR");
                    outBuf = (sb.toString()).getBytes();
                    outPacket = new DatagramPacket(outBuf, 0, outBuf.length, source_address, source_port);
                    socket.send(outPacket);
                }
                else
                {
                    try
                    {
                        // File Send Process, Independent
                        File ff = new File(fl[index].getAbsolutePath());  // Gets the file path to get the file in runtime
                        FileReader fr = new FileReader(ff);               // Read file
                        BufferedReader brf = new BufferedReader(fr);
                        String s = null;
                        sb = new StringBuilder();                         //Append line

                        while((s = brf.readLine()) != null)
                        {
                            sb.append(s);
                        }
                        if(brf.readLine() == null)
                            System.out.println("File Read Successful. Close Socket.");  // When file reading is successful, socket closed.

                        outBuf = new byte[100000];                        //Send msg
                        outBuf = (sb.toString()).getBytes();
                        outPacket = new DatagramPacket(outBuf, 0, outBuf.length, source_address, source_port);
                        socket.send(outPacket);                          //Packet sent to client
                    }
                    catch(IOException ioe)                               //If any exception while sending
                    {  
                        System.out.println(ioe);
                    }
                } // Else ending

            } //while ending

        } // Try ending

        catch(Exception e) // For main try block
        {
            System.out.println("Error\n");
        }
    }
}
