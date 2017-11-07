package yanews.malygin.tim.yanews.api;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ApiKeys {

    int LOGOUT = -1;
    int LOGIN = 0;
    int REGISTRATION = 1;
    int DELETE_USER = 2;
    int GET_NEWS = 3;

    @IntDef({
            LOGIN, REGISTRATION, LOGOUT, DELETE_USER, GET_NEWS
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ApiKey {
    }
}
