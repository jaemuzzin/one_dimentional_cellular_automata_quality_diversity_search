
package kg.home.muzzin.qd_automaton;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author jae
 */
public class Species {
    private int width;
    private boolean[] dna;
private int id;
    public Species(int id, boolean[] dna) {
        this.width = dna.length;
        this.dna = dna;
        this.id = id;
    }
    
    public Automaton getAutomaton(){
        var a = new Automaton(width);
        IntStream.range(0, width).forEach(i -> a.set(i, dna[i]));
        a.commit();
        return a;
    }
    
    public Species cloneEvolved(Random r, int id){
        boolean[] newDna = Arrays.copyOf(dna, width);
        int i = r.nextInt(width);
        newDna[i] = !newDna[i];
        return new Species(id, newDna);
    }

    public int getWidth() {
        return width;
    }

    public boolean[] getDna() {
        return dna;
    }

    public int getId() {
        return id;
    }
    
}
