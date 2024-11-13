package main.java.DomainModel;

public enum StatoPianta {
    da_piantare,
    sta_crescendo,
    ha_bisogno_di_cure,
    curata_sta_crescendo,
    da_ritirare;

    public int getId(){
        return this.ordinal();
    }
}
