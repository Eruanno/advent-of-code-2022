package com.mycompany.app;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mycompany.app.FileReader.readInput;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toList;

public class Day16 implements Day {

    private final Pattern pressurePattern = Pattern.compile("-?\\d+");
    private final Pattern valvesPattern = Pattern.compile("[A-Z][A-Z]");

    private final Map<String, List<String>> tunnelsGraph = new HashMap<>();
    private final Map<String, Integer> pressureValues = new HashMap<>();
    private final Map<String, Integer> maxPressure = new HashMap<>();

    private final List<Edge> optimizedGraph = new ArrayList<>();
    private final List<Edge> allEdges = new ArrayList<>();
    private final Map<String, Vertex> vertexes = new HashMap<>();

    private final String filename;
    private List<String> input;

    public Day16(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        for (String line : input) {
            Matcher pressureMatcher = pressurePattern.matcher(line);
            pressureMatcher.find();
            int pressure = Integer.parseInt(pressureMatcher.group(0));

            Matcher valvesMatcher = valvesPattern.matcher(line);
            valvesMatcher.find();
            String label = valvesMatcher.group(0);
            List<String> tunnels = new ArrayList<>();
            while (valvesMatcher.find()) {
                tunnels.add(valvesMatcher.group(0));
            }
            tunnelsGraph.put(label, tunnels);
            pressureValues.put(label, pressure);
        }
        simplifyGraph();
    }

    private void simplifyGraph() {
        for (Map.Entry<String, List<String>> entry : tunnelsGraph.entrySet()) {
            String labelFrom = entry.getKey();
            for (String labelTo : entry.getValue()) {
                Vertex vertexFrom = vertexes.get(labelFrom);
                if (vertexFrom == null) {
                    vertexFrom = new Vertex(labelFrom, pressureValues.get(labelFrom));
                    vertexes.put(labelFrom, vertexFrom);
                }
                Vertex vertexTo = vertexes.get(labelTo);
                if (vertexTo == null) {
                    vertexTo = new Vertex(labelTo, pressureValues.get(labelTo));
                    vertexes.put(labelTo, vertexTo);
                }
                optimizedGraph.add(new Edge(vertexFrom, vertexTo, 1));
            }
        }
        List<String> emptyValves = pressureValues.entrySet()
                                                 .stream()
                                                 .filter(e -> e.getValue() == 0)
                                                 .map(Map.Entry::getKey)
                                                 .collect(toList());
        emptyValves.remove("AA");
        while (!emptyValves.isEmpty()) {
            String toRemove = emptyValves.remove(0);
            List<Edge> toSimplify = new ArrayList<>();
            for (Edge edge : optimizedGraph) {
                if (edge.from.label.equals(toRemove) || edge.to.label.equals(toRemove)) {
                    toSimplify.add(edge);
                }
            }
            if (!toSimplify.isEmpty()) {
                optimizedGraph.removeAll(toSimplify);
                List<Edge> processed = new ArrayList<>();
                for (Edge edgeA : toSimplify) {
                    if (!processed.contains(edgeA)) {
                        Edge newEdge;
                        if (edgeA.from.label.equals(toRemove)) {
                            Edge edgeB = toSimplify.stream()
                                                   .filter(e -> !e.from.equals(edgeA.to) && e.to.label.equals(toRemove))
                                                   .findFirst()
                                                   .get();
                            newEdge = new Edge(edgeB.from, edgeA.to, edgeA.cost + edgeB.cost);
                            processed.add(edgeB);
                        } else {
                            Edge edgeB = toSimplify.stream()
                                                   .filter(e -> !e.to.equals(edgeA.from) && e.from.label.equals(toRemove))
                                                   .findFirst()
                                                   .get();
                            newEdge = new Edge(edgeA.from, edgeB.to, edgeA.cost + edgeB.cost);
                            processed.add(edgeB);
                        }
                        optimizedGraph.add(newEdge);
                        processed.add(edgeA);
                    }
                }
            }
        }
        calcDistances();
    }

    private void calcDistances() {
        List<String> ind = vertexes.keySet().stream().sorted(naturalOrder()).toList();
        int[][] adjMatrix = new int[vertexes.size()][vertexes.size()];
        String[][] prevMatrix = new String[vertexes.size()][vertexes.size()];
        for (int i = 0; i < ind.size(); i++) {
            for (int j = 0; j < ind.size(); j++) {
                if (i == j) {
                    adjMatrix[i][j] = 0;
                } else {
                    adjMatrix[i][j] = 999;
                }
            }
        }
        for (Edge edge : optimizedGraph) {
            int a = ind.indexOf(edge.from.label);
            int b = ind.indexOf(edge.to.label);
            adjMatrix[a][b] = edge.cost;
            prevMatrix[a][b] = edge.from.label;
        }
        for (int u = 0; u < ind.size(); u++) {
            for (int v1 = 0; v1 < ind.size(); v1++) {
                for (int v2 = 0; v2 < ind.size(); v2++) {
                    if (adjMatrix[v1][v2] > adjMatrix[v1][u] + adjMatrix[u][v2]) {
                        adjMatrix[v1][v2] = adjMatrix[v1][u] + adjMatrix[u][v2];
                        prevMatrix[v1][v2] = prevMatrix[u][v2];
                    }
                }
            }
        }
        for (int v1 = 0; v1 < ind.size(); v1++) {
            for (int v2 = 0; v2 < ind.size(); v2++) {
                if (adjMatrix[v1][v2] > 0 && adjMatrix[v1][v2] < 999) {
                    Edge newEdge = new Edge(vertexes.get(ind.get(v1)), vertexes.get(ind.get(v2)), adjMatrix[v1][v2]);
                    allEdges.add(newEdge);
                }
            }
        }
    }

    @Override
    public String calculateFirstStar() {
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State("AA", 30, 0, ""));
        while (!queue.isEmpty()) {
            State state = queue.poll();
            List<Edge> edges = allEdges.stream()
                                       .filter(e -> e.from.equals(vertexes.get(state.currentValve)))
                                       .filter(e -> !e.to.label.equals("AA"))
                                       .filter(e -> !state.openedValves.contains(e.to.label))
                                       .filter(e -> state.timeRemaining - e.cost >= 1)
                                       .toList();
            for (Edge edge : edges) {
                String openedValves = state.openedValves.isEmpty() ? edge.to.label : state.openedValves + "," + edge.to.label;
                int timeRemaining = state.timeRemaining - edge.cost - 1;
                int totalRelief = state.totalRelief + edge.to.pressure * timeRemaining;
                queue.offer(new State(edge.to.label, timeRemaining, totalRelief, openedValves));
                updateRelief(openedValves, totalRelief);
            }
        }
        return "" + maxPressure.values().stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    @Override
    public String calculateSecondStar() {
        return "";
    }

    private void updateRelief(String openedValves, int totalRelief) {
        if (maxPressure.containsKey(openedValves)) {
            if (maxPressure.get(openedValves) < totalRelief) {
                maxPressure.put(openedValves, totalRelief);
            }
        } else {
            maxPressure.put(openedValves, totalRelief);
        }
    }

    private record State(String currentValve, int timeRemaining, int totalRelief, String openedValves) {
    }

    private record Vertex(String label, int pressure) {
    }

    private record Edge(Vertex from, Vertex to, int cost) {
    }
}
