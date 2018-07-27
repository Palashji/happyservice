package com.bluewebspark.happyservice.model;

/**
 * Created by abc on 21-Mar-18.
 */

public class ServiceProcedureModel {
    String procedureId;
    String procedureTitle;

    public ServiceProcedureModel(String procedureId, String procedureTitle) {
        this.procedureId = procedureId;
        this.procedureTitle = procedureTitle;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureTitle() {
        return procedureTitle;
    }

    public void setProcedureTitle(String procedureTitle) {
        this.procedureTitle = procedureTitle;
    }
}
