package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory;

import com.teamapt.exceptions.CosmosServiceException;
import com.teamapt.profectus.commons.lib.services.ProfectusStringUtils;
import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.TokenExtractionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AOSOBU
 *
 * 01/2020
 */
public interface TokenExtractor<T, M extends TokenExtractionConfiguration> {

    List<T> performExtraction(List<T> tokens, M tokenExtractionConfiguration);

    static<T> void validateTokenList(List<T> tokens){
        if(tokens.isEmpty()) {
            try {
                throw new CosmosServiceException(FactoryConstants.EMPTY_TOKEN_LIST.getValue());
            } catch (CosmosServiceException e) {
                e.printStackTrace();
            }
        }
    }

    static List<String> returnMatcherGroups(int startIndex, int endIndex, List<String> tokens,
                                            TokenExtractionConfiguration tokenExtractionConfiguration, boolean lookBehindStatus) throws CosmosServiceException {
        return TokenExtractorUtil.returnMatcherGroups(startIndex, endIndex,  tokens, tokenExtractionConfiguration, lookBehindStatus);
    }

    static int getOffsetIndex(List<String> tokens, String offsetPattern, boolean offsetStatus, int ignoreOffset, String uniqueIdentifier, boolean ignoreOffsetStepStatus){
        return TokenExtractorUtil.getOffsetIndex(tokens, offsetPattern, offsetStatus, ignoreOffset, uniqueIdentifier, ignoreOffsetStepStatus);
    }

    static int getStartIndex(TokenExtractionConfiguration tokenExtractionConfiguration, List<String> tokens){
        return TokenExtractorUtil.getStartIndex(tokenExtractionConfiguration, tokens);
    }

    static int getEndIndex(TokenExtractionConfiguration tokenExtractionConfiguration, List<String> tokens){
        return TokenExtractorUtil.getEndIndex(tokenExtractionConfiguration, tokens);
    }

}
