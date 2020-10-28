package service.impl;

import dao.WaterPipelineNodeDao;
import exceptions.DataProcessingException;
import model.WaterPipelineNode;

import java.util.List;

public class WaterPipelineNodeServiceImpl implements service.WaterPipelineNodeService {
    private WaterPipelineNodeDao waterPipelineNodeDao;

    public WaterPipelineNodeServiceImpl(WaterPipelineNodeDao waterPipelineNode) {
        this.waterPipelineNodeDao = waterPipelineNode;
    }

    @Override
    public boolean save(List<WaterPipelineNode> waterPipelineNodes) throws DataProcessingException {
        long id = -1;
        for (WaterPipelineNode waterPipelineNode : waterPipelineNodes) {
            id = waterPipelineNodeDao.save(waterPipelineNode);
            if (id < 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<WaterPipelineNode> getAllWaterPiplineNodes() throws DataProcessingException {
        return waterPipelineNodeDao.getAllWaterPiplineNodes();
    }

    @Override
    public long getIdByNameNode(String nameNode) throws DataProcessingException {
        return waterPipelineNodeDao.getIdByNameNode(nameNode);
    }

    @Override
    public WaterPipelineNode getNodeById(long id) throws DataProcessingException {
        return waterPipelineNodeDao.getNodeById(id);
    }
}
