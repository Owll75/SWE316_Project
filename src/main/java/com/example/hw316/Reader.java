package com.example.hw316;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Reader {

    public Reader() {

    }

    public ArrayList<Stage> readStages(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        ArrayList<Stage> stage_list = new ArrayList<Stage>();

        ArrayList<String> objectV = new ArrayList<String>();
        ArrayList<Integer> ducN = new ArrayList<Integer>();
        ArrayList<String> FieldN = new ArrayList<String>();
        ArrayList<String> indicator = new ArrayList<String>();
        ArrayList<Integer> textF = new ArrayList<Integer>();
        ArrayList<Number> newV = new ArrayList<Number>();
        ArrayList<Integer> oldV = new ArrayList<Integer>();

        int index = 0;
        for (Row row : sheet) {
            if (row.getRowNum() != 0) {
                if (row.getPhysicalNumberOfCells() == 7) {
                    for (Cell cell : row) {
                        if (index == 0) {
                            objectV.add(cell.getStringCellValue());
                            index++;
                        } else if (index == 1) {
                            ducN.add((int) cell.getNumericCellValue());
                            index++;
                        } else if (index == 2) {
                            FieldN.add(cell.getStringCellValue());
                            index++;
                        } else if (index == 3) {
                            indicator.add(cell.getStringCellValue());
                            index++;
                        } else if (index == 4) {
                            textF.add((int) cell.getNumericCellValue());
                            index++;
                        } else if (index == 5) {
                            newV.add((int) cell.getNumericCellValue());
                            index++;
                        } else if (index == 6) {
                            oldV.add((int) cell.getNumericCellValue());
                            index = 0;
                        }

                    }
                } else {
                    for (Cell cell : row) {
                        if (index == 0) {
                            objectV.add(cell.getStringCellValue());
                            index++;
                        } else if (index == 1) {
                            ducN.add((int) cell.getNumericCellValue());
                            index++;
                        } else if (index == 2) {
                            FieldN.add(cell.getStringCellValue());
                            index++;
                        } else if (index == 3) {
                            indicator.add(cell.getStringCellValue());
                            index++;
                        } else if (index == 4) {
                            textF.add((int) cell.getNumericCellValue());
                            index++;
                        } else if (index == 5) {
                            newV.add((int) cell.getNumericCellValue());
                            oldV.add(0);
                            index = 0;
                        }
                    }
                }
                stage_list.add(new Stage());
                stage_list.get(stage_list.size() - 1).setObjValue(objectV.get(0));
                stage_list.get(stage_list.size() - 1).setDocNumber(ducN.get(0));
                stage_list.get(stage_list.size() - 1).setNewValue((Integer) newV.get(0));
                stage_list.get((stage_list.size()) - 1).setOldValue((Integer) oldV.get(0));

                objectV.clear();
                ducN.clear();
                oldV.clear();
                newV.clear();

            }
        }
        return stage_list;
    }

    public ArrayList<Stage> readStagesDetails(String Path, ArrayList<Stage> stageList) throws IOException {
        FileInputStream fis = new FileInputStream(new File(Path));
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);

        ArrayList<Integer> DucNum = new ArrayList<Integer>();
        ArrayList<Date> date = new ArrayList<Date>();

        ArrayList<Stage> list = stageList;

        for (Row row : sheet) {
            if (row.getRowNum() != 0) {
                for (Cell cell : row) {
                    CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    if ('B' == (cellRef.formatAsString().charAt(0))) {
                        DucNum.add((int) cell.getNumericCellValue());
                    } else if ('C' == (cellRef.formatAsString().charAt(0))) {
                        date.add(cell.getDateCellValue());
                    } else {
                    }
                }
            }
        }

        for(int i = 0; i < list.size();i++){
            for (Stage x : list){
                if (DucNum.get(i).equals(x.getDocNumber())){
                    x.setDate(date.get(i));
                }
            }
        }

        return list;
    }

    public ArrayList<Project> readProject(String path, ArrayList<Stage> list) throws IOException {
        FileInputStream file = new FileInputStream(new File(path));
        HSSFWorkbook wb = new HSSFWorkbook(file);
        HSSFSheet sheet = wb.getSheetAt(0);

        ArrayList<Stage> stages_list = list;
        ArrayList<Project> project_list = new ArrayList<Project>();

        for (Row row : sheet) {
            if (row.getRowNum() != 0) {
                if (row.getPhysicalNumberOfCells() == 9) {
                    project_list.add(new Project());
                    for (Cell cell : row) {
                        CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                        if ('A' == (cellRef.formatAsString().charAt(0))) {
                            project_list.get(project_list.size() -1).setNodeId(cell.getRichStringCellValue().getString());
                        }
                        else if ('B' == (cellRef.formatAsString().charAt(0))) {
                            project_list.get(project_list.size() -1).setCustomerProjectId(cell.getRichStringCellValue().getString());
                        }
                        else if ('C' == (cellRef.formatAsString().charAt(0))) {
                            project_list.get(project_list.size() -1).setStagesValue((int) cell.getNumericCellValue());
                        }
                        else {}
                    }

                    for (Stage x: stages_list){
                        if (x.getObjValue().equals(project_list.get(project_list.size()-1).getNodeId())){
                            project_list.get(project_list.size()-1).addStage(x);
                        }
                    }

                }
            }
        }
        return project_list;
    }

    public ArrayList<Project> getProjectList() throws IOException, ParseException {
        ArrayList<Stage> stageArrayList = readStages("Stages.xls");
        ArrayList<Stage> stageDetailsArrayList = readStagesDetails("Stages_Detailed.xls", stageArrayList);
        return readProject("Projects.xls", stageDetailsArrayList);
    }
}
