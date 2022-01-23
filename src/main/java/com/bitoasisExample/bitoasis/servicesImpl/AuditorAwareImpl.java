package com.bitoasisExample.bitoasis.servicesImpl;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor()
    {
        return Optional.of("Current User");
    }
}
