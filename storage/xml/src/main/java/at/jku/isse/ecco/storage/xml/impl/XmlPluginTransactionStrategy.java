package at.jku.isse.ecco.storage.xml.impl;

import at.jku.isse.ecco.EccoException;
import at.jku.isse.ecco.dao.TransactionStrategy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Singleton
public class XmlPluginTransactionStrategy implements TransactionStrategy {

    private static final String JSON_DB_NAME = "ecco.db.xml.zip";
    public static final String RENAMED_JSON_DB_NAME = JSON_DB_NAME + ".backup";
    private static final String TEMP_JSON_DB_NAME = JSON_DB_NAME + ".lock";


    public XmlRepository load() {
        final Path repoPath = getRepoPath();
        if (!Files.exists(repoPath))
            return curRepo = new XmlRepository();
        try {
            return curRepo = XmlRepository.loadFromDisk(repoPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Path getRepoPath() {
        return repositoryDir.resolve(JSON_DB_NAME);
    }

    public Path getTempRepoPath() {
        return repositoryDir.resolve(TEMP_JSON_DB_NAME);
    }


    private final Path repositoryDir;

    private XmlRepository curRepo;

    public XmlRepository getOrLoadRepository() {
        return Optional.ofNullable(curRepo).orElseGet(this::load);
    }


    @Inject
    public XmlPluginTransactionStrategy(@Named("repositoryDir") Path repositoryDir) {
        this.repositoryDir = requireNonNull(repositoryDir);
    }

    //Only one transaction at a time

    @Override
    public void open() throws EccoException {
    }

    @Override
    public void close() throws EccoException {
        try {
            Files.deleteIfExists(getTempRepoPath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void begin() throws EccoException {

    }

    @Override
    public void end() throws EccoException {
    }

    @Override
    public void rollback() throws EccoException {
    }
}