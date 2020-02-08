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
public class LookBehindExtractionMode implements TokenExtractor<String, TokenExtractionConfiguration> {
    @Override
    public List<String> performExtraction(List<String> tokens, TokenExtractionConfiguration tokenExtractionConfiguration) {
        List<String> tokenList = new ArrayList();
        int startOffset, endOffset;

        TokenExtractor.validateTokenList(tokens);
        startOffset = TokenExtractor.getStartIndex(tokenExtractionConfiguration, tokens);
        endOffset = TokenExtractor.getEndIndex(tokenExtractionConfiguration, tokens);
        try {
            tokenList = TokenExtractor.returnMatcherGroups(startOffset, endOffset, tokens, tokenExtractionConfiguration, true);
        } catch (CosmosServiceException e) {
            e.printStackTrace();
        }

        tokens = null;
        return tokenList;
    }
}
