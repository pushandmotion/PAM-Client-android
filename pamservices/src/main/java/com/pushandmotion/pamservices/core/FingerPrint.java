package com.pushandmotion.pamservices.core;

import com.pushandmotion.pamservices.data.TrackingData;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mac on 8/18/2017 AD.
 */

public class FingerPrint {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String fromString(String data){
        return hash(data);
    }

    private static String hash( String toHash )
    {
        String hash = null;
        try
        {
            MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();
            hash = bytesToHex( bytes );
        }
        catch( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }
        catch( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        return hash;
    }

    private static String bytesToHex( byte[] bytes )
    {
        char[] hexChars = new char[ bytes.length * 2 ];
        for( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[ j ] & 0xFF;
            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
        }
        return new String( hexChars );
    }
}
