package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory;

/**
 * Created by AOSOBU
 *
 * 01/2020
 */
public enum FactoryConstants {

    BATCH_SIZE("Batch::"),
    COMMA(","),
    EMPTY_TOKEN_LIST("token list cannot be empty");

    private static int batchCount;
    private String value;

    FactoryConstants(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static int getBatchCount() {
        return --batchCount;
    }

    public static void setBatchCount(int batchCount) {
        FactoryConstants.batchCount = batchCount;
    }
}
