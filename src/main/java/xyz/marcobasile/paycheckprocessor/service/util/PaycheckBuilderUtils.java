package xyz.marcobasile.paycheckprocessor.service.util;

import xyz.marcobasile.paycheckprocessor.model.Paycheck;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class PaycheckBuilderUtils {

    public static Paycheck paycheckFromMap(Map<String, String> map) {

        return Paycheck.builder()
                .ferie(defaultNumericValue(Paycheck.FERIE, map))
                .permessi(defaultNumericValue(Paycheck.PERMESSI, map))
                .netto(defaultNumericValue(Paycheck.NETTO, map))
                .lordo(defaultNumericValue(Paycheck.LORDO, map))
                .imponibileIrpef(defaultNumericValue(Paycheck.LORDO, map))
                .pagaBase(defaultNumericValue(Paycheck.PAGA_BASE, map))
                .meseRetribuzione(defaultStringValue(Paycheck.MESE_RETRIBUZIONE, map))
                .codiceFiscale(defaultStringValue(Paycheck.CODICE_FISCALE, map))
                .nomeCompleto(defaultStringValue(Paycheck.NOME_COMPLETO, map))
                .nascita(defaultStringValue(Paycheck.DATA_NASCITA, map))
                .assunzione(defaultStringValue(Paycheck.ASSUNZIONE, map))
                .codiceAzienda(defaultStringValue(Paycheck.CODICE_AZIENDA, map))
                .matricolaInpsAzienda(defaultStringValue(Paycheck.MATRICOLA_INPS_AZIENDA, map))
                .contoCorrente(defaultStringValue(Paycheck.CONTO_CORRENTE, map))
                .build();
    }

    private static String defaultStringValue(String s, Map<String, String> map) {
        return map.getOrDefault(s, "N/A");
    }

    private static Double defaultNumericValue(String s, Map<String, String> map) {

        return Optional.ofNullable(map.getOrDefault(s, "-1"))
                .filter(d -> !d.equals(""))
                .map(str -> str.replace(".", ""))
                .map(str -> str.replace(",", "."))
                .map(Double::parseDouble)
                .orElse(-1.0);
    }
}