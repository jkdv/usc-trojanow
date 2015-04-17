package com.trojanow.account.entity;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by heetae on 4/13/15.
 */
public class DigestWrapper {
    /**
     * @param password
     * @return @Nullable
     */
    public static String encode(String password) {
        return new String(Hex.encodeHex(DigestUtils.sha512(password)));
    }
}
