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
     
//    static Semaphore grup3H = new Semaphore(3);
//    static Semaphore grup3M = new Semaphore(3);
//    static Semaphore grup3 = new Semaphore(3);

//    static volatile int contadorPersonasWCH;
//    static Semaphore ScontadorPersonasWCH = new Semaphore(1);
//    static volatile int contadorPersonasWCM;
//    static Semaphore ScontadorPersonasWCM = new Semaphore(1);
    
    static Semaphore banyo = new Semaphore(1);
    static Semaphore cola = new Semaphore(1); //para la inanición
    
//    static int general;
  

    public static void main(String[] args) {       
//        contadorPersonasWC=0;
//        contadorPersonasWCH=0;
//        contadorPersonasWCM=0;
//        general=0;
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
