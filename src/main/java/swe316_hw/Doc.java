package swe316_hw;

import java.util.List;

public interface Doc {
    @Override
    String toString();
    List<Doc> getSubFiles();
    List<Doc> getSubFolders();
    String hierarcchy();
    String getType();
    String getSize();
    String getName();
}
