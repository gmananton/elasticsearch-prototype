package com.gman.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by Anton Mikhaylov on 19.01.2021.
 * Позаимствовал из instr-commons =)
 */
public class FileHelper {

    public static String loadResource(String resource) {
        return loadResource(resource, StandardCharsets.UTF_8);
    }

    public static String loadResource(String resourceName, Charset encoding) {
        try {
            InputStream is = FileHelper.class.getResourceAsStream(resourceName);

            String var3;
            try {
                var3 = loadInputStream(is, encoding);
            } catch (Throwable var6) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }
                }

                throw var6;
            }

            if (is != null) {
                is.close();
            }

            return var3;
        } catch (IOException var7) {
            throw new RuntimeException("Failed to loadResource as InputStream: resourceName=" + resourceName + " encoding=" + encoding, var7);
        }
    }

    public static String loadInputStream(InputStream is, Charset encoding) {
        try {
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[256];
            BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));

            int count;
            try {
                while((count = br.read(buffer, 0, 256)) > 0) {
                    sb.append(buffer, 0, count);
                }
            } catch (Throwable var9) {
                try {
                    br.close();
                } catch (Throwable var8) {
                    var9.addSuppressed(var8);
                }

                throw var9;
            }

            br.close();
            return sb.toString();
        } catch (IOException var10) {
            throw new RuntimeException("Failed to loadInputStream", var10);
        }
    }
}
