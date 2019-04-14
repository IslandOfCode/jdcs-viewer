
package it.islandofcode.jdcsviewer.xml;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="video">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="URLstream" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="URLimage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="audio">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="URLstream" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="panning">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="pannable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="URLpanUp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="URLpanDown" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="URLpanLeft" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="URLpanRight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="URLpreset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="degree" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="command">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="NVtogglable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="URLNVon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="URLNVoff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="URLNVauto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="MotionTogglable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="URLmotionOn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="URLmotionOff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "model",
    "version",
    "author",
    "video",
    "audio",
    "panning",
    "command"
})
@XmlRootElement(name = "ipcamdata")
public class Ipcamdata {

    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected String version;
    @XmlElement(required = true)
    protected String author;
    @XmlElement(required = true)
    protected Ipcamdata.Video video;
    @XmlElement(required = true)
    protected Ipcamdata.Audio audio;
    @XmlElement(required = true)
    protected Ipcamdata.Panning panning;
    @XmlElement(required = true)
    protected Ipcamdata.Command command;

    /**
     * Recupera il valore della propriet� model.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Imposta il valore della propriet� model.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Recupera il valore della propriet� version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Imposta il valore della propriet� version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Recupera il valore della propriet� author.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Imposta il valore della propriet� author.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Recupera il valore della propriet� video.
     * 
     * @return
     *     possible object is
     *     {@link Ipcamdata.Video }
     *     
     */
    public Ipcamdata.Video getVideo() {
        return video;
    }

    /**
     * Imposta il valore della propriet� video.
     * 
     * @param value
     *     allowed object is
     *     {@link Ipcamdata.Video }
     *     
     */
    public void setVideo(Ipcamdata.Video value) {
        this.video = value;
    }

    /**
     * Recupera il valore della propriet� audio.
     * 
     * @return
     *     possible object is
     *     {@link Ipcamdata.Audio }
     *     
     */
    public Ipcamdata.Audio getAudio() {
        return audio;
    }

    /**
     * Imposta il valore della propriet� audio.
     * 
     * @param value
     *     allowed object is
     *     {@link Ipcamdata.Audio }
     *     
     */
    public void setAudio(Ipcamdata.Audio value) {
        this.audio = value;
    }

    /**
     * Recupera il valore della propriet� panning.
     * 
     * @return
     *     possible object is
     *     {@link Ipcamdata.Panning }
     *     
     */
    public Ipcamdata.Panning getPanning() {
        return panning;
    }

    /**
     * Imposta il valore della propriet� panning.
     * 
     * @param value
     *     allowed object is
     *     {@link Ipcamdata.Panning }
     *     
     */
    public void setPanning(Ipcamdata.Panning value) {
        this.panning = value;
    }

    /**
     * Recupera il valore della propriet� command.
     * 
     * @return
     *     possible object is
     *     {@link Ipcamdata.Command }
     *     
     */
    public Ipcamdata.Command getCommand() {
        return command;
    }

    /**
     * Imposta il valore della propriet� command.
     * 
     * @param value
     *     allowed object is
     *     {@link Ipcamdata.Command }
     *     
     */
    public void setCommand(Ipcamdata.Command value) {
        this.command = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="URLstream" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "urLstream"
    })
    public static class Audio {

        @XmlElement(name = "URLstream")
        protected String urLstream;

        /**
         * Recupera il valore della propriet� urLstream.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLstream() {
            return urLstream;
        }

        /**
         * Imposta il valore della propriet� urLstream.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLstream(String value) {
            this.urLstream = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="NVtogglable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="URLNVon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="URLNVoff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="URLNVauto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="MotionTogglable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="URLmotionOn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="URLmotionOff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "nVtogglable",
        "urlnVon",
        "urlnVoff",
        "urlnVauto",
        "motionTogglable",
        "urLmotionOn",
        "urLmotionOff"
    })
    public static class Command {

        @XmlElement(name = "NVtogglable")
        protected boolean nVtogglable;
        @XmlElement(name = "URLNVon")
        protected String urlnVon;
        @XmlElement(name = "URLNVoff")
        protected String urlnVoff;
        @XmlElement(name = "URLNVauto")
        protected String urlnVauto;
        @XmlElement(name = "MotionTogglable")
        protected boolean motionTogglable;
        @XmlElement(name = "URLmotionOn")
        protected String urLmotionOn;
        @XmlElement(name = "URLmotionOff")
        protected String urLmotionOff;

        /**
         * Recupera il valore della propriet� nVtogglable.
         * 
         */
        public boolean isNVtogglable() {
            return nVtogglable;
        }

        /**
         * Imposta il valore della propriet� nVtogglable.
         * 
         */
        public void setNVtogglable(boolean value) {
            this.nVtogglable = value;
        }

        /**
         * Recupera il valore della propriet� urlnVon.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLNVon() {
            return urlnVon;
        }

        /**
         * Imposta il valore della propriet� urlnVon.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLNVon(String value) {
            this.urlnVon = value;
        }

        /**
         * Recupera il valore della propriet� urlnVoff.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLNVoff() {
            return urlnVoff;
        }

        /**
         * Imposta il valore della propriet� urlnVoff.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLNVoff(String value) {
            this.urlnVoff = value;
        }

        /**
         * Recupera il valore della propriet� urlnVauto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLNVauto() {
            return urlnVauto;
        }

        /**
         * Imposta il valore della propriet� urlnVauto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLNVauto(String value) {
            this.urlnVauto = value;
        }

        /**
         * Recupera il valore della propriet� motionTogglable.
         * 
         */
        public boolean isMotionTogglable() {
            return motionTogglable;
        }

        /**
         * Imposta il valore della propriet� motionTogglable.
         * 
         */
        public void setMotionTogglable(boolean value) {
            this.motionTogglable = value;
        }

        /**
         * Recupera il valore della propriet� urLmotionOn.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLmotionOn() {
            return urLmotionOn;
        }

        /**
         * Imposta il valore della propriet� urLmotionOn.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLmotionOn(String value) {
            this.urLmotionOn = value;
        }

        /**
         * Recupera il valore della propriet� urLmotionOff.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLmotionOff() {
            return urLmotionOff;
        }

        /**
         * Imposta il valore della propriet� urLmotionOff.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLmotionOff(String value) {
            this.urLmotionOff = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="pannable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="URLpanUp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="URLpanDown" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="URLpanLeft" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="URLpanRight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="URLpreset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="degree" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "pannable",
        "urLpanUp",
        "urLpanDown",
        "urLpanLeft",
        "urLpanRight",
        "urLpreset",
        "degree"
    })
    public static class Panning {

        protected boolean pannable;
        @XmlElement(name = "URLpanUp")
        protected String urLpanUp;
        @XmlElement(name = "URLpanDown")
        protected String urLpanDown;
        @XmlElement(name = "URLpanLeft")
        protected String urLpanLeft;
        @XmlElement(name = "URLpanRight")
        protected String urLpanRight;
        @XmlElement(name = "URLpreset")
        protected String urLpreset;
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger degree;

        /**
         * Recupera il valore della propriet� pannable.
         * 
         */
        public boolean isPannable() {
            return pannable;
        }

        /**
         * Imposta il valore della propriet� pannable.
         * 
         */
        public void setPannable(boolean value) {
            this.pannable = value;
        }

        /**
         * Recupera il valore della propriet� urLpanUp.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLpanUp() {
            return urLpanUp;
        }

        /**
         * Imposta il valore della propriet� urLpanUp.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLpanUp(String value) {
            this.urLpanUp = value;
        }

        /**
         * Recupera il valore della propriet� urLpanDown.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLpanDown() {
            return urLpanDown;
        }

        /**
         * Imposta il valore della propriet� urLpanDown.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLpanDown(String value) {
            this.urLpanDown = value;
        }

        /**
         * Recupera il valore della propriet� urLpanLeft.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLpanLeft() {
            return urLpanLeft;
        }

        /**
         * Imposta il valore della propriet� urLpanLeft.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLpanLeft(String value) {
            this.urLpanLeft = value;
        }

        /**
         * Recupera il valore della propriet� urLpanRight.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLpanRight() {
            return urLpanRight;
        }

        /**
         * Imposta il valore della propriet� urLpanRight.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLpanRight(String value) {
            this.urLpanRight = value;
        }

        /**
         * Recupera il valore della propriet� urLpreset.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLpreset() {
            return urLpreset;
        }

        /**
         * Imposta il valore della propriet� urLpreset.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLpreset(String value) {
            this.urLpreset = value;
        }

        /**
         * Recupera il valore della propriet� degree.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDegree() {
            return degree;
        }

        /**
         * Imposta il valore della propriet� degree.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDegree(BigInteger value) {
            this.degree = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="URLstream" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="URLimage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "urLstream",
        "urLimage"
    })
    public static class Video {

        @XmlElement(name = "URLstream", required = true)
        protected String urLstream;
        @XmlElement(name = "URLimage")
        protected String urLimage;

        /**
         * Recupera il valore della propriet� urLstream.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLstream() {
            return urLstream;
        }

        /**
         * Imposta il valore della propriet� urLstream.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLstream(String value) {
            this.urLstream = value;
        }

        /**
         * Recupera il valore della propriet� urLimage.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURLimage() {
            return urLimage;
        }

        /**
         * Imposta il valore della propriet� urLimage.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURLimage(String value) {
            this.urLimage = value;
        }

    }

}
