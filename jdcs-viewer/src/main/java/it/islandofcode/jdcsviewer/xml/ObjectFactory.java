
package it.islandofcode.jdcsviewer.xml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.islandofcode.jdcsviewer.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.islandofcode.jdcsviewer.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Ipcamdata }
     * 
     */
    public Ipcamdata createIpcamdata() {
        return new Ipcamdata();
    }

    /**
     * Create an instance of {@link Ipcamdata.Video }
     * 
     */
    public Ipcamdata.Video createIpcamdataVideo() {
        return new Ipcamdata.Video();
    }

    /**
     * Create an instance of {@link Ipcamdata.Audio }
     * 
     */
    public Ipcamdata.Audio createIpcamdataAudio() {
        return new Ipcamdata.Audio();
    }

    /**
     * Create an instance of {@link Ipcamdata.Panning }
     * 
     */
    public Ipcamdata.Panning createIpcamdataPanning() {
        return new Ipcamdata.Panning();
    }

    /**
     * Create an instance of {@link Ipcamdata.Command }
     * 
     */
    public Ipcamdata.Command createIpcamdataCommand() {
        return new Ipcamdata.Command();
    }

}
