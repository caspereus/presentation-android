package taystudio.presentasi.presentasi.RetrofitService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import taystudio.presentasi.presentasi.Models.Article;
import taystudio.presentasi.presentasi.Models.Event;
import taystudio.presentasi.presentasi.Models.EventFile;
import taystudio.presentasi.presentasi.Models.Gallery;
import taystudio.presentasi.presentasi.Models.JSONArticle;
import taystudio.presentasi.presentasi.Models.JSONEvent;
import taystudio.presentasi.presentasi.Models.JSONGallery;
import taystudio.presentasi.presentasi.Models.JSONImageGallery;
import taystudio.presentasi.presentasi.Models.JSONVideo;
import taystudio.presentasi.presentasi.Models.Like;
import taystudio.presentasi.presentasi.Models.Presentator;
import taystudio.presentasi.presentasi.Models.Video;
import taystudio.presentasi.presentasi.Models.Visitors;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("api/visitors/create")
    Call<Visitors> addVisitors(@Field("name") String name,@Field("email") String email,@Field("phone") String phone);

    @GET("api/presentator/data")
    Call<Presentator> getPresentator();

    @GET("api/event")
    Call<JSONEvent> getEvent();

    @GET("api/event_file/{id}")
    Call<EventFile> getFile(@Path("id") String id);

    @GET("api/event_file/download/{id}")
    Call<Visitors> download(@Path("id") String id);

    @GET("api/article")
    Call<JSONArticle> getArticle();

    @GET("api/gallery")
    Call<JSONGallery> getGallery();

    @GET("api/gallery/detail/{id}")
    Call<JSONImageGallery> getImages(@Path("id") String id);

    @GET("api/presentator/event")
    Call<JSONEvent> getMainEvent();

    @GET("api/presentator/article")
    Call<JSONArticle> getMainArticle();

    @GET("api/gallery/view_count/{id}")
    Call<Gallery> count_view(@Path("id") String id);

    @GET("api/video/view_count/{id}")
    Call<Video> count_video_view(@Path("id") String id);

    @GET("api/event/view_count/{id}")
    Call<Event> count_event_view(@Path("id") String id);

    @GET("api/article/view_count/{id}")
    Call<Article> count_article_view(@Path("id") String id);

    @GET("api/video")
    Call<JSONVideo> getVideo();


    @GET("api/like/check/{post_id}/{user_id}/{type}")
    Call<Like> check_like(@Path("post_id") String post_id,@Path("user_id") String user_id,@Path("type") String type);

    @GET("api/like/{post_id}/{user_id}/{type}")
    Call<Like> do_like(@Path("post_id") String post_id,@Path("user_id") String user_id,@Path("type") String type);

    @GET("api/like/un/{post_id}/{user_id}/{type}")
    Call<Like> unlike(@Path("post_id") String post_id,@Path("user_id") String user_id,@Path("type") String type);
}