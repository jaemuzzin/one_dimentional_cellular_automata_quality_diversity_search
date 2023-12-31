/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package kg.home.muzzin.qd_automaton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

/**
 *
 * @author jae
 */
public class Qd_automaton {

    static final int width = 256;
    static final int population = 2000;
    static final RuleSet ruleset = new Rule37();
    static final int turns = 250;
    static final int iterations = 50;
    static int nextId = 1;

    public static void main(String[] args) {
        run(123);
        run(1);
        run(0);
        run(1231);
    }
    public static void run(int seed) {
        Random r = new Random(seed);
        float blackPercentage = 10;
        {        
//init population
        HashMap<Integer, Species> species = new HashMap<>();
        for (int p = 0; p < population; p++) {
            var dna = new boolean[width];
            for (int i = 0; i < width; i++) {
                dna[i] = r.nextFloat() * 100 < blackPercentage;
            }
            species.put(nextId, new Species(nextId, dna));
            nextId++;
        }

        for (int iteration = 0; iteration < iterations; iteration++) {
            //map to behavior space
            ConcurrentMap<Integer, Integer> behaviors = new ConcurrentHashMap<>();
            species.keySet().stream().parallel().forEach(p -> {
                Automaton a = species.get(p).getAutomaton();
                IntStream.range(0, turns).forEach(i -> a.timeStep(ruleset));
                behaviors.put(p, a.getLastNonRepeatingGeneration());
            });
            species.keySet().stream()
                    .forEach(p -> {
                        if (!behaviors.containsKey(p)) {
                            throw new RuntimeException("why");
                        }
                    });
            //A density map showing how many individuals ended at the height (key)
            HashMap<Integer, Integer> totals = new HashMap<>();
            //density of each species in behavior space.  Will return most dense individuals
            PriorityQueue<Integer> densityScores
                    = new PriorityQueue<>((p1, p2) -> {
                        try {
                            return totals.get(behaviors.get(p2)).compareTo(totals.get(behaviors.get(p1)));
                        } catch (Exception omg) {
                            throw omg;
                        }
                    });
            //init to 0
            IntStream.range(0, turns + 100)
                    .forEach(t -> totals.put(t, 0));
            //total the number of species that ended for each height
            behaviors.values().stream()
                    .forEach((height) -> totals.put(height, totals.get(height) + 1));
            species.keySet().stream()
                    .forEach(p -> densityScores.add(p));

            //remove those with lowest density scores
            IntStream.range(0, population / 2).forEach(i -> {
                species.remove(densityScores.poll());
            });

            //clone existing individuals to be more complex
            List<Species> toAdd = new LinkedList<>();
            species.keySet().stream().forEach(i -> {
                toAdd.add(species.get(i).cloneEvolved(r, nextId));
                nextId++;
            });
            toAdd.forEach(s -> species.put(s.getId(), s));
        }
        {
            HashMap<Integer, Integer> behaviors = new HashMap<>();
            species.keySet().stream().forEach(p -> {
                Automaton a = species.get(p).getAutomaton();
                IntStream.range(0, turns).forEach(i -> a.timeStep(ruleset));
                behaviors.put(p, a.getLastNonRepeatingGeneration());
            });
            var best = species.get(behaviors.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get().getKey());
            var a = best.getAutomaton();
            for (int i = 0; i < turns; i++) {
                //System.out.print(a.toString());
                a.timeStep(ruleset);
            }
            System.out.println("Best Novel Search Height:" + a.getLastNonRepeatingGeneration());
        }
    }
        //init population
        HashMap<Integer, Species> ospecies = new HashMap<>();
        nextId=0;
        for (int p = 0; p < population; p++) {
            var dna = new boolean[width];
            for (int i = 0; i < width; i++) {
                dna[i] = r.nextFloat() * 100 < blackPercentage;
            }
            ospecies.put(nextId, new Species(nextId, dna));
            nextId++;
        }

        for (int iteration = 0; iteration < iterations; iteration++) {
            //map to behavior space
            ConcurrentMap<Integer, Integer> behaviors = new ConcurrentHashMap<>();
            ospecies.keySet().stream().parallel().forEach(p -> {
                Automaton a = ospecies.get(p).getAutomaton();
                IntStream.range(0, turns).forEach(i -> a.timeStep(ruleset));
                behaviors.put(p, a.getLastNonRepeatingGeneration());
            });
            ospecies.keySet().stream()
                    .forEach(p -> {
                        if (!behaviors.containsKey(p)) {
                            throw new RuntimeException("why");
                        }
                    });
            //Will return best individuals
            PriorityQueue<Integer> densityScores
                    = new PriorityQueue<>((p1, p2) -> {
                        try {
                            return (behaviors.get(p2)).compareTo(behaviors.get(p1));
                        } catch (Exception omg) {
                            throw omg;
                        }
                    });
            ospecies.keySet().stream()
                    .forEach(p -> densityScores.add(p));

            //remove those with lowest density scores
            IntStream.range(0, population / 2).forEach(i -> {
                ospecies.remove(densityScores.poll());
            });

            //clone existing individuals to be more complex
            List<Species> toAdd = new LinkedList<>();
            ospecies.keySet().stream().forEach(i -> {
                toAdd.add(ospecies.get(i).cloneEvolved(r, nextId));
                nextId++;
            });
            toAdd.forEach(s -> ospecies.put(s.getId(), s));
        }
        {
            HashMap<Integer, Integer> behaviors = new HashMap<>();
            ospecies.keySet().stream().forEach(p -> {
                Automaton a = ospecies.get(p).getAutomaton();
                IntStream.range(0, turns).forEach(i -> a.timeStep(ruleset));
                behaviors.put(p, a.getLastNonRepeatingGeneration());
            });
            var best = ospecies.get(behaviors.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get().getKey());
            var a = best.getAutomaton();
            for (int i = 0; i < turns; i++) {
                //System.out.print(a.toString());
                a.timeStep(ruleset);
            }
            System.out.println("Best Optimal Search Height:" + a.getLastNonRepeatingGeneration());
        }
        {
            //random generated animals for comparison
            //init population
            HashMap<Integer, Species> randomSpecies = new HashMap<>();
            nextId = 0;
            for (int p = 0; p < population; p++) {
                var dna = new boolean[width];
                for (int i = 0; i < width; i++) {
                    dna[i] = r.nextFloat() * 100 < blackPercentage;
                }
                randomSpecies.put(nextId, new Species(nextId, dna));
                nextId++;
            }
            HashMap<Integer, Integer> randomBehaviors = new HashMap<>();
            randomSpecies.keySet().stream().forEach(p -> {
                Automaton ra = randomSpecies.get(p).getAutomaton();
                IntStream.range(0, turns).forEach(i -> ra.timeStep(ruleset));
                randomBehaviors.put(p, ra.getLastNonRepeatingGeneration());
            });
            var bestRandom = randomSpecies.get(randomBehaviors.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue())).get().getKey());
            var ra = bestRandom.getAutomaton();
            for (int i = 0; i < turns; i++) {
                //System.out.print(a.toString());
                ra.timeStep(ruleset);
            }
            System.out.println("Random Best Height:" + ra.getLastNonRepeatingGeneration());
        }
    }
}
