package service.impl;

import dao.WaterPipelineSystemDao;
import exceptions.DataProcessingException;
import model.Graph;
import model.WaterPipelineNode;
import model.WaterPipelineSystem;
import service.WaterPipelineSystemService;

import java.util.List;

public class WaterPipelineSystemServiceImpl implements WaterPipelineSystemService {
    private WaterPipelineSystemDao waterPipelineSystemDao;

    public WaterPipelineSystemServiceImpl(WaterPipelineSystemDao waterPipelineSystemDao) {
        this.waterPipelineSystemDao = waterPipelineSystemDao;
    }

    @Override
    public Graph getWaterSystemGraph() throws DataProcessingException {
        return waterPipelineSystemDao.getWaterSystemByNode();
    }

    @Override
    public List<WaterPipelineSystem> getAllWaterPipelineSystem() throws DataProcessingException {
        return waterPipelineSystemDao.getAllWaterPipelineSystem();
    }

    @Override
    public void save(List<WaterPipelineSystem> waterPipelineSystems) throws DataProcessingException {
        for (WaterPipelineSystem waterPipelineNode : waterPipelineSystems) {
            waterPipelineSystemDao.save(waterPipelineNode);
        }
    }
}
