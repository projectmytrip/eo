package it.reply.compliance.commons.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;

public interface StorageService {

    Path relativize(Path path);

    Path load(Path directory, String filename);

    Resource loadAsResource(Path directory, String filename);

    Stream<Path> fileStream();
}