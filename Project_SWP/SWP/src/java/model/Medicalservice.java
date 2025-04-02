package model;

import java.io.Serializable;

/**
 * 
 */
public class Medicalservice implements Serializable {
    private int examinationID;
    private int packageID;    

 
    public Medicalservice() {
    }

 
    public Medicalservice(int examinationID, int packageID) {
        this.examinationID = examinationID;
        this.packageID = packageID;
    }

   
    public int getExaminationID() {
        return examinationID;
    }

    public void setExaminationID(int examinationID) {
        this.examinationID = examinationID;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

   
    @Override
    public String toString() {
        return "MedicalService{" +
                "examinationID=" + examinationID +
                ", packageID=" + packageID +
                '}';
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicalservice that = (Medicalservice) o;
        return examinationID == that.examinationID && packageID == that.packageID;
    }

    @Override
    public int hashCode() {
        int result = examinationID;
        result = 31 * result + packageID;
        return result;
    }
}