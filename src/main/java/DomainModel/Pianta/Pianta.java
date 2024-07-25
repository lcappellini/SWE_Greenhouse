package main.java.DomainModel.Pianta;

public class Pianta {
    private int id;
    private String tipoPianta;
    private String descrizione;
    private int minTemp;
    private int maxTemp;
    private int oreLuce;
    private int minAcqua;
    private int maxAcqua;

    public String getTipoPianta() {
        return tipoPianta;
    }

    public Pianta(String tipoPianta) {
        this.tipoPianta = tipoPianta;
    }

    public Pianta(int id, String tipoPianta, String descrizione) {
        this.id = id;
        this.tipoPianta = tipoPianta;
        this.descrizione = descrizione;
    }

    public boolean controlla_stato() {
        boolean stato_buono;

        float probabilitaOttimale = 0.8f; // Valore di base

        // Genera stato
        if (Math.random() < probabilitaOttimale) {
            stato_buono = true;
            this.descrizione = "sta crescendo";
        } else {
            stato_buono = false;
            this.descrizione = "ha bisogno di cura";
        }

        return stato_buono;
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
