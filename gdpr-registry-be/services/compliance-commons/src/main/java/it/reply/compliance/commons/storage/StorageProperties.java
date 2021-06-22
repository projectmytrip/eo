package it.reply.compliance.commons.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    private Directory directory;

    public String getRootDirectory() {
        return this.directory.root;
    }

    public void setRootDirectory(String value) {
        this.directory.root = value;
    }

    @Data
    private static class Directory {
        private String root;
    }
}
