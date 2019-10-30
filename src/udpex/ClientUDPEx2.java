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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trung
 */
public class ClientUDPEx2 {
    
    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            
            System.out.println("Sending to client");
            
            byte[] dataSending = ";B16DCCN370;101".getBytes();
           
            InetAddress iPAddressName = InetAddress.getByName("localhost"); 
            DatagramPacket datagramPacket = new DatagramPacket(dataSending, dataSending.length, iPAddressName, 1108);
            
            datagramSocket.send(datagramPacket);
            
            // Give a string
            
            byte[] dataRecive = new byte[1024];
            
            datagramPacket = new DatagramPacket(dataRecive, dataRecive.length);
            datagramSocket.receive(datagramPacket);
            
            String str = new String(datagramPacket.getData());
            str = str.trim();
            
            System.out.println(str);
            
           // cắt chuỗi lấy 2 phần request id và data
            String[] out =  str.split(";");
            String requestId = out[0];
            String data = out[1];
           // xử lí để đc 2 chuỗi kí tự + số và chuỗi kí tự đặc biệt
           
           String strNormal = "";
           String strSpecial = "";
           
           for(int i=0; i < data.length(); i ++ ){
               
               char c = data.charAt(i);
               if( (c >= 'a' && c <= 'z') || (c >= 'A'  && c <= 'Z')  || (c >= '0' && c <= '9'))strNormal += c;
               
               else strSpecial += c;
           }
            
           // tạo đúng định dạng để gửi 
            
           String aswer = requestId + ";" + strNormal + "," + strSpecial;
            
            System.out.println(aswer);
            
            // gửi đi 
            byte[] sendAnswer = aswer.getBytes();
            
            datagramPacket = new DatagramPacket(sendAnswer, sendAnswer.length, iPAddressName, 1108);
            
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
