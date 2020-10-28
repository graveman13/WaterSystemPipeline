package dao.impl;
import dao.WaterPipelineNodeDao;
import exceptions.DataProcessingException;
import model.WaterPipelineNode;
import util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class WaterPipelineNodeDaoImpl implements WaterPipelineNodeDao {
    private static final String DATASCHEMA_NAME = "WATER_NODE";
    private static final Connection connection = ConnectionUtil.getConnection();
    @Override
    public long save(WaterPipelineNode waterPipelineNode) throws DataProcessingException {
        String query = String.format("INSERT INTO %s (node) VALUES (?)",
                DATASCHEMA_NAME);
        try (PreparedStatement statement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, waterPipelineNode.getNodeName());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add WaterPipelinode " + e);
        }
        return 0;
    }
    @Override
    public List<WaterPipelineNode> getAllWaterPiplineNodes() throws DataProcessingException {
        List<WaterPipelineNode> nodes = new ArrayList<>();
        String query = String.format("SELECT * FROM %s;",
                DATASCHEMA_NAME);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                nodes.add(setData(rs));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all items " + e);
        }
        return nodes;
    }
    @Override
    public long getIdByNameNode(String nameNode) throws DataProcessingException {
        String query = String.format("SELECT id FROM %s WHERE node = ?",
                DATASCHEMA_NAME);
        long id = -1;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nameNode);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all items " + e);
        }
        return id;
    }
    @Override
    public WaterPipelineNode getNodeById(long id) throws DataProcessingException {
        String query = String.format("SELECT node FROM %s WHERE id = ?",
                DATASCHEMA_NAME);
        WaterPipelineNode waterPipelineNode = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return setData(rs);
            }
        } catch (SQLException | DataProcessingException e) {
            throw new DataProcessingException("Can't get all items " + e);
        }
        return waterPipelineNode;
    }
    private WaterPipelineNode setData(ResultSet rs) throws DataProcessingException {
        WaterPipelineNode waterPipelineNode = null;
        try {
            waterPipelineNode = new WaterPipelineNode(rs.getString("node"));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all items " + e);
        }
        return waterPipelineNode;
    }
}
