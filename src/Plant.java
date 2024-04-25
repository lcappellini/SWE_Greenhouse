import java.util.*;

public class Plant {
    private int name;
    private int id;
    private Map<ParameterType, RequiredParameter> requiredParameterMap;
    private Map<ParameterType, Sensor> sensorMap;

    public Plant(){
        this.sensorMap = Map.of(
                ParameterType.cameraValuation, new CameraSensor(),
                ParameterType.terrainHumidity, new TerrainHumiditySensor()
        );
        this.requiredParameterMap = Map.of(
                ParameterType.cameraValuation, new RequiredParameter(), //TODO get data from DB
                ParameterType.terrainHumidity, new RequiredParameter() //TODO get data from DB
        );
    }

    public int getId(){
        return this.id;
    }

    public RequiredParameter getRequiredParameter(ParameterType parameterType){
        return requiredParameterMap.get(parameterType);
    }

    public float getSensorData(ParameterType parameterType){
        return sensorMap.get(parameterType).getData();
    }

    public Map<ParameterType, Float> getAllSensorData(){
        Map<ParameterType, Float> sensorData = new HashMap<>();
        for (var entry : sensorMap.entrySet()){
            sensorData.put(entry.getKey(), entry.getValue().getData());
        }
        return sensorData;
    }

}
