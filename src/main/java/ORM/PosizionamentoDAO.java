package main.java.ORM;

import main.java.DomainModel.Impianto.Posizionamento;
import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PosizionamentoDAO {

    private Connection connection;

    public PosizionamentoDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void creaPosizionamenti(Ordine ordine) {
        try {
            // Trova il tipoPianta dall'ordine utilizzando l'id dell'ordine
            String tipoPianta = null;
            String queryTipoPianta = "SELECT tipoPianta FROM Ordine WHERE id = ?";
            try (PreparedStatement statementTipoPianta = connection.prepareStatement(queryTipoPianta)) {
                statementTipoPianta.setInt(1, ordine.getId());
                try (ResultSet resultSetTipoPianta = statementTipoPianta.executeQuery()) {
                    if (resultSetTipoPianta.next()) {
                        tipoPianta = resultSetTipoPianta.getString("tipoPianta");
                    }
                }
            }

            // Trova posizioni disponibili dalla tabella Posizioni che abbiano l'attributo assegnato a false
            ArrayList<Integer> posizioniDisponibili = new ArrayList<>();
            String queryPosizione = "SELECT id FROM Posizione WHERE assegnato = false";
            try (PreparedStatement statementPosizione = connection.prepareStatement(queryPosizione);
                 ResultSet resultSetPosizione = statementPosizione.executeQuery()) {
                while (resultSetPosizione.next()) {
                    posizioniDisponibili.add(resultSetPosizione.getInt("id"));
                }
            }

            // Controlla che ci siano abbastanza posizioni disponibili per tutte le piante nell'ordine
            if (tipoPianta != null && !posizioniDisponibili.isEmpty() && posizioniDisponibili.size() >= ordine.getnPiante()) {
                // Aggiungi un posizionamento per ogni pianta nell'ordine
                String queryInserimento = "INSERT INTO Posizionamento (pianta, posizione, ordine) VALUES (?, ?, ?) RETURNING id";
                try (PreparedStatement statementInserimento = connection.prepareStatement(queryInserimento)) {
                    for (int i = 0; i < ordine.getnPiante(); i++) {
                        int idPosizione = posizioniDisponibili.get(i);
                        statementInserimento.setString(1, tipoPianta);
                        statementInserimento.setInt(2, idPosizione);
                        statementInserimento.setInt(3, ordine.getId());
                        statementInserimento.executeUpdate();
                    }
                    System.out.println("Posizionamenti creati con successo!");
                }

            } else {
                System.out.println("Impossibile creare i posizionamenti: tipoPianta non trovato o posizioni insufficienti.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dei posizionamenti: " + e.getMessage());
        }
    }

    public void rimuoviPosizionamentoId(int idPosizionamento) {
        String query = "DELETE FROM Posizionamento WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPosizionamento);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Posizionamento rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione del posizionamento. Nessun posizionamento trovato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione del posizionamento: " + e.getMessage());
        }
    }

    public void rimuoviPosizionamentoPosizione(int idPosizione) {
        String query = "DELETE FROM Posizionamento WHERE posizione = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPosizione);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Posizionamento rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione del posizionamento. Nessun posizionamento trovato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione del posizionamento: " + e.getMessage());
        }
    }
}
