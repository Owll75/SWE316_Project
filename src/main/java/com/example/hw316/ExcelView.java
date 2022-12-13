package com.example.hw316;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

    public class ExcelView extends Pane {

    private TableView<Project> table;
    private TableColumn<Project, String> colIndex;
    private TableColumn<Project, String> colProject_ID;
    private TableColumn<Project, Integer> colStages;
    private ArrayList<Project> projectArrayList;
    private Text text;

    private String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private Canvas canvas;

    public ExcelView() {
        text = new Text();
        setTable();
        setCanvas();

    }

    private void setCanvas() {
        canvas = new Canvas();
        canvas.setWidth(500);
        canvas.setHeight(300);
        canvas.setLayoutX(240);
        canvas.setLayoutY(120);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 300);

        this.getChildren().add(canvas);
    }

    private void setTable() {
        table = new TableView();
        projectArrayList = new ArrayList<>();
        try {
            projectArrayList = new Reader().getProjectList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        colIndex = new TableColumn("index");
        colProject_ID = new TableColumn("Project ID");
        colStages = new TableColumn("Stages");

        ObservableList<Project> data = FXCollections.observableArrayList();

        for (int i = 0; i < projectArrayList.size(); i++) {
            data.add(projectArrayList.get(i));
        }

        colIndex.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> p) {
                return new ReadOnlyObjectWrapper((table.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        colProject_ID.setCellValueFactory(new PropertyValueFactory<>("customerProjectId"));
        colStages.setCellValueFactory(new PropertyValueFactory("StagesValue"));

        table.setPrefWidth(200);
        table.setPrefHeight(420);

        colIndex.setSortable(false);

        table.setItems(data);
        table.getColumns().addAll(colIndex, colProject_ID, colStages);
        table.setLayoutX(30);
        table.setLayoutY(30);

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (newValue != null) {

                    Project project = (Project) newValue;
                    text.setText("ProjectID:  " + project.getCustomerProjectId());
                    GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                    Rework(project);
                    createLine(graphicsContext, project);
                }
            }
        });

        this.getChildren().add(table);

    }

    private void createLine(GraphicsContext grapicsContext, Project project) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        grapicsContext.setFill(Color.WHITE);
        grapicsContext.fillRect(0, 0, 500, 300);
        grapicsContext.setFill(Color.GREEN);
        ArrayList<Date> dateArrayList = project.getDatesList();
        ArrayList<String> monthsList = getMonthsList(dateArrayList);
        ArrayList<Stage> stageArrayList = project.getStages();
        int totalMonths = monthsList.size();
        int diff = 480 / totalMonths;
        grapicsContext.fillRect(10, 250, 480, 1);
        grapicsContext.fillRect(10, 245, 1, 10);
        grapicsContext.fillRect(490, 245, 1, 10);

        int x = 10;
        for (int i = 0; i < 100; i++) {

            if (x <= 490) {
                grapicsContext.fillRect(x, 247, 1, 6);
            }
            x += 5;

        }

        x = 10;
        for (int i = 0; i < totalMonths; i++) {

            grapicsContext.setStroke(Color.GREEN);
            grapicsContext.setFont(new Font("serif", 8));
            grapicsContext.strokeText(monthsList.get(i), x, 270);
            if (i != 0) {
                grapicsContext.setFill(Color.RED);

                grapicsContext.fillRect(x - 0.5, 242, 2, 10);
            }

            for (int j = 0; j < stageArrayList.size(); j++) {
                Stage stage = stageArrayList.get(j);
                Date date = stage.getDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                String mont1 = months[calendar.get(Calendar.MONTH)];
                int yer = calendar.get(Calendar.YEAR);
                int temp = 0;

                if (monthsList.get(i).equals(mont1 + " " + yer)) {
                    temp = (diff / 30) * calendar.get(Calendar.DAY_OF_MONTH);
                }

                if (temp > 0) {
                    grapicsContext.setStroke(Color.GRAY);
                    grapicsContext.setFont(new Font("arial", 5));
                    grapicsContext.strokeText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.YEAR), x + temp, 260);
                    grapicsContext.fillRect(x + temp, 250 - ((stage.getNewValue() * 9)), 1, (stage.getNewValue() * 9));
                    grapicsContext.strokeText(stage.getNewValue() + "", x + temp + 4, 250 - ((stage.getNewValue() * 9)));


                    Color color = setColor(stage.getOldValue(), stage.getNewValue());

                    grapicsContext.setFill(color);
                    grapicsContext.fillRect(x + temp, 250 - ((stage.getNewValue() * 9)) - 3, 3, 3);

                    if (minX == Integer.MAX_VALUE) {
                        minX = x + temp;

                    }
                    if (maxX < x + temp) {
                        maxX = x + temp;
                    }
                }
            }
            x += diff;
        }
        Date d1 = null;
        Date d2 = null;
        if (dateArrayList.size() > 0) {
            d1 = dateArrayList.get(0);
            d2 = dateArrayList.get(dateArrayList.size() - 1);
        }
        long dur = d2.getTime() - d1.getTime();

        grapicsContext.setStroke(Color.BLACK);
        grapicsContext.setFont(new Font("arial", 14));
        int posX = minX + ((maxX - minX) / 2) - 50;
        grapicsContext.strokeText("Project ID: " + project.getCustomerProjectId(), 20, 30);
        grapicsContext.setStroke(Color.RED);
        grapicsContext.strokeText("Duration: " + dur / 1000 / 60 / 60 / 24, posX, 120);

        grapicsContext.setFont(new Font("arial", 15));
        grapicsContext.setStroke(Color.BLACK);
        grapicsContext.strokeText("Reworks",380,25);

        grapicsContext.setFont(new Font("arial", 10));
        grapicsContext.strokeText(project.getBeforeAward() + "                              " + project.getAfterAward(), 360, 45);
        grapicsContext.strokeText("Before Awarded " + "     After Awarded", 330, 65);

        grapicsContext.setFill(Color.RED);
        grapicsContext.fillRect(minX, 100, maxX - minX, 2);

    }
        public void Rework(Project project) {
            boolean notReach = true;
            project.setAfterAward(0);
            project.setBeforeAward(0);

            for (Stage stage : project.getStages()) {

                if (stage.getNewValue() == 5){
                    notReach = false;
                }
                if (stage.getOldValue() > stage.getNewValue() && notReach) {
                    project.setBeforeAward(project.getBeforeAward()+1);
                }
                else if (stage.getOldValue() > stage.getNewValue()) {
                    project.setAfterAward(project.getAfterAward()+1);
                }
            }
        }

    private ArrayList<String> getMonthsList(ArrayList<Date> list) {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(list.get(i));
            String month = months[calendar.get(Calendar.MONTH)];
            int year = calendar.get(Calendar.YEAR);
            if (!temp.contains(month + " " + year)) {
                temp.add(month + " " + year);

            }
        }
        return temp;
    }

    public Color setColor(int oldValue, int newValue) {
        Color color;
        if (oldValue > newValue) {
            return color = Color.RED;
        } else {
            return color = Color.GREEN;
        }
    }






}
