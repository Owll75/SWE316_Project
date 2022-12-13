package com.example.hw316;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Project {

    private String nodeID;
    private String customerProjectId;
    private ArrayList<Stage> stages;
    private int StagesValue;

    private ArrayList<Date> datesList;

    private int beforeAward;
    private int afterAward;

    public Project() {
        stages = new ArrayList<>();
        datesList = new ArrayList<Date>();
    }

    public Project(String id, String customerProjectId, Date date, int stagesValue) {
        this.nodeID = id;
        this.customerProjectId = customerProjectId;
        this.StagesValue = stagesValue;
        stages = new ArrayList<>();
        datesList = new ArrayList<Date>();
    }

    public String getNodeId() {
        return nodeID;
    }

    public void setNodeId(String id) {
        this.nodeID = id;
    }

    public String getCustomerProjectId() {
        return customerProjectId;
    }

    public void setCustomerProjectId(String customerProjectId) {
        this.customerProjectId = customerProjectId;
    }

    public int getStagesValue() { // the Stage value in the Project.xls file
        return StagesValue;
    }

    public void setStagesValue(int stagesValue) {
        StagesValue = stagesValue;
    }

    public int getBeforeAward() {
        return beforeAward;
    }

    public int getAfterAward() {
        return afterAward;
    }

    public void setBeforeAward(int beforeAward) {
        this.beforeAward = beforeAward;
    }

    public void setAfterAward(int afterAward) {
        this.afterAward = afterAward;
    }

    public String toString() {
        return "ID: " + nodeID + ", Customer Project ID: " + customerProjectId + ", stagesValue: " + StagesValue;
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Stage> stages) {
        this.stages = stages;
    }

    public ArrayList<Date> getDatesList() {

        ArrayList<Stage> stageArrayList = getStages();
        for (int i = 0; i < stageArrayList.size(); i++) {
            Date date = stageArrayList.get(i).getDate();

            datesList.add(date);

        }
        datesList.sort((o1, o2) -> o1.compareTo(o2));
        return (ArrayList<Date>) datesList.stream().distinct().collect(Collectors.toList());

    }

    public void addStage(Stage x) {
        stages.add(x);
    }


}
