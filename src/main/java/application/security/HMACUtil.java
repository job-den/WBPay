package application.security;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Base64;

public class HMACUtil {

    public static String hmacWithBouncyCastle(String algorithm, String data, String key) {
        Digest digest = getHashDigest(algorithm);

        HMac hMac = new HMac(digest);
        hMac.init(new KeyParameter(key.getBytes()));

        byte[] hmacIn = data.getBytes();
        hMac.update(hmacIn, 0, hmacIn.length);
        byte[] hmacOut = new byte[hMac.getMacSize()];

        hMac.doFinal(hmacOut, 0);
        return bytesToHex(hmacOut);
    }

    private static Digest getHashDigest(String algorithm) {
        switch (algorithm) {
            case "HmacMD5":
                return new MD5Digest();
            case "HmacSHA256":
                return new SHA256Digest();
            case "HmacSHA384":
                return new SHA384Digest();
            case "HmacSHA512":
                return new SHA512Digest();
        }
        return new SHA256Digest();
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static byte[] hmac256(String secretKey, String message) {
        try {
            return hmac256(secretKey.getBytes("UTF-8"), message.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMACSHA256 encrypt", e);
        }
    }

    public static byte[] hmac256(byte[] secretKey, byte[] message) {
        byte[] hmac256 = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec sks = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(sks);
            hmac256 = mac.doFinal(message);
            return hmac256;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMACSHA256 encrypt ");
        }
    }

    public static String hmacBC(String secretKey, String message) {
        try {
            String hmacAlgorithm = "HmacSHA512";
            return HMACUtil.hmacWithBouncyCastle(hmacAlgorithm, message, secretKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMACSHA256 encrypt", e);
        }
    }

    public static void main(String args[]) {
        byte[] hmacSha256 = HMACUtil.hmac256("secreT1_", "Hello world from Java!");
        System.out.println(String.format("Hex: %032x", new BigInteger(1, hmacSha256)));

        String base64HmacSha256 = Base64.getEncoder().encodeToString(hmacSha256);
        System.out.println("Base64: " + base64HmacSha256);
    }

}
