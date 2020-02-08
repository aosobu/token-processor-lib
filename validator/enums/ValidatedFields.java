package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.validator.enums;

/**
 * Created by AOSOBU
 *
 * 01/2020
 */
public enum ValidatedFields {

    startOffset("startOffset"),
    endOffset("endOffset"),
    tokenFormatterRegex("tokenFormatterRegex"),
    filePath("filePath"),
    lookBehindMatcherRegex("lookBehindMatcherRegex"),
    batchIdentifier("batchIdentifier"),
    matcherGroupRegex("matcherGroupRegex");

    private String field;

    ValidatedFields(String field){
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
