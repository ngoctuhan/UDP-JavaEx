/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpex;

import UDP.Student;
import Utils.utils;
import static Utils.utils.covertToGpaLetter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import org.jcp.xml.dsig.internal.dom.Utils;

/**
 *
 * @author trung
 */
public class ServerUDPEx3 {
    
    private final static int SERVER_PORT = 1109;
    
    public static void main(String[] args) throws SocketException, IOException, ClassNotFoundException {
        
        DatagramSocket datagramSocket = new DatagramSocket(SERVER_PORT);
        
        System.out.println("Waitting a client send a object");
        
        while(true)
        {
            byte[] dataReceive = new byte[1024];
            
            DatagramPacket datagramPacket = new DatagramPacket(dataReceive, dataReceive.length);
            
            // nhận đối tượng
            datagramSocket.receive(datagramPacket);
            
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            
            Student student = (Student) objectInputStream.readObject();
            
            System.out.println("Nhận được sinh viên : " +  student);
            
            //   thiết lập là id, code, gpa 
            
            student.setId(123);
            student.setCode("B16DCN370");
            
            Random random = new Random();
            
            
            student.setGpa( random.nextInt(4) );
            
            // gửi lại object cho client nhé.
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
            objectOutputStream.writeObject(student);
            byte[] dataSending = baos.toByteArray();
            
            InetAddress inetAddress = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            
            datagramPacket = new DatagramPacket(dataSending, dataSending.length, inetAddress, port);
            datagramSocket.send(datagramPacket);
            
            System.out.println("Đã gửi xong : " + student);
            
            // 
            char c = covertToGpaLetter(student.getGpa());
            student.setGpaLetter(""+c);
            
            // nhận object gửi về
            
            dataReceive = new byte[1024];
            datagramPacket = new DatagramPacket(dataReceive, dataReceive.length);
            
            datagramSocket.receive(datagramPacket);
            
            byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            
            Student  student1 = (Student) objectInputStream.readObject();
            System.out.println("Kết quả :  " + student);
            System.out.println("Nhận sinh viên: " +  student1);
            
            // check kết quả
            
            if(student.toString().equals(student1.toString()))
            {
                System.out.println("OK");
            }
            else
                System.out.println("NOT OK");
            
            
            objectInputStream.close();
            objectOutputStream.close();
            
        }
        
        
                
        
    }
    
    
}
