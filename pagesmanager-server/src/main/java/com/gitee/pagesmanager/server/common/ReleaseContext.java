package com.gitee.pagesmanager.server.common;

/**
 * @author tanghc
 */
public class ReleaseContext {
    private int id;

    /**  数据库字段：local_git_path */
    private String localGitPath;

    /**  数据库字段：git_cmd */
    private String gitCmd;

    /**  数据库字段：git_url */
    private String gitUrl;

    private String destPath;

    public int getProjectId() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public String getLocalGitPath() {
        return localGitPath;
    }

    public void setLocalGitPath(String localGitPath) {
        this.localGitPath = localGitPath;
    }

    public String getGitCmd() {
        return gitCmd;
    }

    public void setGitCmd(String gitCmd) {
        this.gitCmd = gitCmd;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    @Override
    public String toString() {
        return "ReleaseContext{" +
                "id=" + id +
                ", localGitPath='" + localGitPath + '\'' +
                ", gitCmd='" + gitCmd + '\'' +
                ", gitUrl='" + gitUrl + '\'' +
                ", destPath='" + destPath + '\'' +
                '}';
    }
}
