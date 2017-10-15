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

//    private List<News> generatedNews(){
//        final Title[] titleArray = new Title[]{
//                new Title("%s: История успеха", "Жил был %s со старухой. Поймал золотую рыбку. "),
//                new Title("Даст ли ответы %s?", "Второй день %s молчит. Что бы это значило?"),
//                new Title("%s и дороги: две проблемы России!", "С дорогами решили проблему - их разломали. А вот %s более серьезная проблема."),
//                new Title("Мы снова в кризисе! Виноват ли %s или это наша вина?", "Как сообщает ИТАРТАСС: %s не причем. Но мы напишем еще пару статей на этот повод! Оставайтесь с нами"),
//                new Title("%s оказался смертелен. Небольшая доза разорвала хомячка пополам! Смотреть Видео", "Ученые смогли доказать что небольшое количество смертельно для мертвого хомяка!"),
//                new Title("%s в деле.", "Сегодня %s вышел на работу! Третья неделя запоя закончилась! Бабушки на скамейках рады - двор будет чист!"),
//                new Title("Смотрел %s мультики в детстве?", "Как сообщают ученые: Графитифолс терапия помогла многим. %s тоже нуждается в такой терапии"),
//                new Title("В небе снова появился %s. Чего ждать?", "%s выглядит дружелюбно. Но почему оно в небе?"),
//                new Title("Свобода слова снова в деле! %s постарался.", "Спасибо, %s. Мы теперь можем готовить отменную лапшу. Надеямся, дорогие читатели, ваши уши готовы к тонне макарон?"),
//                new Title("%s экспериментирует над людьми!", "Прошлые выходные были замечены люди. Среди них был %s! Он снова взялся за старое?"),
//                new Title("%s укусил собачку! Видео!", "Собаку смогли откачать! %s больше ей не грозит."),
//                new Title("%s вернулся! Он обещал и он вернулся!", "Выйдя из окна, %s смог подняться обратно по лестнице!"),
//                new Title("%s нашел золото в кармане отца!", "МВД ведет дело. Отцу грозит большой срок. %s настоящий гражданин своей страны!"),
//                new Title("%s увидел необходимость в посреднике между Чебурашкой и Геной", "%s убежден что крокодил должен смириться с необходимостью жить рядом с монстром!"),
//                new Title("%s провел «очень дружелюбный» разговор с дворником Васей", "Дворник Вася из политических убеждении не убирал территорию с табличкой \'%s\'."),
//                new Title("В техасе из-за торнадо пропал %s", "Как говорят очевидцы, торнадо унес с собой, дом, %s и даже их собачку")
//        };
//
//        final String[] namesArray = new String[]{
//                "Дональд дак", "Карлсон", "Рыба", "Папа римский", "Святой Себастьян Чревоугодник III",
//                "Башмак", "Капитан Очевидность", "Святой угодник", "Николай", "Гоблин", "Рыцарь на белом коне",
//                "Баба Таня", "Протеанин", "Шепард", "Горлум", "Костоправ", "Душелов", "Пузырь"
//        };
//        List<News> news = new ArrayList<>();
//        final List<Title> title = new ArrayList<>(titleArray.length);
//        final List<String> names = new ArrayList<>(namesArray.length);
//
//
//        for (int i = 0; i < 30; i++) {
//            if (title.isEmpty()) {
//                title.addAll(Arrays.asList(titleArray));
//                Collections.shuffle(title);
//            }
//            if (names.isEmpty()) {
//                names.addAll(Arrays.asList(namesArray));
//                Collections.shuffle(names);
//            }
//            String name = names.get(0);
//            String titleText = String.format(title.get(0).title, name);
//            String body = String.format(title.get(0).description, name);
//            News newNews = new News("id_" + i, titleText, body);
//            news.add(newNews);
//            title.remove(0);
//            names.remove(0);
//        }
//    return news;
//    }
//
//    class Title {
//        public final String title, description;
//
//        Title(String title, String description) {
//            this.title = title;
//            this.description = description;
//        }
//    }

    public interface GetNewsResult extends ApiResult {

        void showLoading();

        void onNewsLoaded(@NonNull List<News> news);

        void onError(@Nullable String msg);
    }
}
