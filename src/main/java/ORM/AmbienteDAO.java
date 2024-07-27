package main.java.ORM;

import main.java.DomainModel.Impianto.Ambiente;

import java.sql.*;
import java.util.ArrayList;

public class AmbienteDAO {

    private Connection connection;

    public  AmbienteDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public void creaAmbiente(int idSpazio, int nPosizioniMax,
                           int idTermometro, int idFotosensore, int idIgrometroAria,
                           int idClimatizzazione, int idLampada) {
        String insertAmbienteSQL = "INSERT INTO \"Ambiente\" (spazio_id, nPosizioniMax, termometro, " +
                "fotosensore, igrometroAria, climatizzazione, lampada) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertAmbienteSQL)) {
            // Assegna una posizione. Potrebbe essere calcolata o generata in qualche modo.

            insertStatement.setInt(1, idSpazio);
            insertStatement.setInt(2, nPosizioniMax);
            insertStatement.setInt(3, idTermometro);
            insertStatement.setInt(4, idFotosensore);
            insertStatement.setInt(5, idIgrometroAria);
            insertStatement.setInt(6, idClimatizzazione);
            insertStatement.setInt(7, idLampada);

            insertStatement.executeUpdate();
            System.out.println("Ambiente creato correttamente e associato all'spazio con ID: " + idSpazio);

        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dello ambiente: " + e.getMessage());
        }
    }


    public void visualizzaAmbienti(int idSpazio) {
        //FIXME Qui c'è da far vedere lo Ambiente con i suoi attributi e gli ID delle posizioni che ha
        String query = "SELECT * FROM \"Ambiente\" WHERE spazio_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Stampiamo l'intestazione
            System.out.println("+--------+------------+-------------------+");
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("| %-10s ", metaData.getColumnName(i));
            }
            System.out.println("|");
            System.out.println("+--------+------------+-------------------+");

            // Stampiamo le righe
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("| %-10s ", resultSet.getString(i));
                }
                System.out.println("|");
            }
            System.out.println("+--------+------------+-------------------+");

        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione degli ambienti: " + e.getMessage());
        }
    }


    public void rimuoviAmbiente(int idAmbiente) {
        String query = "DELETE FROM \"Ambiente\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAmbiente);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ambiente rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione dello ambiente. Nessuno ambiente trovato con l'ID specificato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione dello ambiente: " + e.getMessage());
        }
    }


    public void monitoraAmbiente(int idAmbiente) {
        //TODO monitoraggio dello Ambiente con aggiornamento ogni secondo (1 ora in simulazione) dove verranno
        //TODO [...] mostrate i parametri della temperatura , percentuale di luce e di umidità. Inoltre mostrerà,
        //TODO [...] in caso siano accesi, il valore di temperatura e umidità impostato dal climatizzatore e se
        //TODO [...] la lampada sia accesa
        String query = "SELECT " +
                "T.temperatura, " +
                "U.perc_acqua, " +
                "F.perc_luce, " +
                "C.acceso AS climatizzatore_acceso, " +
                "L.acceso AS lampada_acceso " +
                "FROM \"Ambiente\" S " +
                "LEFT JOIN \"Termometro\" T ON S.termometro = T.id " +
                "LEFT JOIN \"IgrometroAria\" U ON S.igrometroAria = U.id " +
                "LEFT JOIN \"Fotosensore\" F ON S.fotosensore = F.id " +
                "LEFT JOIN \"Climatizzatore\" C ON S.climatizzazione = C.id " +
                "LEFT JOIN \"Lampada\" L ON S.lampada = L.id " +
                "WHERE S.id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idAmbiente);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int temperatura = rs.getInt("temperatura");
                int percAcqua = rs.getInt("perc_acqua");
                int percLuce = rs.getInt("perc_luce");
                boolean climatizzatoreAcceso = rs.getBoolean("climatizzatore_acceso");
                boolean lampadaAccesa = rs.getBoolean("lampada_acceso");

                // Stampare i risultati in formato tabellare
                System.out.println("+----------------------+");
                System.out.println("| Parametro            | Valore                |");
                System.out.println("+----------------------+-----------------------+");
                System.out.printf("| Temperatura          | %-21d|\n", temperatura);
                System.out.printf("| Percentuale Acqua    | %-21d|\n", percAcqua);
                System.out.printf("| Percentuale Luce     | %-21d|\n", percLuce);
                System.out.printf("| Climatizzazione Acceso| %-21s|\n", climatizzatoreAcceso ? "Sì" : "No");
                System.out.printf("| Lampada Accesa       | %-21s|\n", lampadaAccesa ? "Sì" : "No");
                System.out.println("+----------------------+-----------------------+");
            } else {
                System.out.println("Nessun dato trovato per lo ambiente con ID: " + idAmbiente);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }



    public ArrayList<Ambiente> completaSpazio(int idSpazio) {
        String query = "SELECT * FROM \"Ambiente\" WHERE spazio_id = ?";
        ArrayList<Ambiente> ambienti = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int nPosizioniMax = resultSet.getInt("nPosizioniMax");
                    ambienti.add(new Ambiente(id, nPosizioniMax));
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il completamento dell'spazio: " + e.getMessage());
        }

        return ambienti;
    }


    public Ambiente getAmbiente(int idAmbiente) {
        String query = "SELECT * FROM \"Ambiente\" WHERE id = ?";
        Ambiente ambiente = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAmbiente);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int nPosMax = resultSet.getInt("nPosizioniMax");
                    int idTermometro = resultSet.getInt("termometro");
                    if( idTermometro>0){
                        int idIgrometro = resultSet.getInt("igrometroAria");
                        int idFotosensore = resultSet.getInt("fotosensore");
                        int idClimatizzatore = resultSet.getInt("climatizzazione");
                        int idLampada = resultSet.getInt("lampada");
                        ambiente = new Ambiente(idAmbiente, nPosMax, idTermometro, idIgrometro,
                                idFotosensore, idClimatizzatore, idLampada);
                    }else {
                        ambiente = new Ambiente(idAmbiente, nPosMax);
                    }

                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dello ambiente: " + e.getMessage());
        }

        return ambiente;
    }


    public void visualizzaAmbiente() {
    }
}
