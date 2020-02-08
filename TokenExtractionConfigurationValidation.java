package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil;

import com.teamapt.exceptions.CosmosServiceException;
import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.validator.TokenProcessorValidatorUtil;
import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.validator.enums.ValidatedFields;

/**
 * This class validates required fields from an instance of the TokenExtractionConfiguration class
 *
 * Created by AOSOBU
 */
public class TokenExtractionConfigurationValidation {

    public static void validate(TokenExtractionConfiguration tokenExtractionConfiguration) throws CosmosServiceException {

        //Mandatory field check
        validateField(tokenExtractionConfiguration, ValidatedFields.filePath.getField());
        validateField(tokenExtractionConfiguration, ValidatedFields.tokenFormatterRegex.getField());
        validateField(tokenExtractionConfiguration, ValidatedFields.batchIdentifier.getField());
        validateField(tokenExtractionConfiguration, ValidatedFields.matcherGroupRegex.getField());

        if(tokenExtractionConfiguration.getStartOffsetStatus())
            validateField(tokenExtractionConfiguration, ValidatedFields.startOffset.getField());

        if(tokenExtractionConfiguration.getEndOffSetStatus())
            validateField(tokenExtractionConfiguration, ValidatedFields.endOffset.getField());

        if(tokenExtractionConfiguration.getLookBehindMatcherRegexStatus())
            validateField(tokenExtractionConfiguration, ValidatedFields.lookBehindMatcherRegex.getField());
    }

    private static void validateField(TokenExtractionConfiguration tokenExtractionConfiguration, String field) throws CosmosServiceException {
        try {
            TokenProcessorValidatorUtil.validate(tokenExtractionConfiguration,
                    tokenExtractionConfiguration.getClass().getDeclaredField(ValidatedFields.valueOf(field).getField()).getName());
        } catch (NoSuchFieldException | CosmosServiceException e) {
            throw new CosmosServiceException(e.getMessage());
        }
    }

}
