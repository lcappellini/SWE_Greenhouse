package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Pianta;
import main.java.DomainModel.Posizionamento;
import main.java.ORM.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class AdminController {

    public AdminController(){}

    public Settore monitoraSettore(int idSettore, LocalDateTime lt){
        SettoreDAO settoreDAO = new SettoreDAO();
        SensoreDAO sensoreDAO = new SensoreDAO();
        AttuatoreDAO attuatoreDAO = new AttuatoreDAO();

        Settore settore = settoreDAO.getById(idSettore);

        ArrayList<Sensore> sensori = settore.getSensori();
        for (Sensore sensore : sensori) {
            Attuatore attuatoreAssociato = settore.getAttuatoreAssociato(sensore);

            if (attuatoreAssociato != null) {
                // Ottieni il valore della misura
                float misuraVal = sensore.misura(lt, attuatoreAssociato.isWorking());
                sensoreDAO.aggiorna(sensore);

                // Verifica se il valore è nel range valido, altrimenti attiva l'attuatore
                if (settore.isSensorValueInRange(sensore.getTipoSensore(), misuraVal)) {
                    attuatoreAssociato.esegui(0);
                } else {
                    attuatoreAssociato.esegui(1);
                }
                attuatoreDAO.registraAzione(attuatoreAssociato, lt.toString());
            }
        }

        return settore;
    }

    public ArrayList<Map.Entry<Posizione, Pianta>> monitoraPosizioniBySettoreId(int idSettore, LocalDateTime lt){
        PiantaDAO piantaDAO = new PiantaDAO();
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        PosizioneDAO posizioneDAO = new PosizioneDAO();

        ArrayList<Map.Entry<Posizione, Pianta>> posizioni_piante = new ArrayList<>();

        ArrayList<Posizione> posizioni = posizioneDAO.get(Map.of("settore", idSettore));

        SensoreDAO sensoreDAO = new SensoreDAO();
        AttuatoreDAO attuatoreDAO = new AttuatoreDAO();

        for (Posizione posizione : posizioni) {

            IgrometroTerra igrometroTerra = posizione.getIgrometroTerra();
            Irrigatore irrigatore = posizione.getIrrigatore();
            float misuraVal = igrometroTerra.misura(lt, irrigatore.isWorking());

            sensoreDAO.aggiorna(igrometroTerra);

            if (posizione.isSensorValueInRange(igrometroTerra.getTipoSensore(), misuraVal)) {
                irrigatore.esegui(0);
            } else {
                irrigatore.esegui(1);
            }

            attuatoreDAO.registraAzione(irrigatore, lt.toString());

            ArrayList<Posizionamento> posizionamenti = posizionamentoDAO.get(Map.of("posizione", posizione.getId()));
            if (!posizionamenti.isEmpty()){
                Posizionamento posizionamento = posizionamenti.get(0);
                Pianta pianta = piantaDAO.getById(posizionamento.getIdPianta());
                posizioni_piante.add(Map.entry(posizione, pianta));
            }
        }

        return posizioni_piante;
    }

    public ArrayList<Cliente> getClienti(Map<String, Object> criteri) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.get(criteri);
    }

    public ArrayList<Settore> getSettori(Map<String, Object> criteri) {
        SettoreDAO settoreDAO = new SettoreDAO();
        return settoreDAO.get(criteri);
    }
}
