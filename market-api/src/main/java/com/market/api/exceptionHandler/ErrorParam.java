package com.market.api.exceptionHandler;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ErrorParam {

    private final String message;
    private Map<String, String> errors = new ConcurrentHashMap<>();
}
