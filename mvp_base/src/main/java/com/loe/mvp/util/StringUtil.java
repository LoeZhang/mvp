package com.loe.mvp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
    private static final char[] cs = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    /** 获取英文小写+数字随机字符串 */
    public static String getRandomString(int len)
    {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        // 第一个字符为英文小写
        if (len > 0)
        {
            sb.append(cs[random.nextInt(26)]);
            len--;
        } else
        {
            return "";
        }
        for (int i = 0; i < len; i++)
        {
            sb.append(cs[random.nextInt(36)]);
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isBlank(String str)
    {
        return (str == null || "".equals(str));
    }

    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }

    public static Random random(String s)
    {
        byte[] bs = s.getBytes();
        int l = 0;
        for (byte b : bs)
        {
            l+=b>0?b:-b;
        }
        return new Random(l);
    }

    /**
     * 是否为手机号
     */
    public static boolean isPhone(String phone)
    {
        Pattern pattern = Pattern.compile("^(1[3,4,5,7,8][0-9])\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 汉字或少数民族
     * @param name
     * @return
     */
    public static boolean isUserName(String name)
    {
        Pattern pattern = Pattern.compile("^[\\u3400-\\u9fef\\·]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static String fsMap(Map<String, Object> map)
    {
        StringBuilder sb = new StringBuilder();
        String n = "";
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            sb.append(n);
            sb.append(String.format("%s : %s", entry.getKey(), entry.getValue()));
            n = "\n";
        }
        return sb.toString();
    }

    public static boolean testNewVersion(String nowV, String newV)
    {
        try
        {
            String[] vs1 = nowV.split("\\.");
            String[] vs2 = newV.split("\\.");
            if (Integer.parseInt(vs2[0]) < Integer.parseInt(vs1[0]))
            {
                return false;
            }
            if (Integer.parseInt(vs2[0]) > Integer.parseInt(vs1[0]))
            {
                return true;
            }
            if (Integer.parseInt(vs2[1]) < Integer.parseInt(vs1[1]))
            {
                return false;
            }
            if (Integer.parseInt(vs2[1]) > Integer.parseInt(vs1[1]))
            {
                return true;
            }
            if (Integer.parseInt(vs2[2]) <= Integer.parseInt(vs1[2]))
            {
                return false;
            }
        } catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public static final SimpleDateFormat hms = new SimpleDateFormat("h:mm");
    private static final SimpleDateFormat mdhms = new SimpleDateFormat("M月d日 h:mm");
    private static final SimpleDateFormat ymdhms = new SimpleDateFormat("y年M月d日 h:mm");

    /**
     * 时间格式化
     */
    public static String formatDynamicTime(long time)
    {
        long now = System.currentTimeMillis();
        long dt = now - time;
        if (dt < 0)
        {
            return "error";
        }
        Calendar calendar0 = Calendar.getInstance();
        calendar0.setTimeInMillis(time);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(now);
        int y0 = calendar0.get(Calendar.YEAR);
        int d0 = calendar0.get(Calendar.DAY_OF_YEAR);
        int y1 = calendar1.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);

        if (y0 == y1)
        {
            // 如果1天内
            if (d0 == d1)
            {
                // 如果是1小时内
                if (dt < 3600000)
                {
                    // 如果是1分钟内
                    if (dt < 60000)
                    {
                        return dt / 1000 + " 秒前";
                    }
                    return dt / 60000 + " 分钟前";
                }
                return "今天 " + hms.format(new Date(time));
            } else
            {
                if (d0 == d1 - 1)
                {
                    return "昨天 " + hms.format(new Date(time));
                }
            }
        } else
        {
            if (d1 == 1 && y0 == y1 - 1)
            {
                calendar1.add(Calendar.DATE, -1);
                // 为昨天
                if (calendar1.get(Calendar.DAY_OF_YEAR) == d0)
                {
                    return "昨天 " + hms.format(new Date(time));
                }
            }else
            {
                return ymdhms.format(new Date(time));
            }
        }
        return mdhms.format(new Date(time));
    }
}