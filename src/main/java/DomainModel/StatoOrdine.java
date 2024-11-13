package main.java.DomainModel;

public enum StatoOrdine {
    da_piantare,
    posizionato,
    da_completare,
    da_ritirare,
    ritirato;

    public int getId(){
        return this.ordinal();
    }

}
