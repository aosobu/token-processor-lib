package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory;

/**
 * Created by AOSOBU
 *
 * 01/2020
 */
public enum ExtractionTicket {
    DEFAULT("DEFAULT_MODE"),
    PROCESS_LOOK_BEHIND_MODE("LOOK_BEHIND_MODE");

    private String value;

    ExtractionTicket(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
