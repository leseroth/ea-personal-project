

import java.awt.Component;
import java.io.Serializable;

public abstract interface Demo extends Serializable
{
  public static final int ATTRIBUTE_NONE = 0;
  public static final int ATTRIBUTE_NEW = 1;
  public static final int ATTRIBUTE_BETA = 2;
  public static final int ATTRIBUTE_UPDATED = 4;

  public abstract String getName();

  public abstract String getDescription();

  public abstract String getProduct();

  public abstract Component getDemoPanel();

  public abstract String[] getDemoSource();

  public abstract String getDemoFolder();

  public abstract void dispose();

  public abstract Component getOptionsPanel();

  public abstract int getAttributes();

  public abstract boolean isCommonOptionsPaneVisible();
}