/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package kg.home.muzzin.qd_automaton;

import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jae
 */
public class AutomatonTest {
    
    public AutomatonTest() {
    }

    

    @Test
    public void testTimeStep() {
        Random r = new Random(134);
        RuleSet rules = new Rule110();
        Automaton instance = new Automaton(25);
        for(int i=0;i<25;i++){
            instance.set(i, r.nextBoolean());
        }
        instance.commit();
        for(int i=0;i<25;i++){
            System.out.print(instance.toString());
        instance.timeStep(rules);
        }
    }
    @Test
    public void testTimeStep2() {
        Random r = new Random(14);
        RuleSet rules = new Rule37();
        Automaton instance = new Automaton(80);
        for(int i=0;i<80;i++){
            instance.set(i, r.nextBoolean());
        }
        instance.commit();
        for(int i=0;i<25;i++){
            System.out.print(instance.toString());
        instance.timeStep(rules);
        }
    }
}
