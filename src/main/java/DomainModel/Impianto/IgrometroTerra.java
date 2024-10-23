package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;

public class IgrometroTerra extends Sensore {

    public IgrometroTerra(int id, String dataString, float valore) {
        super(id);
        this.valore = valore;
        if (dataString == null)
            this.data = null;
        else
            this.data = LocalDateTime.parse(dataString);
    }

    @Override
    public float misura(LocalDateTime lt, boolean attuatori_accesi) {
        if (attuatori_accesi) {
            this.valore += 10;
        }
        else {
            this.valore -= (float)(Math.random()*5);
        }
        this.data = lt;
        this.valore = Math.max(0, Math.min(this.valore, 100));
        return valore;
    }

    @Override
    public String getTipoSensore(){
        return "IgrometroTerra";
    }
}

