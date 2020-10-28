package service;

import exceptions.DataProcessingException;
import model.Graph;
import model.WaterPipelineSystem;

import java.util.List;

public interface WaterPipelineSystemService {
    Graph getWaterSystemGraph() throws DataProcessingException;

    List<WaterPipelineSystem> getAllWaterPipelineSystem() throws DataProcessingException;

    void save(List<WaterPipelineSystem> waterPipelineSystem) throws DataProcessingException;
}
