package ${PACKAGE_NAME};

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

#set( $USER = "TapuMandal" )
#set( $EMAIL = "online.tapu@gmail.com" )
/**
* Created by ${USER} on ${DATE}.
* For any query ask ${EMAIL} 
*/

#parse("File Header.java")
public class ${NAME} extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
