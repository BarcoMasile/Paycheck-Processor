package xyz.marcobasile.paycheckprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class Paycheck {

    public static final String FERIE             = "FERIE RES.";
    public static final String PERMESSI          = "PERMESSI RES.";
    public static final String NETTO             = "NETTOI BUSTA";
    public static final String LORDO             = "TOTALE LORDO";
    public static final String MESE_RETRIBUZIONE = "MESE RETRIBUTTO";
    public static final String CODICE_FISCALE    = "CODICE FISCALE";
    public static final String NOME_COMPLETO     = "COGNOME E NOME";
    public static final String DATA_NASCITA      = "DATA NASCITA";
    public static final String ASSUNZIONE        = "DATA ASSUNZIONE";
    public static final String PAGA_BASE         = "PAGA BASE";
    public static final String CODICE_AZIENDA    = "COD. AZIENDA";

    public static final String IMPONIBILE_IRPEF         = "IMPONIBILE IRPEF";
    public static final String MATRICOLA_INPS_AZIENDA   = "MATRICOLA INPS AZIENDA";
    public static final String CONTO_CORRENTE           = "C/C";

    private final Double ferie;
    private final Double permessi;
    private final Double netto;
    private final Double lordo;
    private final Double imponibileIrpef;

    private final String meseRetribuzione;
    private final Double pagaBase;

    private final String codiceFiscale;
    private final String nomeCompleto;
    private final String nascita;
    private final String assunzione;
    private final String codiceAzienda;
    private final String matricolaInpsAzienda;
    private final String contoCorrente;

    @JsonIgnore
    private final Map<String, String> addizionali;

}