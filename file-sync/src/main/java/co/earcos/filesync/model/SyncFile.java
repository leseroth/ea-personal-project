package co.earcos.filesync.model;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class SyncFile implements Comparable<SyncFile> {

  private File file;
  private Set<SyncFile> fileSet;

  public SyncFile(File file) {
    this.file = file;
    if(file.isDirectory()) {
      fileSet = new TreeSet<SyncFile>();
    }
  }

  public File getFile() {
    return file;
  }

  public boolean isFile(){
    return file.isFile();
  }

  public Set<SyncFile> getFileSet(){
    return fileSet;
  }

  public void addChild(SyncFile syncFile){
    if(file.isDirectory()) {
      fileSet.add(syncFile);
    }
  }

  @Override
  public String toString() {
    String fileName = "";
    if (file != null) {
      fileName = file.getName();
      int index = fileName.lastIndexOf(File.separator);
      fileName = index == -1 ? fileName : fileName.substring(index);
    }
    return fileName;
  }

  @Override
  public int compareTo(SyncFile other) {
    int compare = 0;
    if(other != null){
      compare = file.compareTo(other.file);
    }
    return compare;
  }
}
