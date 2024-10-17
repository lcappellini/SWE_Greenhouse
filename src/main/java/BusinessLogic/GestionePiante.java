package main.java.BusinessLogic;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.ObjectDAO;
import main.java.ORM.PiantaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;


public class GestionePiante {
    PiantaDAO piantaDAO;
    public GestionePiante() {
         piantaDAO = new PiantaDAO();
    }

    public void visualizza(Map<String, Object> criteri){
        ObjectDAO dao = new ObjectDAO();
        dao.visualizza("Pianta", criteri);
    }

    public Pianta restituisciPianta(int id){
        return piantaDAO.getById(id);
    }

    public ArrayList<Pianta> aggiungi(ArrayList<Pianta> piante, int id_ordine) throws SQLException {
        return piantaDAO.inserisci(piante, id_ordine);
    }
    public ArrayList<Pianta> controllaStatoPiante() throws InterruptedException {
        int i = 1;
        Pianta p = piantaDAO.getById(i);
        ArrayList<Pianta> pianteDaCurare = new ArrayList<>();
        while(p != null){
            System.out.println(p.generaStato());
            if(p.haBisogno()){ pianteDaCurare.add(p); }
            sleep(1000);
            i++;
            p = piantaDAO.getById(i);
        }
        return pianteDaCurare;
    }
    public void aggiornaDescrizione(int idPianta, String descrizione) {
        piantaDAO.aggiornaDescrizione(idPianta, descrizione);
    }
/*
    public void visualizzaPiante(Map<String, Object> criteri) {

        ArrayList<Pianta> piante = piantaDAO.getById(criteri);

        if (piante.isEmpty()) {
            System.out.println("Non sono state trovate piante");
            return;
        }

        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.println("|   ID   | Tipo    | Piantato   | Stato  | Costo   | Descrizione                   |");
        System.out.println("|--------|---------|------------|--------|---------|-------------------------------|");

        for (Pianta p : piante) {
            // Ottieni la stringa delle piante
            String piante = p.getDescrizione();

            // Suddividi la stringa in righe di massimo 14 caratteri
            List<String> pianteLinee = spezzaStringa(piante, 24);

            // Stampa la prima riga con i dati principali
            System.out.printf("| %-6d | %-7d | %-10s | %-6s | %-14s | %-24s |\n",
                    p.getId(), p.getTipoPianta(), p.getDataInizio().toString(), p.getCosto(), p.getDescrizione()
            );

            // Stampa le righe successive con le piante spezzate, mantenendo gli altri campi vuoti
            for (int i = 1; i < pianteLinee.size(); i++) {
                System.out.printf("| %-6s | %-7s | %-10s | %-6s | %-14s | %-24s |\n", "", "", "", "", "", pianteLinee.get(i));
            }
        }

        System.out.println("+------------------------------------------------------------------------------------+");
    }
*/
    // Metodo per spezzare una stringa in linee di lunghezza massima
    private List<String> spezzaStringa(String s, int maxLunghezza) {
        List<String> linee = new ArrayList<>();
        int lunghezza = s.length();

        for (int i = 0; i < lunghezza; i += maxLunghezza) {
            linee.add(s.substring(i, Math.min(lunghezza, i + maxLunghezza)));
        }

        return linee;
    }

    public void elimina(int id_ordine) throws SQLException {
        if(piantaDAO.elimina(id_ordine)){
            System.out.println("Le piante dell'ordine "+id_ordine+" sono state rimosse.");
        }else{
            System.out.println("Rimozione piante non effettuata.");
        }
    }
}
