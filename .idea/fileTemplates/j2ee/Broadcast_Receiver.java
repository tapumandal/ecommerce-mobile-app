package ${PACKAGE_NAME};

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

#set( $USER = "TapuMandal" )
#set( $EMAIL = "online.tapu@gmail.com" )
/**
* Created by ${USER} on ${DATE}.
* For any query ask ${EMAIL} 
*/
#parse("File Header.java")
public class ${NAME} extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    }
}