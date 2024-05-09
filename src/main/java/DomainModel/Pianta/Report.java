public class Report {
    private int plantId;
    private ParameterType parameterType;

    private Report(int plantId, ParameterType parameterType) {
        this.plantId = plantId;
        this.parameterType = parameterType;
    }

    public int getPlantId() {
        return plantId;
    }

    public ParameterType getParameterType(){
        return parameterType;
    }
}
