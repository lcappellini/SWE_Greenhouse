package main.java.DomainModel.Impianto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class IgrometroTerra extends Sensore<Integer> {
    LocalDateTime lt;
    public IgrometroTerra(int id) {
        super(id); // Chiama il costruttore della classe Sensore
    }


    public IgrometroTerra(int id, int percAcqua, String data) {
        super(id);
        this.valore = percAcqua;
        if(data != null){
            LocalDateTime lt = LocalDateTime.parse(data);
        }else{
            lt = LocalDateTime.now();
        }
    }

    @Override
    public Integer misura(LocalDateTime lt, boolean attuatori_accesi) {
        this.valore = 30 + (int)Math.random() * 70;
        return 0;
    }

    @Override
    public String tipoSensore(){
        return "IgrometroTerra";
    }
}

