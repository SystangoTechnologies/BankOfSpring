package com.bankofspring.exception;

/**
 * Created by Arpit Khandelwal.
 */
public class DuplicateEntityException extends EntityException {
    public DuplicateEntityException(Class clazz, String... searchParamsMap) {
        super(clazz, " was already found for parameters ", searchParamsMap);
    }
}
