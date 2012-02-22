package co.earcos.filesync.view;

import co.earcos.filesync.model.SyncFile;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

class SyncFileTreeModel implements TreeModel {

  protected SyncFile root;

  public SyncFileTreeModel(SyncFile root) {
    this.root = root;
  }

  @Override
  public Object getRoot() {
    return root;
  }

  @Override
  public boolean isLeaf(Object node) {
    return ((SyncFile) node).isFile();
  }

  @Override
  public int getChildCount(Object parent) {
    SyncFile parentSyncFile = (SyncFile) parent;
    return parentSyncFile.isFile() ? 0 : parentSyncFile.getFileSet().size();
  }

  @Override
  public Object getChild(Object parent, int index) {
    SyncFile parentSyncFile = (SyncFile) parent;
    SyncFile childSyncFile = null;
    if (!parentSyncFile.isFile()) {
      childSyncFile = (SyncFile) parentSyncFile.getFileSet().toArray()[index];
    }
    return childSyncFile;
  }

  @Override
  public int getIndexOfChild(Object parent, Object child) {
    SyncFile parentSyncFile = (SyncFile) parent;
    SyncFile childSyncFile = (SyncFile) child;
    int position = -1;

    if (!parentSyncFile.isFile()) {
      int helper = -1;
      childSearch:
      for (Object o : parentSyncFile.getFileSet().toArray()) {
        SyncFile parentChild = (SyncFile) o;
        helper++;
        if (parentChild.equals(childSyncFile)) {
          position = helper;
          break childSearch;
        }
      }
    }

    return position;
  }

  // This method is invoked by the JTree only for editable trees.
  // This TreeModel does not allow editing, so we do not implement
  // this method.  The JTree editable property is false by default.
  @Override
  public void valueForPathChanged(TreePath path, Object newvalue) {
  }

  // Since this is not an editable tree model, we never fire any events,
  // so we don't actually have to keep track of interested listeners
  @Override
  public void addTreeModelListener(TreeModelListener l) {
  }

  @Override
  public void removeTreeModelListener(TreeModelListener l) {
  }
}