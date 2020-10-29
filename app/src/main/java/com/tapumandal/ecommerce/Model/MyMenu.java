package com.tapumandal.ecommerce.Model;

import java.util.List;

/**
 * Created by TapuMandal on 10/27/2020.
 * For any query ask online.tapu@gmail.com
 */
public class MyMenu {
     String menuName;
     String icon;
     boolean isGroup;
     boolean hasChildren;
     boolean open;
     List<MyMenu> child;
}
