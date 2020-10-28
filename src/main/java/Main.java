import exceptions.DataProcessingException;
import model.Route;
import model.WaterPipelineNode;
import model.WaterPipelineSystem;
import service.impl.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Service service;
    private static Scanner scanner = new Scanner(System.in);
    private static final String MAINT_PAGE =
            "Please make your choice\n" +
                    "1-Show all nodes\n" +
                    "2-Show water pipeline system into file\n" +
                    "3-Show results\n" +
                    "4-exit\n";

    public static void main(String[] args) throws DataProcessingException, SQLException, IOException {

        service = new Service();
        service.run();
        while (true) {
            System.out.println(MAINT_PAGE);
            System.out.print("Enter your choice :");
            int idxMainMenu = scanner.nextInt();
            switch (idxMainMenu) {
                case 1:
                    showAllNodes();
                    break;
                case 2:
                    showWaterSystem();
                    break;
                case 3:
                    showAllRouts();
                    break;
                case 4:
                    stopApplication();
                    break;
            }
        }
    }

    public static void stopApplication() {
        System.out.println("The system has finished its work");
        System.exit(0);
    }

    public static void showAllNodes() {
        for (WaterPipelineNode waterPipelineNode : service.getWaterNodes()) {
            System.out.println(waterPipelineNode.getNodeName());
        }
    }

    public static void showWaterSystem() {
        System.out.println(WaterPipelineSystem.toString(service.getWaterSystems()));
    }

    public static void showAllRouts() {
        System.out.println(Route.toString(service.getRoutes()));
    }
}
