package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.tokenextractionfactory;

import com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.TokenExtractionConfiguration;

/**
 * Created by AOSOBU
 *
 * 01/2020
 */
public class ExtractionFactoryTicket {

    public static String getTicket(TokenExtractionConfiguration tokenExtractionConfiguration){// extraction strategies
        if(tokenExtractionConfiguration.getLookBehindMatcherRegexStatus())
            return ExtractionTicket.PROCESS_LOOK_BEHIND_MODE.getValue();

        return ExtractionTicket.DEFAULT.getValue();
    }
}
