package com.example.admin.stwitter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.*;
import retrofit2.Call;

import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String secret;
    String token;
public String header;

TabLayout tabLayout;
    final private int TabCount=4;
List<Fragment> frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        frag=new ArrayList<>();
        frag.add(new PlaceholderFragment());
        frag.add(new PlaceholderFragment());
        frag.add(new PlaceholderFragment());
        frag.add(new PlaceholderFragment());
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),frag);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout=(TabLayout)findViewById(R.id.tabs);
       // tabLayout.getTabAt(2).setIcon(R.drawable.search);
        tabLayout.setupWithViewPager(mViewPager);
        setActiveSession();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TweetComposeActivity.class));
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      //  navigationView.setItemBackground();
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void setActiveSession() {
        SharedPreferences sharedPreferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        String token=sharedPreferences.getString(LoginStartActivity.TOKEN,null);
        String TokenSecret=sharedPreferences.getString(LoginStartActivity.TOKENSECRET,null);
        String UserName=sharedPreferences.getString(LoginStartActivity.USERNAME,null);
        long userId=sharedPreferences.getLong(LoginStartActivity.USERID,-1);
        TwitterAuthToken authToken=new TwitterAuthToken(token,TokenSecret);
        TwitterSession session=new TwitterSession(authToken,userId,UserName);
        TwitterCore.getInstance().getSessionManager().setActiveSession(session);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent(MainActivity.this,UserProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent= new Intent(MainActivity.this,DirectMessageListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(MainActivity.this,TrendListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent= new Intent(MainActivity.this,AccountDetailActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setTitle("         LOG OUT");
            builder.setCancelable(false);
//            builder.setMessage("Are you sure you want to delete ??");

            View v = getLayoutInflater().inflate(R.layout.dialog_view,null);

            final  TextView tv = (TextView) v.findViewById(R.id.dialogTextView);
            tv.setText("Are you sure you want to Log Out??");
            builder.setView(v);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TwitterCore.getInstance().getSessionManager().clearActiveSession();
                    startActivity(new Intent(MainActivity.this,LoginStartActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();



        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.ListFragment /*implements CustomAdapter.OnTweetClickListener*/ {
        ListView listView;
        SharedPreferences sharedPreferences;



        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }




        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int sec_no=   getArguments().getInt(ARG_SECTION_NUMBER);
            if(sec_no==1){
                getActivity().setTitle("Home");


               // final ViewGroup tweetRegion=(ViewGroup)view.findViewById(R.id.ViewMain);

            /* final  LinearLayout  myLayout=(LinearLayout)view.findViewById(R.id.ViewMain);

                final long tweetId = 510908133917487104L;
               TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        myLayout.addView(new TweetView(getContext(), result.data));
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });*//*
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                StatusesService statusesService = twitterApiClient.getStatusesService();
                Call<Tweet> call = statusesService.show(524971209851543553L, null, null, null);
                call.enqueue(new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        myLayout.addView(new TweetView(getContext(), result.data));
                    }

                    public void failure(TwitterException exception) {
                        //Do something on failure
                    }
                });*/
               /* View view=inflater.inflate(R.layout.fragment_show_tweets,container,false);
                listView=(ListView)view.findViewById(R.id.tweetListView);
            final UserTimeline    userTimeline=new UserTimeline.Builder().screenName("Tweets").build();
             final TweetTimelineListAdapter   tweetTimelineListAdapter= new TweetTimelineListAdapter.Builder(getContext()).setTimeline(userTimeline).build();
              CustomAdapter  customAdapter=new CustomAdapter(getContext(),userTimeline);
              // listView.setAdapter(tweetTimelineListAdapter);
                listView=(ListView)view.findViewById(R.id.tweetListView);
            listView.setAdapter(customAdapter);

                customAdapter.notifyDataSetChanged();*/
                final View view= inflater.inflate(R.layout.fragment_show_tweets,container,false);
                final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
               /* TwitterAuthConfig authConfig=TwitterCore.getInstance().getAuthConfig();
                TwitterAuthToken authToken=TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();
                OAuthSigning authSigning= new OAuthSigning(authConfig,authToken);
                HashMap<String,String> params=new HashMap<>();
                //  params.put("id","1212");
                String header=authSigning.getAuthorizationHeader("GET","https://api.twitter.com/1.1/statuses/home_timeline.json",null);
                Log.i("MY",header);
                final List<Long> ids= new ArrayList<>();*/
                /*String urlString = "http://api.yourbackend.com/check_credentials.json";
                final HomeTimelineAsyncTask homeTimelineAsyncTask = new HomeTimelineAsyncTask();
                homeTimelineAsyncTask.execute(urlString);
*/
                /*homeTimelineAsyncTask.setOnDownloadCompleteListener(new OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(ArrayList<Home> coursesList) {
                        ids.clear();
                        for(int i=0;i<coursesList.size();i++){
                            Long id= coursesList.get(i).id;
                            ids.add(id);
                        }
                    }
                });
*/
//                Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.twitter.com/1.1/").addConverterFactory(GsonConverterFactory.create()).build();
//                ApiInterface apiInterface= retrofit.create(ApiInterface.class);
                final List<Long> ids= new ArrayList<>();
                ApiInterface apiInterface = ApiClient.getApiInterface();
                Call<ArrayList<Home>> call=apiInterface.getHomeTimeLine();
                call.enqueue(new retrofit2.Callback<ArrayList<Home>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Home>> call, Response<ArrayList<Home>> response) {
                        Log.d("error", String.valueOf(response.code()));
                        Log.i("String", String.valueOf(response.body()));
                        ArrayList<Home> hometimeline=response.body();
                        for(int i=0;i<hometimeline.size();i++){
                            Long id= hometimeline.get(i).id;
                            ids.add(id);

                        }
                        Log.i("MyIds",""+ids.size());
                        TweetUtils.loadTweets(ids, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                final  FixedTweetTimeline fixedTimeline=new FixedTweetTimeline.Builder().setTweets(result.data).build();
                                final TweetTimelineListAdapter  adapter=new TweetTimelineListAdapter.Builder(getActivity()).setTimeline(fixedTimeline).build();
                                setListAdapter(adapter);
                               swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                                            @Override
                                            public void success(Result<TimelineResult<Tweet>> result) {
                                                swipeRefreshLayout.setRefreshing(false);

                                            }

                                            @Override
                                            public void failure(TwitterException exception) {

                                            }
                                        });
                                    }
                                });

                            }

                            @Override
                            public void failure(TwitterException exception) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Home>> call, Throwable t) {

                    }
                });



                return  view;


            }
            if(sec_no==4){
                final  View view=inflater.inflate(R.layout.fragment_show_tweets,container,false);
                getActivity().setTitle("Suggestions to Follow");
                final ArrayList<FollowClass> f=new ArrayList<>();
                final ListView listView=(ListView)view.findViewById(android.R.id.list);
                final CustomAdapter customAdapter=new CustomAdapter(getContext(),f);
                final ArrayList<String>ids= new ArrayList<>();
                ApiInterface apiInterface=ApiClient.getApiInterface();
                Call<SuggestionMembers> call=apiInterface.getSuggestedMembers();
                call.enqueue(new retrofit2.Callback<SuggestionMembers>() {
                    @Override
                    public void onResponse(Call<SuggestionMembers> call, Response<SuggestionMembers> response) {
                        SuggestionMembers s= response.body();
                        ArrayList<SuggestionMembers.Users> userses=s.users;
                        for(int i=0;i<userses.size();i++){
                            FollowClass fc=new FollowClass();
                            fc.Name= userses.get(i).name;
                            fc.Picture= String.valueOf(userses.get(i).profile_image_url);
                            f.add(fc);
                            ids.add(userses.get(i).id_str);
                        }
                        setListAdapter(customAdapter);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                ApiInterface apiInterface1=ApiClient.getApiInterface();
                                Call<SuggestionFollow> call1=apiInterface1.SuggFollow(Long.valueOf(ids.get(position)),"true");
                                call1.enqueue(new retrofit2.Callback<SuggestionFollow>() {
                                    @Override
                                    public void onResponse(Call<SuggestionFollow> call, Response<SuggestionFollow> response) {
                                        SuggestionFollow s=response.body();
                                        if(response.code()==200){
                                            Toast.makeText(getContext(),"Following "+s.name+" Now",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SuggestionFollow> call, Throwable t) {

                                    }
                                });
                                return true;
                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<SuggestionMembers> call, Throwable t) {

                    }
                });
                /*final  View view=inflater.inflate(R.layout.fragment_show_tweets,container,false);
                final CollectionTimeline collectionTimeline=new CollectionTimeline.Builder().id(8044403L).build();
                final TweetTimelineListAdapter adapter=new TweetTimelineListAdapter(getActivity(),collectionTimeline);
                setListAdapter(adapter);
                return  view;*/
                return view;

            }
            if(sec_no==2){
                final View view=inflater.inflate(R.layout.searchtimeline_fragment,container,false);
                getActivity().setTitle("Search");
                final ListView listView=(ListView)view.findViewById(android.R.id.list);
                SearchView searchView=(SearchView)view.findViewById(R.id.SearchView);
                searchView.setVisibility(View.VISIBLE);
                ApiInterface apiInterface=ApiClient.getApiInterface();
                Call<ArrayList<Suggestions>> call=apiInterface.getSuggestions();
                call.enqueue(new retrofit2.Callback<ArrayList<Suggestions>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Suggestions>> call, Response<ArrayList<Suggestions>> response) {
                        ArrayList<Suggestions> suggestionses=response.body();
                        final ArrayList<String> SugList=new ArrayList<String>();
                        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,SugList);
                        for(int i=0; i<suggestionses.size();i++){
                            String n= suggestionses.get(i).name;
                            SugList.add(n);
                        }
                        setListAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent= new Intent(getContext(),ShowListActivity.class);
                                intent.putExtra("listId",SugList.get(position));
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Suggestions>> call, Throwable t) {

                    }
                });

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                      /*  final List<Long>ids=new ArrayList<>();
                        TwitterAuthConfig authConfig=TwitterCore.getInstance().getAuthConfig();
                        TwitterAuthToken authToken=TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();
                        OAuthSigning authSigning= new OAuthSigning(authConfig,authToken);
                        HashMap<String,String> params=new HashMap<>();
                        params.put("q","Twitter%20API");
                        String header=authSigning.getAuthorizationHeader("GET","https://api.twitter.com/1.1/users/search.json",params);
                        Log.d("ABC",header);
                        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.twitter.com/1.1/").addConverterFactory(GsonConverterFactory.create()).build();
                        ApiInterface apiInterface= retrofit.create(ApiInterface.class);
                        Call<ArrayList<status>> call=apiInterface.getSearchTimeLine(header,newText);
                        call.enqueue(new retrofit2.Callback<ArrayList<status>>() {
                            @Override
                            public void onResponse(Call<ArrayList<status>> call, Response<ArrayList<status>> response) {
                                Log.d("XYZ", String.valueOf(response.body()));
                                ArrayList<status> hometimeline=response.body();
                                for(int i=0;i<hometimeline.size();i++){
                                    Long id= hometimeline.get(i).id;
                                 //   Long id=s.st.id;
                                    ids.add(id);

                                }
                                Log.i("MyIds",""+ids.size());
                                TweetUtils.loadTweets(ids, new Callback<List<Tweet>>() {
                                    @Override
                                    public void success(Result<List<Tweet>> result) {
                                        final  FixedTweetTimeline fixedTimeline=new FixedTweetTimeline.Builder().setTweets(result.data).build();
                                        final TweetTimelineListAdapter  adapter=new TweetTimelineListAdapter.Builder(getActivity()).setTimeline(fixedTimeline).build();
                                        setListAdapter(adapter);
                                    }

                                    @Override
                                    public void failure(TwitterException exception) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<ArrayList<status>> call, Throwable t) {

                            }

                        });*/

                        final SearchTimeline searchTimeline=new SearchTimeline.Builder().query(newText).maxItemsPerRequest(50).build();
                        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getActivity(),searchTimeline);
                        setListAdapter(adapter);

                        return false;
                    }
                });

                return view;
            }
            if(sec_no==3){
                View view=inflater.inflate(R.layout.fragment_show_tweets,container,false);
                getActivity().setTitle("Your List");
                final ListView listView=(ListView)view.findViewById(android.R.id.list);
                final ArrayList<String> names=new ArrayList<>();
                final ArrayList<String> fullNames= new ArrayList<>();
                ApiInterface apiInterface=ApiClient.getApiInterface();
                Call<ArrayList<ListTwitter>> call= apiInterface.getList();
                call.enqueue(new retrofit2.Callback<ArrayList<ListTwitter>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ListTwitter>> call, Response<ArrayList<ListTwitter>> response) {
                        ArrayList<ListTwitter> l=response.body();
                        for(int i=0; i<l.size();i++){
                            names.add(l.get(i).name);
                            fullNames.add(l.get(i).full_name);
                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,names);
                        setListAdapter(arrayAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getContext(),ShowListActivity.class);
                                intent.putExtra("listId",names.get(position));
                                startActivity(intent);

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<ArrayList<ListTwitter>> call, Throwable t) {

                    }
                });

                return view;

            }
                        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        private void loadTweet(long l, ViewGroup tweetRegion, int jack_regular_tweet) {
        }


    /*    @Override
        public void OnTweetClicked(int position, Tweet tweet) {
            Toast.makeText(getActivity(),tweet.text,Toast.LENGTH_LONG).show();
        }
*/
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;
        public SectionsPagerAdapter(FragmentManager fm,List<Fragment>fragments) {
            super(fm);
            fragmentList=fragments;
        }

       // @Override
        public Fragment getItem(int position) {
           if(position==0){
              PlaceholderFragment placeholderFragment=(PlaceholderFragment) fragmentList.get(position);
               Bundle args= new Bundle();
               args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER,1);
               placeholderFragment.setArguments(args);
               return placeholderFragment;
           }
          if(position==1){
              PlaceholderFragment placeholderFragment=(PlaceholderFragment) fragmentList.get(position);
              Bundle args= new Bundle();
              args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER,2);
              placeholderFragment.setArguments(args);
              return placeholderFragment;
          }
          if(position==2){
              PlaceholderFragment placeholderFragment=(PlaceholderFragment) fragmentList.get(position);
              Bundle args= new Bundle();
              args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER,3);
              placeholderFragment.setArguments(args);
              return placeholderFragment;
          }if(position==3){
                PlaceholderFragment placeholderFragment=(PlaceholderFragment) fragmentList.get(position);
                Bundle args= new Bundle();
                args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER,4);
                placeholderFragment.setArguments(args);
                return placeholderFragment;
            }
           return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
           return fragmentList.size();
          // return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:  // tabLayout.getTabAt(0).setIcon(R.drawable.home);
                    return "HOME";
                case 1:// tabLayout.getTabAt(1).setIcon(R.drawable.search);
                    return "SEARCH";
                case 2: //tabLayout.getTabAt(2).setIcon(R.drawable.notification);
                    return "LIST";
                case 3:// tabLayout.getTabAt(3).setIcon(R.drawable.tw__composer_logo_white);
                    return "TO FOLLOW";
            }
            return null;
        }
    }
}
