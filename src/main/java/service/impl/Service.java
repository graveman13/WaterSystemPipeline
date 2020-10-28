package service.impl;

import dao.InitializationDao;
import dao.WaterPipelineNodeDao;
import dao.WaterPipelineSystemDao;
import dao.impl.InitializationDaoImpl;
import dao.impl.WaterPipelineNodeDaoImpl;
import dao.impl.WaterPipelineSystemDaoImpl;
import exceptions.DataProcessingException;
import model.Graph;
import model.Route;
import model.WaterPipelineNode;
import model.WaterPipelineSystem;
import service.WaterPipelineNodeService;
import service.WaterPipelineSystemService;
import util.CVSUtil;
import util.PropertiesUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {
    private CVSUtil cvsUtil;
    private List<WaterPipelineSystem> waterSystems;
    private List<WaterPipelineNode> waterNodes;
    private List<Route> routes;
    private WaterPipelineNodeDao waterPipelineNodeDao;
    private WaterPipelineSystemDao waterPipelineSystemDao;
    private WaterPipelineNodeService waterPipelineNodeService;
    private WaterPipelineSystemService waterPipelineSystemService;
    private Graph graph;
    private static final String PATH_READ_OF_SYSTEMS_FILE = PropertiesUtil.getPropertiesByKey("pathReadFile");
    private static final String PATH_READ_SET_OF_POINS_FILE = PropertiesUtil.getPropertiesByKey("pathSetOfPoints");
    private static final String PATH_RESULT_FILE = PropertiesUtil.getPropertiesByKey("pathWriteFile");

    public Service() {
        cvsUtil = new CVSUtil();
        this.waterPipelineNodeDao = new WaterPipelineNodeDaoImpl();
        this.waterPipelineNodeService = new WaterPipelineNodeServiceImpl(waterPipelineNodeDao);
        this.waterPipelineSystemDao = new WaterPipelineSystemDaoImpl(waterPipelineNodeService);
        this.waterPipelineSystemService = new WaterPipelineSystemServiceImpl(waterPipelineSystemDao);
    }

    public void run() throws IOException, SQLException, DataProcessingException {
        initDataBase();
        readWaterPipelineFile();
        setWaterNode();
        saveNodeToDatebase();
        saveSystemNodesToDatebase();
        readSetOfPointsFile();
        createGraph();
        calculatedResualt();
        cvsUtil.write(routes, new FileWriter(PATH_RESULT_FILE));
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<WaterPipelineSystem> getWaterSystems() {
        return waterSystems;
    }

    public List<WaterPipelineNode> getWaterNodes() {
        return waterNodes;
    }

    private void initDataBase() throws DataProcessingException, SQLException, IOException {
        InitializationDao initializationDao = new InitializationDaoImpl();
        initializationDao.init();
    }

    private void saveNodeToDatebase() throws DataProcessingException {
        waterPipelineNodeService.save(waterNodes);
    }

    private void saveSystemNodesToDatebase() throws DataProcessingException {
        waterPipelineSystemService.save(waterSystems);
    }

    private void setWaterNode() {
        waterNodes = new ArrayList<>();
        for (WaterPipelineSystem waterPipelineSystem : waterSystems) {
            waterNodes.add(waterPipelineSystem.getStartPoint());
            waterNodes.add(waterPipelineSystem.getEndPoint());
        }
        waterNodes = waterNodes.stream().distinct().collect(Collectors.toList());
    }

    private void readSetOfPointsFile() throws IOException {
        routes = cvsUtil.read(new BufferedReader(new FileReader(PATH_READ_SET_OF_POINS_FILE)), Route.class);
    }

    private void readWaterPipelineFile() throws IOException {
        waterSystems = cvsUtil.read(new BufferedReader(new FileReader(PATH_READ_OF_SYSTEMS_FILE)), WaterPipelineSystem.class);
    }

    private void createGraph() throws DataProcessingException {
        graph = waterPipelineSystemService.getWaterSystemGraph();
    }

    private void calculatedResualt() {
        for (Route route : routes) {
            if (graph.isWaterNodeExist(route)) {
                Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> allPathMapByNode = graph.depthFirstTraversal(graph, route.getStartPoint());
                if (!graph.isGraphHasRout(allPathMapByNode.keySet(), route)) {
                    route.setRoutExist(Route.RoutExist.FALSE);
                    continue;
                }
                Map<WaterPipelineNode, Integer> allLenByNode = graph.getMinLengthByTwoNodes(allPathMapByNode, route);
                route.setLength(allLenByNode.get(route.getEndPoint()));
                route.setRoutExist(Route.RoutExist.TRUE);
            } else {
                route.setRoutExist(Route.RoutExist.FALSE);
            }
        }
    }
}
