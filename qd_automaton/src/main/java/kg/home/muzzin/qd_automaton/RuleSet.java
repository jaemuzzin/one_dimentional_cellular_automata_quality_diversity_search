/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kg.home.muzzin.qd_automaton;

/**
 *
 * @author jae
 */
public abstract class RuleSet {
    
    public void applyRules(Automaton a) {
        for (int i = 0; i < a.getWidth(); i++) {
            var n = a.get3NeighboursOf(i);
            int bits = 0;
            bits |= n[2] ? 1 : 0;
            bits |= n[1] ? 2 : 0;
            bits |= n[0] ? 4 : 0;
            a.set(i, rule(bits));
        }
    }
    public abstract boolean rule(int bits);
}
