package yanews.malygin.tim.yanews.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by tim on 30.04.17.
 */
@IgnoreExtraProperties
public class News implements Parcelable {
    public String id;
    public String title;
    public String body;

    public News() {
    }

    public News(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.body);
    }

    News(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.body = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}

