package com.hp.caf.api;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;


/**
 * Validates a specific String key is present within a Map.
 */
public class ContainsStringKeysValidator implements ConstraintValidator<ContainsStringKeys, Map<?,?>>
{
    String[] requiredKeys;


    @Override
    public void initialize(final ContainsStringKeys containsStringKeys) {
        requiredKeys = containsStringKeys.keys();

    }

    @Override
    public boolean isValid(final Map<?, ?> map, final ConstraintValidatorContext constraintValidatorContext) {
        for ( String requiredKey : requiredKeys ) {
            if ( !map.containsKey(requiredKey) ) {
                return false;
            }
        }
        return true;
    }
}
