package yanews.malygin.tim.yanews.api.methods;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.data.News;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.util.ThreadUtil;

public class GetNewsMethod extends ApiMethod<GetNewsMethod.GetNewsResult> implements Runnable {

    public GetNewsMethod(SimpleIdlingResource idleResources) {
        super(idleResources);
    }

    @Override
    public void send() {
        startLoading();
        run();
    }

    @Override
    void startLoading() {
        super.startLoading();
        GetNewsResult callback = getCallback();
        if (callback != null) {
            callback.showLoading();
        }
    }

    @Override
    public void run() {

        Query query = FirebaseDatabase.getInstance().getReference().child("/news")
                .limitToFirst(100);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<News> loadedNews = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    News news = data.getValue(News.class);
                    loadedNews.add(news);
                }
                ThreadUtil.postOnMainDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callLoaded(loadedNews);
                    }
                });
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                ThreadUtil.postOnMain(new Runnable() {
                    @Override
                    public void run() {
                        callError(databaseError.getMessage());
                    }
                });
            }
        });
    }

    private void callLoaded(@NonNull List<News> news) {
        GetNewsResult callback = getCallback();
        if (callback != null) {
            callback.onNewsLoaded(news);
        }
        finishLoading();
    }

    private void callError(String err) {
        GetNewsResult callback = getCallback();
        if (callback != null) {
            callback.onError(err);
        }
        finishLoading();
    }

    public interface GetNewsResult extends ApiResult {

        void showLoading();

        void onNewsLoaded(@NonNull List<News> news);

        void onError(@Nullable String msg);
    }
}
