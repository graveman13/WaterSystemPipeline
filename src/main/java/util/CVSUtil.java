package util;

import model.Route;
import model.WaterPipelineNode;
import model.WaterPipelineSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CVSUtil {
    private static final String CSVFORMAT_SEPARATOR = ";";
    private Class clazz;

    public <T> List<T> read(BufferedReader reader, Class clazz) throws IOException {
        this.clazz = clazz;
        List<T> list = new ArrayList<>();
        String line = reader.readLine();
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] fields = line.split(CSVFORMAT_SEPARATOR);
            list.add(setDate(fields));
        }
        reader.close();
        return list;
    }

    public void write(List<Route> routes, FileWriter fileWriter) throws IOException {
        try (fileWriter) {
            fileWriter.append("ROUTE EXISTS,MIN LENGTH" + CSVFORMAT_SEPARATOR +"\n");
            for (Route route : routes) {
                fileWriter.append(route.getRoutExist().name());
                fileWriter.append(CSVFORMAT_SEPARATOR);
                fileWriter.append(route.getLength() == null ? "\n" : route.getLength().toString()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> T setDate(String[] fields) {
        T type = clazz.equals(WaterPipelineSystem.class) ?
                (T) getWaterPipelineSystem(fields)
                : (T) getRoute(fields);
        return type;
    }

    private WaterPipelineNode getNode(String nodeName) {
        return new WaterPipelineNode(nodeName);
    }

    private WaterPipelineSystem getWaterPipelineSystem(String[] fields) {
        return new WaterPipelineSystem(
                getNode(fields[0]),
                getNode(fields[1]),
                Integer.parseInt(fields[2]));
    }

    private Route getRoute(String[] fields) {
        return new Route(getNode(fields[0]), getNode(fields[1]));
    }
}