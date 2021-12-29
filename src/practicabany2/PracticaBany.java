/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicabany2;

import java.util.concurrent.Semaphore;

/**
 *
 * @author macia
 */
public class PracticaBany {
 
    static Semaphore banyo = new Semaphore(1);
    static Semaphore cola = new Semaphore(1); //para la inanición
 
  

    public static void main(String[] args) {       

        Thread m1 =new Thread(new Hombre("MACIA"));
        Thread m2 =new Thread(new Hombre("ARTURO"));
        Thread m3 =new Thread(new Hombre("JUAN"));
        
        Thread q1 =new Thread(new Mujer("JUANA"));
        Thread q2 =new Thread(new Mujer("PAZ"));
        Thread q3 =new Thread(new Mujer("ANA"));
        m1.start();
        m2.start();
        m3.start();
        
        q1.start();
        q2.start();
        q3.start();
    }
    
}
