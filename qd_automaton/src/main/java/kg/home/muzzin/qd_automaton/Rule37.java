package kg.home.muzzin.qd_automaton;

import java.util.Arrays;

/**
 *
 * @author jae
 */
public class Rule37 extends RuleSet {

    @Override
    public boolean rule(int bits) {
        switch (bits) {
            case 0:
                return true;
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                return false;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return false;
            case 7:
            default:
                return false;
        }
    }
}
