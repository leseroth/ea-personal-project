
import java.io.File;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * The methods in this class allow the JTree component to traverse the file
 * system tree and display the files and directories.
 *
 */
class FileTreeModel implements TreeModel {
  // We specify the root directory when we create the model.

  protected FileTree root;

  public FileTreeModel(File root) {
    this.root = new FileTree(root);
  }

  // The model knows how to return the root object of the tree
  @Override
  public Object getRoot() {
    return root;
  }

  // Tell JTree whether an object in the tree is a leaf
  @Override
  public boolean isLeaf(Object node) {
    return ((FileTree) node).getFile().isFile();
  }

  // Tell JTree how many children a node has
  @Override
  public int getChildCount(Object parent) {
    String[] children = ((FileTree) parent).getFile().list();
    if (children == null) {
      return 0;
    }
    return children.length;
  }

  // Fetch any numbered child of a node for the JTree.
  // Our model returns File objects for all nodes in the tree.  The
  // JTree displays these by calling the File.toString() method.
  @Override
  public Object getChild(Object parent, int index) {
    File parentFile = ((FileTree) parent).getFile();
    String[] children = parentFile.list();
    if ((children == null) || (index >= children.length)) {
      return null;
    }
    return new FileTree(new File(parentFile, children[index]));
  }

  // Figure out a child's position in its parent node.
  @Override
  public int getIndexOfChild(Object parent, Object child) {
    File parentFile = ((FileTree) parent).getFile();
    File childFile = ((FileTree) child).getFile();
    String[] children = parentFile.list();
    if (children == null) {
      return -1;
    }
    String childname = childFile.getName();
    for (int i = 0; i < children.length; i++) {
      if (childname.equals(children[i])) {
        return i;
      }
    }
    return -1;
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