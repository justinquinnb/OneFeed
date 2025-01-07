package com.justinquinnb.onefeed.data.model.source;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * A content source added to OneFeed.
 */
public class SourceAddon {
    private final String addonName, addonAuthor, addonUrl, addonVersion;
    private final Class source;
    private final String sourceName, sourceOwner, sourceHomepageUrl;

    /**
     * Instantiates a {@code SourceAddon}.
     *
     * @param addonName the name of the {@code SourceAddon}
     * @param addonAuthor the author of the {@code SourceAddon}
     * @param addonUrl the URL of the {@code SourceAddon}
     * @param addonVersion the version number of  the {@code SourceAddon}
     * @param source the {@code Class} object of the {@code Source}
     * @param sourceName the name of the content feed provider
     * @param sourceOwner the owner of the content feed
     * @param sourceHomepageUrl the homepage URL of the content feed provider
     */
    private SourceAddon(
        String addonName,
        String addonAuthor,
        String addonUrl,
        String addonVersion,
        Class source,
        String sourceName,
        String sourceOwner,
        String sourceHomepageUrl
    ) {
        this.addonName = addonName;
        this.addonAuthor = addonAuthor;
        this.addonUrl = addonUrl;
        this.addonVersion = addonVersion;
        this.source = source;
        this.sourceName = sourceName;
        this.sourceOwner = sourceOwner;
        this.sourceHomepageUrl = sourceHomepageUrl;
    }

    /**
     *
     * Instantiates a {@code SourceAddon}.
     *
     * @param addonName the name of the {@code SourceAddon}
     * @param addonAuthor the author of the {@code SourceAddon}
     * @param addonUrl the URL of the {@code SourceAddon}
     * @param addonVersion the version number of  the {@code SourceAddon}
     * @param sourceFilepath the filepath of the {@code Source} file in the addon
     * @param sourceName the name of the content feed provider
     * @param sourceOwner the owner of the content feed
     * @param sourceHomepageUrl the homepage URL of the content feed provider
     *
     * @throws RuntimeException
     */
    private SourceAddon(
            String addonName,
            String addonAuthor,
            String addonUrl,
            String addonVersion,
            String sourceFilepath,
            String sourceName,
            String sourceOwner,
            String sourceHomepageUrl
    ) throws RuntimeException {
        this.addonName = addonName;
        this.addonAuthor = addonAuthor;
        this.addonUrl = addonUrl;
        this.addonVersion = addonVersion;

        this.source = getClassObjFromFilepath(sourceFilepath);

        this.sourceName = sourceName;
        this.sourceOwner = sourceOwner;
        this.sourceHomepageUrl = sourceHomepageUrl;
    }

    public SourceAddon(String sourceFilepath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDoc = builder.parse(sourceFilepath + "/info.xml");

            this.addonName = xmlDoc.getElementsByTagName("addonName").item(0).getTextContent();
            this.addonAuthor = xmlDoc.getElementsByTagName("addonAuthor").item(0).getTextContent();
            this.addonVersion = xmlDoc.getElementsByTagName("addonVersion").item(0).getTextContent();
            this.addonUrl = xmlDoc.getElementsByTagName("addonUrl").item(0).getTextContent();

            // Parse out and instantiate source implementor
            this.source = getClassObjFromFilepath(
                    sourceFilepath + "/" +
                    xmlDoc.getElementsByTagName("implementor").item(0).getTextContent()
            );

            // Parse out source information
            this.sourceName = xmlDoc.getElementsByTagName("sourceName").item(0).getTextContent();
            this.sourceOwner = xmlDoc.getElementsByTagName("sourceOwner").item(0).getTextContent();
            this.sourceHomepageUrl = xmlDoc.getElementsByTagName("sourceHomepageUrl").item(0).getTextContent();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAddonName() {
        return addonName;
    }

    public String getAddonAuthor() {
        return addonAuthor;
    }

    public String getAddonUrl() {
        return addonUrl;
    }

    public String getAddonVersion() {
        return addonVersion;
    }

    public Class getSource() {
        return source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceOwner() {
        return sourceOwner;
    }

    public String getSourceHomepageUrl() {
        return sourceHomepageUrl;
    }

    // HELPERS
    /**
     * Instantiates a {@link Class} object from a given {@code filepath}.
     *
     * @param filepath the filepath to instantiate a {@code Class} object from
     *
     * @return the {@link Class} object that corresponds to the provided class in {@code filepath}
     * @throws RuntimeException if the class cannot be read.
     */
    private static Class getClassObjFromFilepath(String filepath) throws RuntimeException {

    }
}