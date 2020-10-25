package ${PACKAGE_NAME};

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

#set( $USER = "TapuMandal" )
#set( $EMAIL = "online.tapu@gmail.com" )
/**
* Created by ${USER} on ${DATE}.
* For any query ask ${EMAIL} 
*/

#parse("File Header.java")
public class ${NAME} extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }
}