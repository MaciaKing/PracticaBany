/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicabany2;

import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macia
 */
public class Hombre implements Runnable {

    String identificador;
    int vecesAlBanyo;
    static Semaphore grup3H = new Semaphore(3);
    static volatile int contadorPersonasWCH;
    static Semaphore ScontadorPersonasWCH = new Semaphore(1);
    
    public Hombre(String identificador) {
        this.identificador = identificador;
        vecesAlBanyo = 0;
    }

    public void entraTrabajar() {
        System.out.println( identificador + " arriba al despatx");
    }

    public void trabaja() {
        try {
            System.out.println(identificador + " treballa");
            sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(Mujer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acabaTrabajar() {
        System.out.println(identificador + " acaba la feina.");
    }

    @Override
    public String toString() {
        return identificador;
    }

    @Override
    public void run() {
        entraTrabajar();
        for (int i = 0; i < 2; i++) {
            trabaja();
            try {
                PracticaBany.cola.acquire();         
                  if(contadorPersonasWCH==0){
                      PracticaBany.banyo.acquire();
                  }
                   ScontadorPersonasWCH.acquire();
                   contadorPersonasWCH += 1;
                   ScontadorPersonasWCH.release();
                   vecesAlBanyo += 1;
                   PracticaBany.cola.release();

                //SC
                grup3H.acquire();
                ScontadorPersonasWCH.acquire();
                System.out.println(identificador + " entra " + vecesAlBanyo + "/2" + " contadorWC=" + (3-grup3H.availablePermits()));
                ScontadorPersonasWCH.release();
                sleep(5);
                grup3H.release();
                //SC           

                  ScontadorPersonasWCH.acquire();
                   contadorPersonasWCH -= 1;
                   System.out.println(identificador + " surt");
                
                //POSTCONDICIO
                if(contadorPersonasWCH==0){
                    System.out.println("******El bany està buid******");
                    PracticaBany.banyo.release(); //DESPLOQUEAMOS HOMBRES
                }
                ScontadorPersonasWCH.release();

            } catch (InterruptedException ex) {
                Logger.getLogger(Hombre.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        acabaTrabajar();

    }

}
