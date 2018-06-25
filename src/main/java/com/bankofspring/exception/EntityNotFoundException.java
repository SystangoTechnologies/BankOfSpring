package com.bankofspring.exception;

/**
 * Created by Arpit Khandelwal.
 */
public class EntityNotFoundException extends EntityException {

    public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(clazz, " was not found for parameters ", searchParamsMap);
    }
}
