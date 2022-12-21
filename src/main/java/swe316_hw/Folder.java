package swe316_hw;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Folder implements Doc {
    private String name;
    private List<Doc> subFiles;
    private Path path;
    private String type="Folder";
    Folder(String name){
        this.name=name;
    }
    Folder(Path path){
        this.path=path;
        try {
            subFiles= setSubs();
        } catch (Exception e) {
        }
    }
    public List<Doc>setSubs() throws Exception{
        return setSubs(path);
    }
    private List<Doc> setSubs(Path path) throws Exception{
        List<Doc> subFiles = new ArrayList<Doc>();
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        if(attr.isDirectory()){
            DirectoryStream<Path> paths =Files.newDirectoryStream(path);
            for(Path tempPath: paths){
                attr=Files.readAttributes(tempPath, BasicFileAttributes.class);
                if (attr.isDirectory())
                subFiles.add( new Folder(tempPath));
            }
        }
        return subFiles;
    }

    Folder(String name,ArrayList<Doc> subFiles){
        this.name=name;
        this.subFiles=subFiles;
    }
    public void add(Doc doc){
        subFiles.add(doc);
    }
    public void delete(Doc doc){
        subFiles.remove(doc);
    }
    @Override
    public String getName() {
        return String.valueOf(path.getFileName());
    }
    @Override
    public List<Doc>getSubFiles(){
        try {
            return getSubFiles(this.getPath());
        } catch (Exception e) {
            return null;
        }
    }
    private List<Doc> getSubFiles(Path path) throws Exception{
        List<Doc> subFiles = new ArrayList<Doc>();
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        if(attr.isDirectory()){
            DirectoryStream<Path> paths =Files.newDirectoryStream(path);
            for(Path tempPath: paths){
                attr=Files.readAttributes(tempPath, BasicFileAttributes.class);
                if (attr.isDirectory())
                subFiles.add( new Folder(tempPath));
                else
                subFiles.add(new File(tempPath));
            }
        }
        return subFiles;
    }
    @Override
    public List<Doc> getSubFolders() {
        return subFiles;
    }

    @Override
    public String toString() {
        return getName();
    }
    @Override
    public String getType() {
        return type;
    }
    @Override
    public String hierarcchy() {
        try {
            return hierarcchy(this);
        } catch (Exception e) {
            return null;
        }
    }
    private String hierarcchy(Doc doc) throws Exception{

        String temp =new String(doc.getName()+doc.getSize());
            for(Doc tempPath: doc.getSubFiles()){
                temp+=" \n "+tempPath.hierarcchy();
            }
            return temp;
    }
    public Path getPath() {
        return path;
    }
    @Override
    public String getSize() {
        try {
            return ("("+((Files.size(getPath())/1024)+")KB"));
        } catch (Exception e) {
            return null;
        }
    }
}
