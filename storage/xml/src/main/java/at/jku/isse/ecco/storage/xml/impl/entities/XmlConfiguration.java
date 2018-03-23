package at.jku.isse.ecco.storage.xml.impl.entities;

import at.jku.isse.ecco.feature.Configuration;
import at.jku.isse.ecco.feature.FeatureRevision;

import java.io.Serializable;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

public class XmlConfiguration implements Configuration, Serializable {

    private final FeatureRevision[] featureRevisions;

    public XmlConfiguration() {
        this(new FeatureRevision[0]);
    }

    public XmlConfiguration(FeatureRevision[] featureRevisions) {
        checkNotNull(featureRevisions);
        this.featureRevisions = featureRevisions;
    }


    @Override
    public FeatureRevision[] getFeatureRevisions() {
        return this.featureRevisions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XmlConfiguration that = (XmlConfiguration) o;
        return Arrays.equals(featureRevisions, that.featureRevisions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(featureRevisions);
    }

    @Override
    public String toString() {
        return this.getConfigurationString();
    }
}
