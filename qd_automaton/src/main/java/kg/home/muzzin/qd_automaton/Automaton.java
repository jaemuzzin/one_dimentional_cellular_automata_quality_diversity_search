package kg.home.muzzin.qd_automaton;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

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
    
    public void export(String filename) throws IOException{
        BufferedImage img = new BufferedImage(width, history.size(), BufferedImage.TYPE_INT_RGB);
        var g = img.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, history.size());
        IntStream.range(0, history.size()).forEach(h -> {
            for (int i = 0; i < width; i++) {
                g.setColor(history.get(h)[i] ? Color.black : Color.white);
                g.drawRect(i, h, 1, 1);
            }
        });
        ImageIO.write(img, "PNG", new File(filename));
    }
}
