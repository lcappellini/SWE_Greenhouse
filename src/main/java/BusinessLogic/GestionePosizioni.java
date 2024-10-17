package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Posizione;
import main.java.ORM.PosizioneDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionePosizioni {

    PosizioneDAO  posizioneDAO;

    public GestionePosizioni(){
        posizioneDAO = new PosizioneDAO();
    }

    public ArrayList<Posizione> getPosizioniBySettore(int id_settore){
        return  posizioneDAO.getPosizioniBySettore(id_settore);
    }



    //public void visualizzaPosizioni(int idAmbiente) {posizioneDAO.visualizzaPosizioni(idAmbiente);}
    public void visualizzaPosizioni(int idSettore) {


        ArrayList<Posizione> pos = posizioneDAO.getPosizioniBySettore(idSettore); // Supponiamo che il metodo recuperi i settori legati allo spazio

        if (pos.isEmpty()) {
            System.out.println("  N/A   "); // Se non ci sono settori, stampa N/A
        } else {
            System.out.println("+------------------------------------------------------------------------+");
            System.out.println("|   ID   | Assegnata | Occupata | Settore | Igrometro Terr. | Irrigatore |");
            System.out.println("|--------|-----------|----------|---------|-----------------|------------|");
            // Ciclo che continua fino a quando non ci sono più settori
            for(Posizione p : pos){
                System.out.printf("| %-6d | %-9s | %-8s | %-7d | %-15s | %-10s |\n",
                        p.getId(),
                        (p.isAssegnata() ? "Sì" : "No"),
                        (p.isOccupata() ? "Sì" : "No"),
                        idSettore,
                        (p.getIgrometroTerra() != null ? p.getIgrometroTerra().getId() : "N/A"),
                        (p.getIrrigatore() != null ? (p.getIrrigatore().isWorking() ? "ON" : "OFF") : "N/A"));
            }
        }
        System.out.println("+------------------------------------------------------------------------+");
    }

    public void monitoraPosizone(int idPosizione) {
        posizioneDAO.monitoraPosizione(idPosizione);
    }
    public void modificaPosizione(int idPosizione, String query, String valore, String attributo) {
        posizioneDAO.modificaPosizione(idPosizione, query, valore, attributo);
    }

    public void liberaPosizioni(List<Integer> posizioni) {
        posizioneDAO.liberaPosizioni(posizioni);
    }

    public List<Posizione> getPosizioniLibere(int i) {
        //Posizioni da occupare
        Map<String, Object> m = new HashMap<>();
        m.put("assegnata", true);
        m.put("occupata", false);
        ArrayList<Posizione> posizioni = posizioneDAO.get(m);
        if(posizioni.isEmpty()){
            System.out.println("Non ci sono posizioni libere!");
            return null;
        } else if (posizioni.size() < i) {
            System.out.println("Posizioni libere insufficienti");
            return null;
        }
        return posizioni.subList(0,i);
    }

    public void libera(Posizione posizione) throws SQLException {
        Map<String, Object> m = new HashMap<>();
        m.put("assegnata", false);
        m.put("occupata", false);
        posizioneDAO.aggiorna(posizione.getId(), m);
    }

    public void occupa(List<Posizione> posizioni) throws SQLException {
        Map<String, Object> m = new HashMap<>();
        m.put("occupata", true);
        for(Posizione p : posizioni){
            posizioneDAO.aggiorna(p.getId(), m);
        }
    }
}
