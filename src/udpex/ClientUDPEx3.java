/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpex;

import UDP.Student;
import static Utils.utils.covertToGpaLetter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trung
 */
public class ClientUDPEx3 {
    
    public static void main(String[] args) {
        
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            
            // send object cho client
            Student student = new Student("B16DCCN370");
            
            // cover object to byte
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
            
            objectOutputStream.writeObject(student);
            
            byte[] dataSending = baos.toByteArray();
            InetAddress inetAddress = InetAddress.getByName("localhost");
            DatagramPacket datagramPacket = new DatagramPacket(dataSending, dataSending.length, inetAddress, 1109);
            
            datagramSocket.send(datagramPacket);
            
            System.out.println("Đã gửi xong");
            // nhận lại 
           
            byte[] dataReceive = new byte[1024];
            
            datagramPacket = new DatagramPacket(dataReceive, dataReceive.length);
            
            datagramSocket.receive(datagramPacket);
            
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            
            Student student1 = (Student) objectInputStream.readObject();
            
            System.out.println("Nhận được sinh viên : " + student1);
            
            // làm theo yêu cầu và gửi lại
            
            char c = covertToGpaLetter(student1.getGpa());
            student1.setGpaLetter(""+c);
            
            // gửi lại
            
            System.out.println("Sinh viên sau tính toán : " +  student1);
            baos = new ByteArrayOutputStream();
            
            objectOutputStream = new ObjectOutputStream(baos);
            
            objectOutputStream.writeObject(student1);
            
            dataSending = baos.toByteArray();
            inetAddress = datagramPacket.getAddress();
            datagramPacket = new DatagramPacket(dataSending, dataSending.length, inetAddress, 1109);
            
            datagramSocket.send(datagramPacket);
            
            System.out.println("Đã gửi xong");
            
            
            // dong kết nối
            objectOutputStream.close();
            objectOutputStream.close();
              
            
        } catch (SocketException ex) {
            Logger.getLogger(ClientUDPEx3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientUDPEx3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientUDPEx3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    
}
