/* 
 * Main.java
 * 
 * IUT de Rodez, no copy right/left    10/04
 */
 
package polynome;

import java.util.ArrayList;

import polynome.model.Monome;

/**
 * TODO
 * 
 * @autor 
 */

class Main {

    public void main(String[] args) {
        System.out.println(tester());
    }

    public boolean tester(){
        Monome monomeTest1 = new Monome(1.1, 1);
        Monome monomeTest2 = new Monome(2.2 , 2);
        Monome monomeTest3 = new Monome(3.3 , 0);

        ArrayList<Monome> listeMonomeTest = new ArrayList<>();

        listeMonomeTest.add(monomeTest3);
        listeMonomeTest.add(monomeTest1);
        listeMonomeTest.add(monomeTest2);

        return true; //STUB
    }
}