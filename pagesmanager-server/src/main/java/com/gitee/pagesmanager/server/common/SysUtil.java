package com.gitee.pagesmanager.server.common;

/**
 * @author tanghc
 */
public class SysUtil {
    private static boolean isWin;

    static {
        String os = System.getProperty("os.name");
        // 如果是win操作系统
        isWin = os.toLowerCase().startsWith("win");
    }

    /**
     * 当前操作系统是否是windows系统
     * @return
     */
    public static boolean isWin() {
        return isWin;
    }

}
