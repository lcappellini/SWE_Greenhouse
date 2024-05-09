import java.util.HashMap;
import java.util.Map;

public class Greenhouse {
    Map<Integer, Pianta> plants;
    Map<ParameterType, Sensore> sensorMap;


    public void addPlant(Pianta pianta){
        plants.put(pianta.getId(), pianta);
    }
    public void removePlant(int plantId){
        plants.remove(plantId);
    }

    public float getPlantSensorData(int plantId, ParameterType parameterType){
        return plants.get(plantId).getSensorData(parameterType);
    }

    public float getSharedSensorData(ParameterType parameterType){
        return sensorMap.get(parameterType).getData();
    }

    public Map<Integer, Map<ParameterType, Float>> getAllPlantsSensorData(){
        Map<Integer, Map<ParameterType, Float>> dataMap = new HashMap<>();
        for (Pianta pianta : plants.values()){
            dataMap.put(pianta.getId(), pianta.getAllSensorData());
        }
        return dataMap;
    }

    public Map<ParameterType, Float> getAllSharedSensorData(){
        Map<ParameterType, Float> sensorData = new HashMap<>();
        for (var entry : sensorMap.entrySet()){
            sensorData.put(entry.getKey(), entry.getValue().getData());
        }
        return sensorData;
    }

}
