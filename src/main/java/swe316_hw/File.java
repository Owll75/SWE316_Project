package swe316_hw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class File implements Doc{
    private String name;
    private String size;
    private String type="File";
    private Path path;
    File(String name){
        this.name=name;
    }
    File(Path path){
        this.path=path;
    }
    @Override
    public String getName() {
        return String.valueOf(path.getFileName());
    }
    @Override
    public String toString() {
        return getName();
    }
    @Override
    public List<Doc> getSubFiles() {
        return null;
    }
    @Override
    public String getType() {
        return type;
    }
    @Override
    public List<Doc> getSubFolders() {
        return null;
    }
    @Override
    public String hierarcchy() {
        return "   - "+this.getName()+this.getSize();
    }
    @Override
    public String getSize() {
        try {
            return (" ("+((Files.size(path))/1024)+")KB");
        } catch (Exception e) {
            return null;
        }
    }
}
