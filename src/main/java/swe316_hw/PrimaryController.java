package swe316_hw;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

public class PrimaryController implements Initializable{
    @FXML
    private TreeView<Doc> treeView1;
    @FXML
    private TextArea textArea1;
    @FXML
    private Button button;
    private Path currentPath;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        textArea1.clear();
        TreeItem<Doc> root;
        try {
            root = getSubs();
            treeView1.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectItem() throws Exception {
        textArea1.clear();
        textArea1.setText(treeView1.getSelectionModel().getSelectedItem().getValue().hierarcchy());
    }

    public Doc getFolder() throws Exception{
        Folder rootFolder=new Folder(currentPath);
        return rootFolder;
    }
    public void selectFolder(){
        DirectoryChooser fc = new DirectoryChooser();
        File tempFile=fc.showDialog(null);
        currentPath= tempFile.toPath();
        initialize(null, null);
    }
    public TreeItem<Doc> getSubs(){
        try {
            return getSubs(getFolder());
        } catch (Exception e) {
        }
        return null;
    }

    private TreeItem<Doc> getSubs(Doc folder){
        TreeItem<Doc>tempRoot =new TreeItem<Doc>(folder);
            for(Doc tempPath: tempRoot.getValue().getSubFolders()){
                TreeItem<Doc> tempPathItem = new TreeItem<Doc>(tempPath);
                for (int i = 0; i < tempPath.getSubFolders().size(); i++) {
                    if(tempPath.getSubFolders().get(i).getType().equals("Folder")){
                        TreeItem<Doc> temp = new TreeItem<Doc>(tempPath.getSubFolders().get(i));
                        tempPathItem.getChildren().add(getSubs(temp.getValue()));
                    }
                }
                tempRoot.getChildren().add(tempPathItem);
            }
            return tempRoot;
    }


}
