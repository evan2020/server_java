package com.shalou.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class ShortUrlUtils {
    public static void main(String[] args) {
        String longUrl = "http://data.13322.com/basket/team/27_0_1.html";
        String[] shortCodeArray = shortUrl(longUrl);
        for (int i = 0; i < shortCodeArray.length; i++) {
            System.out.println(shortCodeArray[i]);
        }
    }

    public static String[] shortUrl(String url) {
        String key = "";
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String sMD5EncryptResult = DigestUtils.md5Hex(key + url);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                long index = 0x0000003D & lHexLong;
                outChars += chars[(int) index];
                lHexLong = lHexLong >> 5;
            }
            resUrl[i] = outChars;
        }
        return resUrl;
    }


}
