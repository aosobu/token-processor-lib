package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory;

import com.teamapt.exceptions.CosmosServiceException;
import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.TokenExtractionConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AOSOBU
 *
 * 01/2020
 */
public class DefaultMode implements TokenExtractor<String, TokenExtractionConfiguration> {

    @Override
    public List<String> performExtraction(List<String> tokens, TokenExtractionConfiguration tokenExtractionConfiguration) {
        List<String> tokenList = new ArrayList();
        int startIndex, endIndex;

        TokenExtractor.validateTokenList(tokens);
        startIndex = TokenExtractor.getStartIndex(tokenExtractionConfiguration, tokens);
        endIndex = TokenExtractor.getEndIndex(tokenExtractionConfiguration, tokens);
        try {
            tokenList = TokenExtractor.returnMatcherGroups(startIndex, endIndex, tokens, tokenExtractionConfiguration, false);
        } catch (CosmosServiceException e) {
            e.printStackTrace();
        }

        tokens = null;
        return tokenList;
    }
}
