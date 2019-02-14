package life.qbic.portal.model.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * @author afriedrich
 */
public class Batch {

    private int id;
    private Date estimatedDelivery;
    private int numOfSamples;

    private final Logger logger = LogManager.getLogger(Batch.class);

    public Batch(int id, Date deliveryDate, int numOfSamples) {
        this.id = id;
        this.estimatedDelivery = deliveryDate;
        this.numOfSamples = numOfSamples;
    }

    private Date parseDate(String estimatedDelivery) {
        java.util.Date date = new java.util.Date(estimatedDelivery);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            return new Date(date.getTime());
        } catch (Exception e) {
            logger.error("could not parse date " + estimatedDelivery);
            e.printStackTrace();
        }
        return null;
    }

    public Batch(int numOfSamples, String estimatedDelivery) {
        this.id = -1;
        this.numOfSamples = numOfSamples;
        this.estimatedDelivery = parseDate(estimatedDelivery);
    }

    public int getId() {
        return id;
    }

    public int getNumOfSamples() {
        return numOfSamples;
    }

    public void setNumOfSamples(int numOfSamples) {
        this.numOfSamples = numOfSamples;
    }

    public Date getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(Date estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public void setEstimatedDelivery(String estimatedDelivery) {
        this.estimatedDelivery = parseDate(estimatedDelivery);
    }

}
