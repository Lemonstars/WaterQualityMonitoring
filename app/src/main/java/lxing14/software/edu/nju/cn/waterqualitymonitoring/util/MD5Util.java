package lxing14.software.edu.nju.cn.waterqualitymonitoring.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 刘兴
 * @version 1.0
 * @date 28/06/18
 */

public class MD5Util {

    public static String encode(String password){

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (int i: encryption)
            {
                if (Integer.toHexString(0xff & i).length() == 1)
                {
                    stringBuilder.append("0").append(Integer.toHexString(0xff & i));
                }
                else
                {
                    stringBuilder.append(Integer.toHexString(0xff & i));
                }
            }

            return stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }

}
