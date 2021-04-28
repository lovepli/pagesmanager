package com.gitee.pagesmanager.server.common;

import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 * @author tanghc
 */
public class CmdUtil {

    /**
     * 执行cmd命令
     *
     * @param cmd
     * @return
     */
    public static String runCmd(String cmd, String dir) {
        try {
            return runCmd(Runtime.getRuntime().exec(cmd, null, new File(dir)));
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    /**
     * 执行cmd命令
     *
     * @param cmd
     * @return
     */
    public static String runCmd(String cmd) {
        try {
            return runCmd(Runtime.getRuntime().exec(cmd));
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private static String runCmd(Process process) {
        try {

            InputStream err = process.getErrorStream();
            InputStream in = process.getInputStream();
            String str = processStdout(in);
            String errStr = processStdout(err);
            if (StringUtils.isNotBlank(errStr)) {
                return str + errStr;
            }
            in.close();
            process.waitFor();
            return str;
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public static String processStdout(InputStream in) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuffer out = new StringBuffer();
        while ((line = br.readLine()) != null) {
            out.append(line).append("\n");
        }
        return out.toString();
    }



    public static void main(String[] args) {
        runCmd("chmod +x push.sh", "/Users/thc/IdeaProject/pages-doc");
        String out = runCmd("sh push.sh", "/Users/thc/IdeaProject/pages-doc");
        System.out.println(out);
    }
}
