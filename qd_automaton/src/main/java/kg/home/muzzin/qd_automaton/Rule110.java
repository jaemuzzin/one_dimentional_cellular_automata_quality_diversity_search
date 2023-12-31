package kg.home.muzzin.qd_automaton;

/**
 *
 * @author jae
 */
public class Rule110 extends RuleSet {

    @Override
    public boolean rule(int bits) {
        switch (bits) {
            case 0:
                return false;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
            default:
                return false;
        }
    }

}
