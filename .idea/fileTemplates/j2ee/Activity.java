package ${PACKAGE_NAME};

import android.app.Activity;
import android.os.Bundle;

#set( $USER = "TapuMandal" )
#set( $EMAIL = "online.tapu@gmail.com" )
/**
* Created by ${USER} on ${DATE}.
* For any query ask ${EMAIL} 
*/

#parse("File Header.java")
public class ${NAME} extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}