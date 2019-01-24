package life.qbic.portal.model;

import java.sql.Date;

public class Batch {

  private int id;
  private Date estimatedDelivery;
  private int numOfSamples;

  // TODO convert vaadin date to SQL Timestamp
  public Batch(int id, Date estimatedDelivery, int numOfSamples) {
    this.id = id;
    this.estimatedDelivery = estimatedDelivery;
    this.numOfSamples = numOfSamples;
  }

  public Batch(int numOfSamples, Date estimatedDelivery) {
    this.estimatedDelivery = estimatedDelivery;
    this.id = -1;
    this.numOfSamples = numOfSamples;
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

}
