package service;

import exceptions.DataProcessingException;
import model.WaterPipelineNode;

import java.util.List;

public interface WaterPipelineNodeService {

    boolean save(List<WaterPipelineNode> pipelineNodes) throws DataProcessingException;

    List<WaterPipelineNode> getAllWaterPiplineNodes() throws DataProcessingException;

    long getIdByNameNode(String nameNode) throws DataProcessingException;

    WaterPipelineNode getNodeById(long id) throws DataProcessingException;
}