package model;

import java.util.*;

public class Graph {
    private Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>>();
    }

    public Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> getAdjVertices() {
        return adjVertices;
    }

    public void setAdjVertices(Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    public void addVertex(WaterPipelineNode waterPipelineNode) {
        adjVertices.putIfAbsent(waterPipelineNode, new HashMap<WaterPipelineNode, Integer>());
    }

    public void addVertex(List<WaterPipelineNode> waterPipelineNodes) {
        for (WaterPipelineNode waterPipelineNode : waterPipelineNodes) {
            addVertex(waterPipelineNode);
        }
    }

    public void addEdge(WaterPipelineNode ver, WaterPipelineNode edge, Integer length) {
        addToAdjVertices(getVertex(ver).orElse(ver), getVertex(edge).orElse(edge), length);
        addToAdjVertices(getVertex(edge).orElse(edge), getVertex(ver).orElse(ver), length);
    }

    public Optional<WaterPipelineNode> getVertex(WaterPipelineNode node) {
        return adjVertices.keySet().stream().filter(w -> w.equals(node)).findFirst();
    }

    public boolean isWaterNodeExist(WaterPipelineNode node) {
        return adjVertices.containsKey(node);
    }

    public boolean isWaterNodeExist(Route route) {
        return adjVertices.containsKey(route.getStartPoint())
                && adjVertices.containsKey(route.getEndPoint());
    }

    public HashMap<WaterPipelineNode, Integer> getAdjVertices(WaterPipelineNode waterPipelineNode) {
        return adjVertices.get(waterPipelineNode);
    }

    public Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> depthFirstTraversal(Graph graph, WaterPipelineNode waterPipelineNode) {
        Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> visited = new HashMap<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>>();
        Stack<WaterPipelineNode> stack = new Stack<WaterPipelineNode>();
        stack.push(waterPipelineNode);
        while (!stack.isEmpty()) {
            WaterPipelineNode vertex = stack.pop();
            if (!visited.containsKey(vertex)) {
                visited.put(vertex, adjVertices.get(vertex));
                for (Map.Entry<WaterPipelineNode, Integer> v : graph.getAdjVertices(vertex).entrySet()) {
                    stack.push(v.getKey());
                }
            }
        }
        return visited;
    }

    public boolean isGraphHasRout(Set<WaterPipelineNode> waterPipelineNodeSet, Route route) {
        return waterPipelineNodeSet.contains(route.getStartPoint()) &&
                waterPipelineNodeSet.contains(route.getEndPoint());
    }

    public Map<WaterPipelineNode, Integer> getMinLengthByTwoNodes(Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> graph, Route route) {
        WaterPipelineNode node = route.getStartPoint();
        Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> visited = depthFirstTraversal(this, route.getStartPoint());

        Map<WaterPipelineNode, Integer> map = new HashMap<>();
        map.put(node, 0);

        Stack<WaterPipelineNode> stack = new Stack<WaterPipelineNode>();
        stack.push(node);
        Set<WaterPipelineNode> checkedNode = new LinkedHashSet<>();

        while (!stack.isEmpty()) {
            node = stack.pop();
            for (Map.Entry<WaterPipelineNode, Integer> entry : visited.get(node).entrySet()) {
                if (!checkedNode.contains(entry.getKey())) {
                    if (map.containsKey(entry.getKey())) {
                        map.put(entry.getKey(), Math.min(map.get(node) + entry.getValue(), map.get(node)));
                        continue;
                    }
                    map.put(entry.getKey(), map.get(node) + entry.getValue());
                    stack.push(entry.getKey());
                    checkedNode.add(entry.getKey());
                }
            }
        }
        return map;
    }

    public Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> getGraph() {
        return adjVertices;
    }

    private void addToAdjVertices(WaterPipelineNode waterPipelineStart, WaterPipelineNode
            waterPipelineNodeEnd, Integer length) {
        adjVertices.get(waterPipelineStart).put(waterPipelineNodeEnd, length);
    }
}