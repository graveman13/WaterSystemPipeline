package model;

import java.util.List;

public class Route {
    private WaterPipelineNode startPoint;
    private WaterPipelineNode endPoint;
    private RoutExist routExist;
    private Integer length;

    public Route(WaterPipelineNode startPoint, WaterPipelineNode endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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

    public String getResult() {
        return routExist.name() + length;
    }

    public RoutExist getRoutExist() {
        return routExist;
    }

    public void setRoutExist(RoutExist routExist) {
        this.routExist = routExist;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public static String toString(List<Route> routes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-10s %-10s\n", "IDA;", "IDB"));
        for (Route route : routes) {
            stringBuilder.append(String.format("%-10s %-10s\n"
                    , route.getRoutExist() + ";"
                    , route.getLength() + ";"
                    )
            );
        }
        return stringBuilder.toString();
    }

    public enum RoutExist {
        TRUE, FALSE;
    }
}
