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
public class Mujer implements Runnable {

    String identificador;
    int vecesAlBanyo;
    static Semaphore grup3M = new Semaphore(3);
    static volatile int contadorPersonasWCM;
    static Semaphore ScontadorPersonasWCM = new Semaphore(1);
    
    public Mujer(String identificador) {
        this.identificador = identificador;
        vecesAlBanyo = 0;
    }

    public void entraTrabajar() {
        System.out.println("\t" + identificador + " arriba al despatx");
    }

    public void trabaja() {
        try {
            System.out.println("\t" + identificador + " treballa");
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
                  if(contadorPersonasWCM==0){
                      PracticaBany.banyo.acquire();
                  }
                  ScontadorPersonasWCM.acquire();
                   contadorPersonasWCM += 1;
                   ScontadorPersonasWCM.release();
                   vecesAlBanyo += 1;
                   PracticaBany.cola.release();

                //SC
                grup3M.acquire();
                ScontadorPersonasWCM.acquire();
                System.out.println("\t" + identificador + " entra " + vecesAlBanyo + "/2" + " contadorWC=" + (3-grup3M.availablePermits()));
                ScontadorPersonasWCM.release();
                sleep(5);
                grup3M.release();
                //SC           

                  ScontadorPersonasWCM.acquire();
                   contadorPersonasWCM -= 1;
                   System.out.println("\t" + identificador + " surt");
                
                //POSTCONDICIO
                if(contadorPersonasWCM==0){
                    System.out.println("******El bany està buid******");
                    PracticaBany.banyo.release(); //DESPLOQUEAMOS HOMBRES
                }
                ScontadorPersonasWCM.release();

            } catch (InterruptedException ex) {
                Logger.getLogger(Hombre.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        acabaTrabajar();

    }

}
