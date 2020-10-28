package dao.impl;

import dao.WaterPipelineSystemDao;
import exceptions.DataProcessingException;
import model.Graph;
import model.WaterPipelineSystem;
import service.WaterPipelineNodeService;
import util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WaterPipelineSystemDaoImpl implements WaterPipelineSystemDao {
    private static final String DATASCHEMA_NAME = "WATER_SYSTEM";
    private static final Connection connection = ConnectionUtil.getConnection();
    private WaterPipelineNodeService waterPipelineNodeService;

    public WaterPipelineSystemDaoImpl(WaterPipelineNodeService waterPipelineNodeService) {
        this.waterPipelineNodeService = waterPipelineNodeService;
    }

    @Override
    public Graph getWaterSystemByNode() throws DataProcessingException {
        Graph graph = new Graph();
        graph.addVertex(waterPipelineNodeService.getAllWaterPiplineNodes());

        for (WaterPipelineSystem waterPipelineSystem : getAllWaterPipelineSystem()) {
            graph.addEdge(
                    waterPipelineSystem.getStartPoint(),
                    waterPipelineSystem.getEndPoint(),
                    waterPipelineSystem.getLength()
            );
        }
        return graph;
    }

    @Override
    public List<WaterPipelineSystem> getAllWaterPipelineSystem() throws DataProcessingException {
        List<WaterPipelineSystem> waterPipelineSystems = new ArrayList<>();
        String query = String.format("SELECT * FROM %s;",
                DATASCHEMA_NAME);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                waterPipelineSystems.add(setData(rs));
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't get all items " + e);
        }
        return waterPipelineSystems;
    }

    @Override
    public boolean save(WaterPipelineSystem waterPipelineSystem) throws DataProcessingException {
        String query = String.format("INSERT INTO %s (start,end,len) VALUES (?,?,?)",
                DATASCHEMA_NAME);
        long idStartPoint = getIdByNameNode(waterPipelineSystem.getStartPoint().getNodeName());
        long idEndPoint = getIdByNameNode(waterPipelineSystem.getEndPoint().getNodeName());
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setLong(1, idStartPoint);
            statement.setLong(2, idEndPoint);
            statement.setLong(3, waterPipelineSystem.getLength());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add WaterPipelinode " + e);
        }
    }

    private long getIdByNameNode(String nameNode) throws DataProcessingException {
        return waterPipelineNodeService.getIdByNameNode(nameNode);
    }

    private WaterPipelineSystem setData(ResultSet rs) throws DataProcessingException {
        WaterPipelineSystem waterPipelineSystem = null;
        try {
            waterPipelineSystem = new WaterPipelineSystem(
                    waterPipelineNodeService.getNodeById(rs.getLong("start")),
                    waterPipelineNodeService.getNodeById(rs.getLong("end")),
                    rs.getInt("len"));
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't set data " + e);
        }
        return waterPipelineSystem;
    }
}
