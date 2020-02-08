package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil;

import com.teamapt.exceptions.CosmosServiceException;
import com.teamapt.fileutils.FileTokenizer;
import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory.ExtractionFactoryTicket;
import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory.TokenExtractionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class allows the extraction of required groups and patterns from a list of tokens
 * This class is dependent on an instance of the TokenExtractionConfiguration class
 *
 * Created by AOSOBU
 */
public class TokenExtractionUtil implements FileExtractor<String> {
    private static final String NULL_SPACE = "";

    public TokenExtractionUtil() {}

    @Override
    public List<List<String>> extractTokens(String file) throws CosmosServiceException, IOException {
        List<List<String>> tokens = new ArrayList();
        FileTokenizer fileTokenizer = new FileTokenizer(file.toString());
        fileTokenizer.setIncludeEmptyRows(false);
        fileTokenizer.setUseMemoryEfficientProcessor(false);
        tokens = fileTokenizer.getTokens();
        return tokens;
    }

    public static List<String> processTokens(TokenExtractionConfiguration tokenExtractionConfiguration, List<List<String>> tokens) throws CosmosServiceException, IOException {
        TokenExtractionConfigurationValidation.validate(tokenExtractionConfiguration);
        return TokenExtractionFactory.getTokens(getExtractionFactoryTicket(tokenExtractionConfiguration),
                preProcessTokens(tokenExtractionConfiguration, tokens, true),
                tokenExtractionConfiguration);
    }

    public static List<String> processTokens(TokenExtractionConfiguration tokenExtractionConfiguration) throws CosmosServiceException, IOException {
        TokenExtractionConfigurationValidation.validate(tokenExtractionConfiguration);
        return TokenExtractionFactory.getTokens(getExtractionFactoryTicket(tokenExtractionConfiguration),
                                                preProcessTokens(tokenExtractionConfiguration, new ArrayList<>(), false),
                                                tokenExtractionConfiguration);
    }

    private static List<String> preProcessTokens(TokenExtractionConfiguration tokenExtractionConfiguration, List<List<String>> tokens, boolean hasTokens) throws CosmosServiceException, IOException {
        if(hasTokens)
            return performTokenPreprocessing(tokens, tokenExtractionConfiguration);
        return performTokenPreprocessing(new TokenExtractionUtil().extractTokens(tokenExtractionConfiguration.getFilePath()), tokenExtractionConfiguration);
    }

    private static List<String> performTokenPreprocessing(List<List<String>> tokens, TokenExtractionConfiguration tokenExtractionConfiguration){
        List<String> lines = new ArrayList();
        tokens.stream()
                .filter(token -> token.toString().replaceAll(tokenExtractionConfiguration.getTokenFormatterRegex(), NULL_SPACE).trim().length() > 0)
                .forEach(token -> {
                    lines.add(token.toString().replaceAll(tokenExtractionConfiguration.getTokenFormatterRegex(), NULL_SPACE));
//                    if(token.toString().contains("Refund"))
                        System.out.println(token.toString().replaceAll(tokenExtractionConfiguration.getTokenFormatterRegex(), NULL_SPACE));
                });
        tokens = null;
        return lines;
    }

    private static String getExtractionFactoryTicket(TokenExtractionConfiguration tokenExtractionConfiguration){
        return ExtractionFactoryTicket.getTicket(tokenExtractionConfiguration);
    }

    public static CompletableFuture<List<String>> processTokensAsync(TokenExtractionConfiguration tokenExtractionConfiguration) throws CosmosServiceException, IOException{
        CompletableFuture executor = new CompletableFuture();
        return executor.supplyAsync(() -> {
                List<String> result = new ArrayList();
                try {
                    result = processTokens(tokenExtractionConfiguration);
                } catch (CosmosServiceException | IOException e) {
                    e.printStackTrace();
                }
                return result;
        });
    }

    public static CompletableFuture<List<String>> processTokensAsync(TokenExtractionConfiguration tokenExtractionConfiguration, List<List<String>> tokens){
        CompletableFuture executor = new CompletableFuture();
        return executor.supplyAsync(() -> {
            List<String> result = new ArrayList();
            try {
                result = processTokens(tokenExtractionConfiguration, tokens);
            } catch (CosmosServiceException | IOException e) {
                e.printStackTrace();
            }
            return result;
        });
    }

}
