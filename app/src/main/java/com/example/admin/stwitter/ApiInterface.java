package com.example.admin.stwitter;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Admin on 7/24/2017.
 */

public interface ApiInterface {


    @GET("statuses/home_timeline.json")
   // @Headers("Authorization : OAuth oauth_consumer_key=\"p8fO39ZHyo2NKqGce8K4fKokC\", oauth_nonce=\"1166272431118376384273109887943729\", oauth_signature=\"iJlCKulX1uuLFBHG99RMD2MT6DE%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1500915808\", oauth_token=\"885180249776041988-N9uapgLhxx1GtRVamaEllS0Gv0zKsAA\", oauth_version=\"1.0\"\n ")
    Call<ArrayList<Home>>getHomeTimeLine();

    @GET("users/search.json")
    Call<ArrayList<status>>getSearchTimeLine(@Header("Authorization")String auth, @Query("q") String query);
@GET("users/show.json")
    Call<user>getProfilePic(@Query("user_id") Long id);
    @GET("users/profile_banner.json")
    Call<ProfileBanner>getProfileBanner();
    @GET("followers/list.json")
    Call<Followers>getFollowList();
    @GET("friends/list.json")
    Call<Followers>getFollowerList();

    @GET("direct_messages/events/list.json")
    Call<DirectMessagesID>getDMessages();
    @GET("trends/place.json")
    Call<ArrayList<Trends>>getTrends(@Query("id") Long id);
    @FormUrlEncoded
    @POST("direct_messages/events/new.json")
    Call<DMPost>postMessage(@Field("recipient_id") String id,@Field("text") String text);

    @GET("lists/list.json")
    Call<ArrayList<ListTwitter>>getList();

    @GET("users/suggestions.json")
    Call<ArrayList<Suggestions>>getSuggestions();

    @GET("users/suggestions/Bollywood.json")
    Call<SuggestionMembers>getSuggestedMembers();

    @POST("friendships/create.json")
    Call<SuggestionFollow>SuggFollow(@Query("user_id") long id,@Query("follow") String f);

    @POST("friendships/destroy.json")
    Call<UserUnfollow>Unfollow(@Query("user_id") long id);
}
