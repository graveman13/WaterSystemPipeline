package dao;

import exceptions.DataProcessingException;
import model.WaterPipelineNode;

import java.util.List;

public interface WaterPipelineNodeDao {
    long save(WaterPipelineNode waterPipelineNode) throws DataProcessingException;

    List<WaterPipelineNode> getAllWaterPiplineNodes() throws DataProcessingException;

    long getIdByNameNode(String nameNode) throws DataProcessingException;

    WaterPipelineNode getNodeById(long id) throws DataProcessingException;
}