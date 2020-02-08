package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory;

import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.TokenExtractionConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AOSOBU
 *
 * 01/2020
 */
public class TokenExtractionFactory {

    public static List<String> getTokens(String ticket, List<String> tokens, TokenExtractionConfiguration tokenExtractionConfiguration){

        if(ticket.equals(ExtractionTicket.PROCESS_LOOK_BEHIND_MODE.getValue()))
            return new LookBehindExtractionMode().performExtraction(tokens, tokenExtractionConfiguration);

        return new DefaultMode().performExtraction(tokens, tokenExtractionConfiguration);
    }
}
