package jota.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jota.model.Bundle;
import jota.pow.ICurl;
import jota.pow.JCurl;

public class Signing {
    private ICurl curl;

    public Signing() {
        this(null);
    }

    public Signing(ICurl curl) {
        this.curl = curl == null ? new JCurl() : curl;
    }

    public int[] key(int[] seed, int index, int length) {

        for (int i = 0; i < index; i++) {
            for (int j = 0; j < 243; j++) {
                if (++seed[j] > 1) {
                    seed[j] = -1;
                } else {
                    break;
                }
            }
        }

        curl.reset();
        curl.absorbb(seed, 0, seed.length);
        curl.squeezee(seed, 0, seed.length);
        curl.reset();
        curl.absorbb(seed, 0, seed.length);

        final List<Integer> key = new ArrayList<>();
        int[] buffer = new int[seed.length];
        int offset = 0;

        while (length-- > 0) {

            for (int i = 0; i < 27; i++) {
                curl.squeezee(buffer, offset, buffer.length);
                for (int j = 0; j < 243; j++) {
                    key.add(buffer[j]);
                }
            }
        }
        return to(key);
    }

    private int[] to(List<Integer> key) {
        int a[] = new int[key.size()];
        int i = 0;
        for (Integer v : key) {
            a[i++] = v;
        }
        return a;
    }

    public int[] signatureFragment(int[] normalizedBundleFragment, int[] keyFragment) {

        int[] signatureFragment = keyFragment;
        int[] hash;

        for (int i = 0; i < 27; i++) {

            hash = Arrays.copyOfRange(signatureFragment, i * 243, (i + 1) * 243);

            for (int j = 0; j < 13 - normalizedBundleFragment[i]; j++) {
                curl.reset()
                        .absorbb(hash, 0, hash.length)
                        .squeezee(hash, 0, hash.length);
            }

            for (int j = 0; j < 243; j++) {
                signatureFragment[i * 243 + j] = hash[j];
            }
        }

        return signatureFragment;
    }

    public int[] address(int[] digests) {
        int[] address = new int[243];
        curl.reset()
                .absorbb(digests)
                .squeezee(address);
        return address;
    }

    public int[] digests(int[] key) {

        int[] digests = new int[(int) Math.floor(key.length / 6561) * 243];
        int[] buffer = new int[243];

        for (int i = 0; i < Math.floor(key.length / 6561); i++) {
            int[] keyFragment = Arrays.copyOfRange(key, i * 6561, (i + 1) * 6561);

            for (int j = 0; j < 27; j++) {

                buffer = Arrays.copyOfRange(keyFragment, j * 243, (j + 1) * 243);
                for (int k = 0; k < 26; k++) {
                    curl.reset()
                            .absorbb(buffer)
                            .squeezee(buffer);
                }
                System.arraycopy(buffer, 0, keyFragment, j * 243, 243);
            }

            curl.reset();
            curl.absorbb(keyFragment, 0, keyFragment.length);
            curl.squeezee(buffer, 0, buffer.length);

            System.arraycopy(buffer, 0, digests, i * 243, 243);
        }
        return digests;
    }

    public int[] digest(int[] normalizedBundleFragment, int[] signatureFragment) {
        curl.reset();
        int[] buffer = new int[243];

        for (int i = 0; i < 27; i++) {
            buffer = Arrays.copyOfRange(signatureFragment, i * 243, (i + 1) * 243);

            for (int j = normalizedBundleFragment[i] + 13; j-- > 0; ) {

                ICurl jCurl = new JCurl();
                jCurl.reset();
                jCurl.absorbb(buffer);
                jCurl.squeezee(buffer);
            }
            curl.absorbb(buffer);
        }
        curl.squeezee(buffer);

        return buffer;
    }

    public Boolean validateSignatures(String expectedAddress, String[] signatureFragments, String bundleHash) {

        Bundle bundle = new Bundle();

        int[][] normalizedBundleFragments = new int[3][27];
        int[] normalizedBundleHash = bundle.normalizedBundle(bundleHash);

        // Split hash into 3 fragments
        for (int i = 0; i < 3; i++) {
            normalizedBundleFragments[i] = Arrays.copyOfRange(normalizedBundleHash, i * 27, (i + 1) * 27);
        }

        // Get digests
        int[] digests = new int[signatureFragments.length * 243];

        for (int i = 0; i < signatureFragments.length; i++) {

            int[] digestBuffer = digest(normalizedBundleFragments[i % 3], Converter.trits(signatureFragments[i]));

            for (int j = 0; j < 243; j++) {

                digests[i * 243 + j] = digestBuffer[j];
            }
        }
        String address = Converter.trytes(address(digests));

        return (expectedAddress.equals(address));
    }
}

