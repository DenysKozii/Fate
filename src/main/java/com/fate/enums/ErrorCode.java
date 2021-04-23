package com.fate.enums;

public enum ErrorCode {
    UNAUTHORIZED,
    INVALID_CREDENTIALS,
    NOT_CONFIRMED,
    BLOCKED,
    INVALID_TOKEN,
    EXPIRED_TOKEN,
    EMAIL_IS_ALREADY_USED,
    INVALID_BETA_TESTER_KEY,
    BETA_TESTER_KEY_IS_ALREADY_USED,
    TOO_MANY_EMAILS,
    PICKED_COUNT_EXCEEDS_LIMIT,
    VALIDATION,
    SQL;
}