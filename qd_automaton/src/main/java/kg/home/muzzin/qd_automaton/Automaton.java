/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kg.home.muzzin.qd_automaton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jae
 */
public class Automaton {

    private int width;
    private ArrayList<boolean[]> history = new ArrayList<>();
    private boolean[] data;
    private boolean[] dataBuffer;
    private int lastNonRepeatingGeneration = 0;
    private boolean repeatingNow = false;

    public Automaton(int width) {
        this.width = width;
        data = new boolean[width];
        Arrays.fill(data, false);
        dataBuffer = new boolean[width];
        Arrays.fill(dataBuffer, false);
    }

    public void set(int i, boolean value) {
        dataBuffer[i] = value;
    }

    public boolean[] get3NeighboursOf(int i) {
        if (i == 0) {
            return new boolean[]{data[width - 1], data[0], data[1]};
        }
        return new boolean[]{data[(i - 1) % width], data[(i) % width], data[(i + 1) % width]};
    }

    public int getWidth() {
        return width;
    }

    public void commit() {
        for (int i = 0; i < data.length; i++) {
            data[i] = dataBuffer[i];
        }
        history.add(Arrays.copyOf(data, width));
    }

    public boolean[] getData() {
        return data;
    }

    public void timeStep(RuleSet rules) {
        rules.applyRules(this);
        commit();
        if (!repeatingNow && 
                (Arrays.compare(data, history.get(history.size() - 2)) == 0 || 
                (history.size() > 2 && Arrays.compare(data, history.get(history.size() - 3)) == 0))) {
            repeatingNow = true;
        }
        if (!repeatingNow) {
            lastNonRepeatingGeneration++;
        }
    }

    public void clear() {
        data = new boolean[width];
        Arrays.fill(data, false);
        dataBuffer = new boolean[width];
        Arrays.fill(dataBuffer, false);
    }

    public int getLastNonRepeatingGeneration() {
        return lastNonRepeatingGeneration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append((data[i] ? "#" : " "));
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
