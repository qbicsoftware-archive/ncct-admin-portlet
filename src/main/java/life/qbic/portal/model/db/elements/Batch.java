package life.qbic.portal.model.db.elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.Objects;

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

    private Date parseDate(java.util.Date estimatedDelivery) {
        //java.util.Date date = new java.util.Date(estimatedDelivery);
        //SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            return new Date(estimatedDelivery.getTime());
        } catch (Exception e) {
            logger.error("could not parse date " + estimatedDelivery);
            e.printStackTrace();
        }
        return null;
    }

    public Batch(int numOfSamples, java.util.Date estimatedDelivery) {
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

    public void setEstimatedDelivery(java.util.Date estimatedDelivery) {
        this.estimatedDelivery = parseDate(estimatedDelivery);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batch batch = (Batch) o;
        return id == batch.id &&
                numOfSamples == batch.numOfSamples &&
                Objects.equals(estimatedDelivery, batch.estimatedDelivery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estimatedDelivery, numOfSamples);
    }
}
