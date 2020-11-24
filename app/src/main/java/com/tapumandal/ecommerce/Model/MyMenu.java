package com.tapumandal.ecommerce.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TapuMandal on 10/27/2020.
 * For any query ask online.tapu@gmail.com
 */
public class MyMenu implements Serializable {
     String menuName;
     String icon;
     boolean isGroup;
     boolean hasChildren;
     boolean open;
     List<MyMenu> child;

     public String getMenuName() {
          return menuName;
     }

     public void setMenuName(String menuName) {
          this.menuName = menuName;
     }

     public String getIcon() {
          return icon;
     }

     public void setIcon(String icon) {
          this.icon = icon;
     }

     public boolean isGroup() {
          return isGroup;
     }

     public void setGroup(boolean group) {
          isGroup = group;
     }

     public boolean isHasChildren() {
          return hasChildren;
     }

     public void setHasChildren(boolean hasChildren) {
          this.hasChildren = hasChildren;
     }

     public boolean isOpen() {
          return open;
     }

     public void setOpen(boolean open) {
          this.open = open;
     }

     public List<MyMenu> getChild() {
          return child;
     }

     public void setChild(List<MyMenu> child) {
          this.child = child;
     }
}
