package io.github.kloping.qqbot.network.hookauth;

import java.security.PrivateKey;
import java.util.Arrays;

public class CustomPrivateKey implements PrivateKey {

    private byte[] encoded;

    public CustomPrivateKey(byte[] encoded) {
        this.encoded = Arrays.copyOf(encoded, encoded.length);
    }

    @Override
    public String getAlgorithm() {
        return "Ed25519";
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    @Override
    public byte[] getEncoded() {
        return Arrays.copyOf(encoded, encoded.length);
    }
}
