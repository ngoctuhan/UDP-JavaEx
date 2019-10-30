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
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.x509.IPAddressName;

/**
 *
 * @author trung
 */
public class ClientUDPEx1 {
    
    //  server tại cổng 1107
    
    public static void main(String[] args) {
        
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            
            System.out.println("Sending to client");
            
            byte[] dataSending = ";B16DCCN370;100".getBytes();
           
            InetAddress iPAddressName = InetAddress.getByName("localhost"); 
            DatagramPacket datagramPacket = new DatagramPacket(dataSending, dataSending.length, iPAddressName, 1107);
            
            datagramSocket.send(datagramPacket);
            
            // Give a string
            
            byte[] dataRecive = new byte[1024];
            
            datagramPacket = new DatagramPacket(dataRecive, dataRecive.length);
            datagramSocket.receive(datagramPacket);
            
            String str = new String(datagramPacket.getData());
            str = str.trim();
            
            System.out.println(str);
            
           
            String[] out =  str.split(";");
            String[] out2 = out[1].split(",");
            
            int a = Integer.parseInt(out2[0]);
            int b = Integer.parseInt(out2[1]);
            
            int tmp = utils.gcd(a, b);
            int lcm =  (a*b) / tmp;
            int sum = a+b;
            int mul = a*b;
            String aswer = "" + out[0] + ";" + lcm + "," + sum + "," + mul;
            
            System.out.println(aswer);
            byte[] sendAnswer = aswer.getBytes();
            
            datagramPacket = new DatagramPacket(sendAnswer, sendAnswer.length, iPAddressName, 1107);
            
            datagramSocket.send(datagramPacket);
            
                    
        } catch (SocketException ex) {
            Logger.getLogger(ClientUDPEx1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientUDPEx1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientUDPEx1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
}
