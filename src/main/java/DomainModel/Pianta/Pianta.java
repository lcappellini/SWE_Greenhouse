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

    public Pianta(String tipoPianta) {
        this.tipoPianta = tipoPianta;
    }
}
