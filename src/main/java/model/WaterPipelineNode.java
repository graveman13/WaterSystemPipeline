package model;
import java.util.Objects;

public class WaterPipelineNode {
    private String nodeName;

    public WaterPipelineNode(){}

    public WaterPipelineNode(String label) {
        this.nodeName = label;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterPipelineNode waterPipelineNode = (WaterPipelineNode) o;
        return nodeName.equals(waterPipelineNode.nodeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeName);
    }
}