/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpex;

import Utils.utils;
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
public class ServerUDPEx1 {
    
    private final static int SERVER_PORT = 1107;
    
    public static void main(String[] args) {
        
        try {
            DatagramSocket datagramSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("Waiting a client send packet"); 
            while(true)
            {
                // nhận dữ liệu gửi lên
                byte[] dataReceive = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(dataReceive, dataReceive.length);
                
                datagramSocket.receive(datagramPacket);
                
                String receiveStr = new String (datagramPacket.getData());
                receiveStr = receiveStr.trim();
                
                System.out.println(receiveStr);
                
                
                // random ra 2 số để gửi cho client
                Random random = new Random();
                int a = random.nextInt(100);
                int b = random.nextInt(100);
                
                int requestId = random.nextInt();
                
                // định dạn gửi cho client
                String StrSending = "" + requestId + ";" + a +"," + b;
                
                // lấy lại IP và port của người gửi
                InetAddress iPAddressName = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                
                // Gửi lại thông tin cho client theo đúng định dạng
                byte[] sending = StrSending.getBytes();
                DatagramPacket datagramPacket1 = new DatagramPacket(sending, sending.length, iPAddressName, port);
                
                datagramSocket.send(datagramPacket1);
                
                // đáp án  
                int sum = a+b;
                int mul = a*b;
                int tmp = utils.gcd(a, b);
                int lcm =  (a*b) / tmp;
                String correct = "" + requestId + ";" + lcm + "," + sum + "," + mul;
                
                
                // nhận kết quả
                dataReceive = new byte[1024];
                
                datagramPacket = new DatagramPacket(dataReceive, dataReceive.length);
                
                datagramSocket.receive(datagramPacket);
                
                receiveStr = new String (datagramPacket.getData());
                receiveStr = receiveStr.trim();
                
                System.out.println(receiveStr);
                
                // check kết quả và hiển thị
                if(receiveStr.equalsIgnoreCase(correct)){
                    System.out.println("OK");
                }
                else{
                    System.out.println("NOT OK");
                }
   
            }
            
        } catch (SocketException ex) {
            Logger.getLogger(ClientUDPEx1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientUDPEx1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
