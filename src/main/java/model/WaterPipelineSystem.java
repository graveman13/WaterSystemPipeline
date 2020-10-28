package model;

import java.util.List;

public class WaterPipelineSystem {
    private WaterPipelineNode startPoint;
    private WaterPipelineNode endPoint;
    private int length;

    public WaterPipelineSystem() {
    }

    public WaterPipelineSystem(WaterPipelineNode startPoint, WaterPipelineNode endPoint, int length) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
    }

    public WaterPipelineNode getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(WaterPipelineNode startPoint) {
        this.startPoint = startPoint;
    }

    public WaterPipelineNode getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(WaterPipelineNode endPoint) {
        this.endPoint = endPoint;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static String toString(List<WaterPipelineSystem> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-5s %-5s %-5s\n", "IDX;", "IDY;", "LENGTH"));
        for (WaterPipelineSystem waterPipelineSystem : list) {
            stringBuilder.append(String.format("%-5s %-5s %-5s\n"
                    , waterPipelineSystem.getStartPoint().getNodeName() + ";"
                    , waterPipelineSystem.getEndPoint().getNodeName() + ";"
                    , waterPipelineSystem.getLength()));
        }
        return stringBuilder.toString();
    }
}
