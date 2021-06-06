package com.company.test.utility;

import org.springframework.security.core.context.SecurityContextHolder;

public final class CustomerUtility {

    private static final String READER = "READER";

    private static final String WRITER = "WRITER";

    public static boolean hasWriteRights() {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals(WRITER);
    }

    public static boolean hasReaderRights() {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals(READER);
    }

    public static void cleanUp() {
        SecurityContextHolder.clearContext();
    }
}
