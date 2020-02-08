package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil;

import org.hibernate.validator.constraints.NotBlank;

/**
 *This class defines the configuration requirement for token processing
 *
 * Created by AOSOBU
 */
public class TokenExtractionConfiguration {

    /**
     * This specifies the path to the file to be extracted
     */
    @NotBlank(message = "file path cannot be empty")
    private String filePath;

    /**
     * This specifies the start position for token extraction
     */
    @NotBlank(message = "if set, startOffset value cannot be empty")
    private String startOffset;

    /**
     * This specifies the end index for stopping the token
     */
    @NotBlank(message = "if set, endOffSet value cannot be blank")
    private String endOffset;

    /**
     *
     */
    @NotBlank(message = "matcherGroupRegex cannot be empty")
    private String matcherGroupRegex;

    /**
     *
     */
    @NotBlank(message = "if set, lookBehindMatcherRegex cannot be empty")
    private String lookBehindMatcherRegex;

    /**
     *
     */
    @NotBlank(message = "token formatter regex cannot be empty")
    private String tokenFormatterRegex = "[\\[\\]\\,]";

    /**
     *
     */
    private Boolean startOffsetStatus = false;

    /**
     *
     */
    private Boolean endOffSetStatus = false;

    /**
     *
     */
    private Boolean matcherGroupRegexStatus = false;

    /**
     *
     */
    private Boolean lookBehindMatcherRegexStatus = false;

    /**
     * This specifies if asynchronous processing of tokens is required
     */
    private Boolean isAsyncProcessing = false;

    /**
     * This specifies the condition for creating batches from matcher group list
     */
    @NotBlank(message = "batch identifier cannot be empty")
    private String batchIdentifier;

    private int offsetIgnoreStep;

    private String startOffIdentifierSalt;

    private String endOffIdentifierSalt;

    /**
     *
     */
    private boolean ignoreOffsetStepStatus = false;

    public TokenExtractionConfiguration() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(String startOffset) {
        this.startOffset = startOffset;
        setStartOffsetStatus(true);
    }

    public String getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(String endOffset) {
        this.endOffset = endOffset;
        setEndOffSetStatus(true);
    }

    public String getMatcherGroupRegex() {
        return matcherGroupRegex;
    }

    public void setMatcherGroupRegex(String matcherGroupRegex) {
        this.matcherGroupRegex = matcherGroupRegex;
        setMatcherGroupRegexStatus(true);
    }

    public String getTokenFormatterRegex() {
        return tokenFormatterRegex;
    }

    public void setTokenFormatterRegex(String tokenFormatterRegex) {
        this.tokenFormatterRegex = tokenFormatterRegex;
    }

    public Boolean getStartOffsetStatus() {
        return startOffsetStatus;
    }

    public void setStartOffsetStatus(Boolean startOffsetStatus) {
        this.startOffsetStatus = startOffsetStatus;
    }

    public Boolean getEndOffSetStatus() {
        return endOffSetStatus;
    }

    public void setEndOffSetStatus(Boolean endOffSetStatus) {
        this.endOffSetStatus = endOffSetStatus;
    }

    public Boolean getMatcherGroupRegexStatus() {
        return matcherGroupRegexStatus;
    }

    public void setMatcherGroupRegexStatus(Boolean matcherGroupRegexStatus) {
        this.matcherGroupRegexStatus = matcherGroupRegexStatus;
    }

    public Boolean getAsyncProcessing() {
        return isAsyncProcessing;
    }

    public void setAsyncProcessing(Boolean asyncProcessing) {
        isAsyncProcessing = asyncProcessing;
    }

    public String getBatchIdentifier() {
        return batchIdentifier;
    }

    public void setBatchIdentifier(String batchIdentifier) {
        this.batchIdentifier = batchIdentifier;
    }

    public String getLookBehindMatcherRegex() {
        return lookBehindMatcherRegex;
    }

    public void setLookBehindMatcherRegex(String lookBehindMatcherRegex) {
        this.lookBehindMatcherRegex = lookBehindMatcherRegex;
        setLookBehindMatcherRegexStatus(true);
    }

    public Boolean getLookBehindMatcherRegexStatus() {
        return lookBehindMatcherRegexStatus;
    }

    public void setLookBehindMatcherRegexStatus(Boolean lookBehindMatcherRegexStatus) {
        this.lookBehindMatcherRegexStatus = lookBehindMatcherRegexStatus;
    }

    public int getOffsetIgnoreStep() {
        return offsetIgnoreStep;
    }

    public void setOffsetIgnoreStep(int offsetIgnoreStep) {
        this.offsetIgnoreStep = offsetIgnoreStep;
        setIgnoreOffsetStepStatus(true);
    }

    public String getStartOffIdentifierSalt() {
        return startOffIdentifierSalt;
    }

    public void setStartOffIdentifierSalt(String startOffIdentifierSalt) {
        this.startOffIdentifierSalt = startOffIdentifierSalt;
    }

    public String getEndOffIdentifierSalt() {
        return endOffIdentifierSalt;
    }

    public void setEndOffIdentifierSalt(String endOffIdentifierSalt) {
        this.endOffIdentifierSalt = endOffIdentifierSalt;
    }

    public boolean isIgnoreOffsetStepStatus() {
        return ignoreOffsetStepStatus;
    }

    public void setIgnoreOffsetStepStatus(boolean ignoreOffsetStepStatus) {
        this.ignoreOffsetStepStatus = ignoreOffsetStepStatus;
    }
}
