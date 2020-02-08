package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil;

import com.teamapt.exceptions.CosmosServiceException;

import java.io.IOException;
import java.util.List;

/**
 * This interfaces defines the contract for all classes that
 * require token extraction
 *
 * Created by AOSOBU
 */
@FunctionalInterface
public interface FileExtractor<T> {

    /**
     * method returns extracted tokens
     * @return
     */
    List<List<T>> extractTokens(T file) throws CosmosServiceException, IOException;
}
