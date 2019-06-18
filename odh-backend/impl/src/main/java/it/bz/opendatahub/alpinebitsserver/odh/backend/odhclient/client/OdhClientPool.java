/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebitsserver.odh.backend.odhclient.client;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.function.Function;

/**
 * This pool manages {@link OdhClientImpl} instances.
 * <p>
 * Instances of the OdhClientImpl are keyed by a given credential.
 * <p>
 * If no client exists for a given username and password combination,
 * a new client will be created and inserted into the pool.
 * <p>
 * If a username and password combination exists in the pool the according
 * OdhClientImpl instance is returned for usage. In normal circumstances, such
 * an OdhClientImpl will already be authenticated, thus reducing the number
 * of authentication attempts.
 * <p>
 * The pool can be limited by size, using an LRU strategy for eviction. The
 * pool further supports automatic eviction of clients after they weren't used for
 * a specific amount of time.
 */
public class OdhClientPool {

    private final Cache<String, OdhClient> pool;

    public OdhClientPool(int maximumSize, Duration duration) {
        this.pool = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterWrite(duration)
                .build();
    }

    public OdhClient getClient(String hash, Function<? super String, ? extends OdhClient> mappingFunction) {
        return this.pool.get(hash, mappingFunction);
    }

}
