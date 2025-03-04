package com.justinquinnb.onefeed.data.storage;

import com.justinquinnb.onefeed.data.exceptions.TokenEntryNotFound;
import com.justinquinnb.onefeed.data.model.token.TokenEntry;
import com.justinquinnb.onefeed.data.model.token.TokenStorage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class SampleTokenStorage implements TokenStorage {
    // TODO implement
    @Override
    public void addTokenEntryFor(TokenEntry tokenEntry) {

    }

    @Override
    public void removeTokenEntryFor(String contentSourceId) throws TokenEntryNotFound {

    }

    @Override
    public void updateTokenEntryFor(String contentSourceId, String newAccessToken) throws TokenEntryNotFound {

    }

    @Override
    public TokenEntry getTokenEntryFor(String contentSourceId) throws TokenEntryNotFound {
        return null;
    }

    @Override
    public String getAccessTokenFor(String contentSourceId) throws TokenEntryNotFound {
        return "";
    }

    @Override
    public Optional<Instant> getExpirationTimeFor(String contentSourceId) throws TokenEntryNotFound {
        return Optional.empty();
    }

    @Override
    public Instant getLastUpdatedTimeFor(String contentSourceId) throws TokenEntryNotFound {
        return null;
    }

    @Override
    public boolean tokenEntryExistsFor(String contentSourceId) {
        return false;
    }
}
