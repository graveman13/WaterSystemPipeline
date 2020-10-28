import dao.WaterPipelineNodeDao;
import dao.WaterPipelineSystemDao;
import dao.impl.WaterPipelineNodeDaoImpl;
import dao.impl.WaterPipelineSystemDaoImpl;
import exceptions.DataProcessingException;
import model.Graph;
import model.Route;
import model.WaterPipelineNode;
import model.WaterPipelineSystem;
import service.WaterPipelineSystemService;
import service.impl.WaterPipelineSystemServiceImpl;
import util.CVSUtil;
import util.PropertiesUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException, DataProcessingException, SQLException {
        //Read System file
        CVSUtil cvsUtil = new CVSUtil();
        // Get WaterPipelineSystem after read
        List<WaterPipelineSystem> list = cvsUtil.read(new BufferedReader(new FileReader("src/main/resources/files/waterPiplineSystem.csv")), WaterPipelineSystem.class);

        //Get WaterPipelineNode after read
        List<WaterPipelineNode> waterPipelineNodes = new ArrayList<>();
        for (WaterPipelineSystem waterPipelineSystem : list) {
            waterPipelineNodes.add(waterPipelineSystem.getStartPoint());
            waterPipelineNodes.add(waterPipelineSystem.getEndPoint());
        }
        waterPipelineNodes = waterPipelineNodes.stream().distinct().collect(Collectors.toList());

//////////////////////////////////////////////////////////////////////////////////////////////////////
        ///Save node
        WaterPipelineNodeDao waterPipelineNode = new WaterPipelineNodeDaoImpl();
        service.WaterPipelineNodeService service = new service.impl.WaterPipelineNodeServiceImpl(waterPipelineNode);
        service.save(waterPipelineNodes);

        WaterPipelineSystemDao waterPipelineSystemDao = new WaterPipelineSystemDaoImpl(service);
        WaterPipelineSystemService waterPipelineSystemService = new WaterPipelineSystemServiceImpl(waterPipelineSystemDao);
        waterPipelineSystemService.save(list);
///////////////////////////////////////////////////////////////////////////////////////////////////////
        ///Read Set points
        List<Route> routeList = cvsUtil.read(new BufferedReader(new FileReader(PropertiesUtil.getPropertiesByKey("pathSetOfPoints"))), Route.class);

        ///get Graph
        Graph graph = waterPipelineSystemService.getWaterSystemGraph();
////////////////////////////////////////////////////////////////
        //2;5
        //2;6
        //6;7
        WaterPipelineNode start = new WaterPipelineNode("2");
        WaterPipelineNode target = new WaterPipelineNode("5");
        Route route = new Route(start, target);
        if (graph.isWaterNodeExist(start) && graph.isWaterNodeExist(target)) {
            Map<WaterPipelineNode, HashMap<WaterPipelineNode, Integer>> map = graph.depthFirstTraversal(graph,start);
            Map<WaterPipelineNode, Integer> mapp = graph.getMinLengthByTwoNodes(map, route);
            System.out.println(mapp.get(target));
            System.out.println();
        }
    }
}

