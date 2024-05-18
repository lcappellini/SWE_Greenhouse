package main.java.DomainModel.Pianta;

public class Pianta {
    private String tipoPianta;
    private int minTemp;
    private int maxTemp;
    private int oreLuce;
    private int minAcqua;
    private int maxAcqua;

    public String getTipoPianta() {
        return tipoPianta;
    }


    /*
    private Map<ParameterType, RequiredParameter> requiredParameterMap;
    private Map<ParameterType, Sensore> sensorMap;

    public Pianta(){
        this.sensorMap = Map.of(
                ParameterType.cameraValuation, new Operatore(),
                ParameterType.terrainHumidity, new IgrometroTerra()
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
    */
}
