/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpex;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trung
 */
public class ServerUDPEx2 {
    
    private final static int SERVER_PORT = 1108;
    
    public static void main(String[] args) {
        
        try {
            
            // nhận dữ liệu từ client
            DatagramSocket datagramSocket = new DatagramSocket(SERVER_PORT);
            while(true)
            {
                System.out.println("Waiting a client send packet");
                byte[] dataReceive = new byte[1024];

                DatagramPacket datagramPacket = new DatagramPacket(dataReceive, dataReceive.length);

                datagramSocket.receive(datagramPacket);

                String strReceive = new String (datagramPacket.getData());
                strReceive = strReceive.trim(); // trim đi phần thừa

                System.out.println("Nhận được :" + strReceive);

                // gửi lại cho client 1 chuỗi “requestId; data” 

                Random random = new Random();
                int requestId = random.nextInt();

                String data = "homnay12?>>!axby&^^.))";

                String dataSending = "" + requestId + ";" + data;

                System.out.println("Chuỗi gửi đi :" + dataSending);

                dataReceive = new byte[1024];
                dataReceive = dataSending.getBytes();

                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();

                datagramPacket = new DatagramPacket(dataReceive, dataReceive.length, inetAddress, port);

                datagramSocket.send(datagramPacket);

                System.out.println("Gửi xong");
                // tạo kết quả
                String str1 = "homnay12axby";
                String str2 = "?>>!&^^.))";

                String result = "" + requestId + ";" + str1 + "," + str2;

                // nhận kết quả client submit

                dataReceive = new byte[1024];

                datagramPacket = new DatagramPacket(dataReceive, dataReceive.length);


                datagramSocket.receive(datagramPacket);


                strReceive = new String(datagramPacket.getData());


                strReceive = strReceive.trim();

                System.out.println("Nhận kết quả submit: " + strReceive);

                //  check kết quả 

                if(result.equalsIgnoreCase(strReceive))
                {
                    System.out.println("OK");
                }
                else{
                    System.out.println("NOT OKE");
                }
            }
            
            
            
        } catch (SocketException ex) {
            Logger.getLogger(ServerUDPEx2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerUDPEx2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
}
