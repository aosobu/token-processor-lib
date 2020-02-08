package com.teamapt.profectus.moneytransfers.settlementrecon.tokenprocessorutil.validator;

import com.teamapt.exceptions.CosmosServiceException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
/**
 * This class handles the parsing of validation annotations and generation of violation messages
 * Created by AOSOBU
 *
 * 01/2020
 */
public class TokenProcessorValidatorUtil {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     *
     * @param item represents the object to be validated
     * @param fieldName represents the string value of the field to be validated
     * @param <T>
     * @throws CosmosServiceException
     */
    public static <T> void validate(T item, T fieldName)  throws CosmosServiceException{
        Set<ConstraintViolation<T>> violations = validator.validate(item);
        handleFieldViolation(violations, fieldName);
    }

    /**
     *
     * @param violations
     * @param fieldName
     * @param <T>
     * @throws CosmosServiceException
     */
    private static <T> void handleFieldViolation(Set<ConstraintViolation<T>> violations, T fieldName) throws CosmosServiceException {
        ConstraintViolation<T> violation;
        if (!violations.isEmpty()) {
            Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            while(iterator.hasNext()) {
                violation = (ConstraintViolation)iterator.next();
                if (violation.getPropertyPath().toString().equals(fieldName)) {
                    throw new CosmosServiceException(violation.getMessage());
                }
            }
        }
    }

    /**
     *
     * @param violations
     * @param fieldName
     * @param <T>
     * @throws CosmosServiceException
     */
    private static <T> void handleViolations(Set<ConstraintViolation<T>> violations, T fieldName) throws CosmosServiceException {
        if (!violations.isEmpty()) {
            Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            ConstraintViolation<T> violation = (ConstraintViolation)iterator.next();
            throw new CosmosServiceException(violation.getMessage());
        }
    }
}
