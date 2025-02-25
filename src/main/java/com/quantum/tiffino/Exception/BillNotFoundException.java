package com.quantum.tiffino.Exception;

import javax.management.RuntimeMBeanException;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(Long id) {
        super("Bill with ID " + id + " not found.");
    }
}
