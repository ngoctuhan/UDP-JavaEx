/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author trung
 */
public class utils {
    
    
    public static int gcd(int a, int b)
    {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    // 3.7 – 4 -> A; 3.0 – 3.7 -> B; 2.0 – 3.0 -> C; 1.0 – 2.0 -> D;  0 – 1.0 -> F 
 
    public static char covertToGpaLetter(float n)
    {
        if(n >= 3.7 && n <= 4) return 'A';
        else if(n >= 3.0 && n < 3.7 ) return 'B';
        else if(n >= 2.0 && n < 3 ) return 'C';
        else if(n >= 1.0 && n < 2 ) return 'D';
        else return 'F';
    }

}
