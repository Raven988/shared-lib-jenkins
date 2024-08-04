package org.foo

class MyUtils {
    public static Script pipeline
    public static String gitUrl = 'https://github.com/Raven988'

    static String greet(String name) {
        return "Hello, ${name}!"
    }

    static String getGitRepoUrl() {
        return pipeline.scm.getUserRemoteConfigs()[0].getUrl()
    }

    static String getGitPath(def originUrl = getGitRepoUrl()) {
        originUrl - gitUrl - '.git'
    }
    
    static String getGitRepoName(def gitRepoUrl = getGitRepoUrl()) {
        String path = getGitPath(gitRepoUrl)
        return path.substring(path.lastIndexOf('/') + 1)
    }

    static void sh(String script, String label=null) {
        pipeline.sh(script: script, label: label)
    }

    static void mkdir(String dir) {
        pipeline.fileOperations([pipeline.folderCreateOperation(dir)])
    }
}
