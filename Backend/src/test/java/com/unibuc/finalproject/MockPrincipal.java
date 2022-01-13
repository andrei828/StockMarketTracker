package com.unibuc.finalproject;

import java.security.Principal;

public class MockPrincipal implements Principal {
    @Override
    public String getName() {
        return "mock";
    }
}
