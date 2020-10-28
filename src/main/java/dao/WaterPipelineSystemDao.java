package dao;

import exceptions.DataProcessingException;
import model.Graph;
import model.WaterPipelineNode;
import model.WaterPipelineSystem;

import java.util.List;

public interface WaterPipelineSystemDao {
    Graph getWaterSystemByNode() throws DataProcessingException;

    List<WaterPipelineSystem> getAllWaterPipelineSystem() throws DataProcessingException;

    boolean save(WaterPipelineSystem waterPipelineSystem) throws DataProcessingException;
}
