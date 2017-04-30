package yanews.malygin.tim.yanews.api;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by timofey.malygin on 23/04/2017.
 */
public interface ApiKeys {

    public static final int LOGOUT = -1;
    public static final int LOGIN = 0;
    public static final int REGISTRATION = 1;
    public static final int DELETE_USER = 2;
    public static final int GET_NEWS = 3;

    @IntDef({
            LOGIN, REGISTRATION, LOGOUT, DELETE_USER, GET_NEWS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApiKey{}
}
