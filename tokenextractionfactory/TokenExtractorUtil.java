package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory;

import com.teamapt.exceptions.CosmosServiceException;
import com.teamapt.profectus.commons.lib.services.ProfectusStringUtils;
import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.TokenExtractionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds the utility methods for the TokenExtractor interface
 */
public  abstract class TokenExtractorUtil {
    static Logger logger = LoggerFactory.getLogger(TokenExtractor.class);
    private static HashMap<String, List<String>> lookBehindMap = new HashMap<>();;

    public static List<String> returnMatcherGroups(int startIndex, int endIndex, List<String> tokens,
                                                   TokenExtractionConfiguration tokenExtractionConfiguration, boolean lookBehindStatus) throws CosmosServiceException {
        List<String> tokenList = new ArrayList();
        Pattern pattern = Pattern.compile(tokenExtractionConfiguration.getMatcherGroupRegex(), Pattern.CASE_INSENSITIVE);
        Pattern batcherPattern = Pattern.compile(tokenExtractionConfiguration.getBatchIdentifier(),Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        Matcher batched;
        Matcher lookBehindMatcher;
        int batchSize = 1;
        String line, lookBehind = "";
        boolean isLookBehindMatcher = false;
        boolean isListStatus = false;

        for(int i=startIndex; i<endIndex; i++){
            try{
                line = tokens.get(i);
                matcher = pattern.matcher(line);

                if(lookBehindStatus) {
                    Pattern lookBehindPattern = Pattern.compile(tokenExtractionConfiguration.getLookBehindMatcherRegex(), Pattern.CASE_INSENSITIVE);
                    lookBehindMatcher = lookBehindPattern.matcher(line);
                    if(lookBehindMatcher.find()) {
                        isLookBehindMatcher = true;
                        lookBehind = getLookBehindValue(tokenExtractionConfiguration.getLookBehindMatcherRegex(), line);
                        lookBehindMatcher.reset();
                    }
                }

                while(matcher.find()){
                    int groupCount = matcher.groupCount();
                    for(int j=1; j<=groupCount; j++) {
                        tokenList.add(matcher.group(j));
                        isListStatus = true;
                    }
                }

                batched = batcherPattern.matcher(line);
                if(!ProfectusStringUtils.isEmpty(tokenExtractionConfiguration.getBatchIdentifier()) &&
                        batched.find()) {
                    tokenList.add(FactoryConstants.BATCH_SIZE.getValue() + batchSize++);
                }

                if(lookBehindStatus && isListStatus) {
                    createLookBehindHashMap(lookBehindStatus, isLookBehindMatcher, isListStatus, lookBehind, tokenList);
                    isListStatus = false;
                    tokenList.clear();
                }

            }catch(IndexOutOfBoundsException ie){
                logger.info(ie.getMessage());
            }
        }

        FactoryConstants.setBatchCount(batchSize);
        return tokenList;
    }

    public static String getLookBehindValue(String pattern, String line) {
        Pattern lookBehindPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher lookBehindMatcher = lookBehindPattern.matcher(line);
        StringBuilder lookBehind = new StringBuilder();

        while(lookBehindMatcher.find()){
            int groupCount = lookBehindMatcher.groupCount();
            for(int j=1; j<=groupCount; j++)
                lookBehind.append(lookBehindMatcher.group(j) + FactoryConstants.COMMA.getValue());
        }

        return lookBehind.toString();
    }

    public static HashMap<String, List<String>> createLookBehindHashMap(boolean lookBehindStatus, boolean isLookBehindMatcher, boolean isListStatus, String lookBehind, List<String> tokens){
        List<String> tokensCopy = new ArrayList<>(tokens);

        if(lookBehindStatus && isLookBehindMatcher && isListStatus) {
            if(lookBehindMap.containsKey(lookBehind)){
                List<String> values = lookBehindMap.get(lookBehind);
                tokensCopy.forEach(token -> {
                    values.add(token);
                });
                lookBehindMap.put(lookBehind, values);
            }else
                lookBehindMap.put(lookBehind, tokensCopy);
        }

//        map.compute("Key1", (key, val)
//                -> (val == null)
//                ? 1
//                : val + 1);

        return lookBehindMap;
    }

    public static int getOffsetIndex(List<String> tokens, String offsetPattern, boolean offsetStatus, int ignoreOffset, String uniqueIdentifier, boolean ignoreOffsetStepStatus){
        int index = 0;
        int tokenSize = tokens.size();
        StringBuilder builder = new StringBuilder();

        if(offsetStatus) {
            Pattern pattern = Pattern.compile(offsetPattern, Pattern.CASE_INSENSITIVE);
            Matcher matcher;
            for(int i=0; i<tokenSize; i++){
                String line = tokens.get(i);
                matcher = pattern.matcher(line);
                if(matcher.find()) {
                    if(ignoreOffsetStepStatus) {
                        if (ignoreOffset == 0) {
                            index = i;
                            return index;
                        }
                        if (ignoreOffset > 0) {
                            ignoreOffset--;
                            continue;
                        }
                    }
                }
                matcher = pattern.matcher(line);
                while(matcher.find()){
                    int groupCount = matcher.groupCount();
                    for(int j=1; j<=groupCount; j++) {
                        builder.append(matcher.group(j).trim());
                        if(ProfectusStringUtils.trimAllWhitespace(builder.toString()).equalsIgnoreCase(ProfectusStringUtils.trimAllWhitespace(uniqueIdentifier))){
                            index = i;
                            return index;
                        }
                    }
                    builder = new StringBuilder("");
                }
            }
        }

        return index;
    }

    public static int getStartIndex(TokenExtractionConfiguration tokenExtractionConfiguration, List<String> tokens){
        return tokenExtractionConfiguration.getStartOffsetStatus() ?
                TokenExtractor.getOffsetIndex(tokens, tokenExtractionConfiguration.getStartOffset(),
                        tokenExtractionConfiguration.getStartOffsetStatus(), tokenExtractionConfiguration.getOffsetIgnoreStep(),
                        tokenExtractionConfiguration.getStartOffIdentifierSalt(), tokenExtractionConfiguration.isIgnoreOffsetStepStatus()) : 0;
    }

    public static int getEndIndex(TokenExtractionConfiguration tokenExtractionConfiguration, List<String> tokens){
        return tokenExtractionConfiguration.getEndOffSetStatus() ?
                TokenExtractor.getOffsetIndex(tokens, tokenExtractionConfiguration.getEndOffset(),
                        tokenExtractionConfiguration.getEndOffSetStatus(), tokenExtractionConfiguration.getOffsetIgnoreStep(),
                        tokenExtractionConfiguration.getEndOffIdentifierSalt(), tokenExtractionConfiguration.isIgnoreOffsetStepStatus()) : tokens.size();
    }

    public static HashMap<String, List<String>> getLookBehindMap() {
        return lookBehindMap;
    }

    public static void setLookBehindMap() {
        TokenExtractorUtil.lookBehindMap = new HashMap<>();
    }

}
