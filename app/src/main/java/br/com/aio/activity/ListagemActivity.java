package br.com.aio.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.adapter.FeedItem;
import br.com.aio.adapter.MyRecyclerViewAdapter;

public class ListagemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "RecyclerViewExample";
    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=30";
        new DownloadTask().execute(url);
    }


    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new MyRecyclerViewAdapter(ListagemActivity.this, feedsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(ListagemActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        result = "{\n" +
                "  \"status\": \"ok\",\n" +
                "  \"count\": 30,\n" +
                "  \"pages\": 2,\n" +
                "  \"category\": {\n" +
                "    \"id\": 284,\n" +
                "    \"slug\": \"news\",\n" +
                "    \"title\": \"News &amp; Updates\",\n" +
                "    \"description\": \"\",\n" +
                "    \"parent\": 0,\n" +
                "    \"post_count\": 37\n" +
                "  },\n" +
                "  \"posts\": [\n" +
                "    {\n" +
                "      \"id\": 12765,\n" +
                "      \"type\": \"post\",\n" +
                "      \"slug\": \"wwdc-2017-highlights-watchos-ios-11-apple-pay-and-more\",\n" +
                "      \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMH2WFHTV8GAU8FiPY3vQw65w7b_7ECUE-6nNz0dpcgKngSPph\",\n" +
                "      \"status\": \"publish\",\n" +
                "      \"title\": \"WWDC 2017 Highlights: WatchOS, iOS 11, Apple Pay and more\",\n" +
                "      \"title_plain\": \"WWDC 2017 Highlights: WatchOS, iOS 11, Apple Pay and more\",\n" +
                "      \"content\": \"<p>WWDC 2017, the biggest annual Apple developer conference gave us a bounty of major announcements on new products, software updates for this fall. Some of my friends including me, felt this it was Apple&#8217;s biggest WWDC opener in recent memory – perhaps ever.</p>\\n<p>From the latest versions of iOS and macOS to a new iPad, updated iMac and MacBook Pros, HomePod and what not. Here are the highlight of the new features in newly announced WatchOs, iOS 11, Apple Pay.</p>\\n<h2>WatchOS Updates</h2>\\n<p>Proactive and intelligent watch face with proactive Siri capabilities. Depending on the time of day and activities done by the user at that hour, watch screen changes to display most relevant information.</p>\\n<p><a href=\\\"http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face.jpg\\\"><img src=\\\"http://stacktips.com/wp-content/themes/Stacktips6.0/img/1x1.trans.gif\\\" data-lazy-src=\\\"http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-940x485.jpg\\\" class=\\\"aligncenter wp-image-12775 size-large\\\" alt width=\\\"900\\\" height=\\\"464\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-940x485.jpg 940w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-620x320.jpg 620w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-980x506.jpg 980w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-720x372.jpg 720w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face.jpg 1000w\\\" sizes=\\\"(max-width: 900px) 100vw, 900px\\\"><noscript><img class=\\\"aligncenter wp-image-12775 size-large\\\" src=\\\"http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-940x485.jpg\\\" alt=\\\"\\\" width=\\\"900\\\" height=\\\"464\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-940x485.jpg 940w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-620x320.jpg 620w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-980x506.jpg 980w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face-720x372.jpg 720w, http://stacktips.com/wp-content/uploads/2017/06/watchOS-4-Siri-Watch-Face.jpg 1000w\\\" sizes=\\\"(max-width: 900px) 100vw, 900px\\\" /></noscript></a></p>\\n<p>You&#8217;ll need to choose the information you want to receive from Siri based on time, location, and your routines. With the Siri watch face on, you can raise your wrist throughout the day to receive dynamic information, such as traffic information if you&#8217;re about to leave work and sunset times in the evening.</p>\\n<p>Apple also announced a couple of fun watch faces, including a &#8220;Kaleidoscope&#8221; face &#8220;Mickey&#8221; and &#8220;Minnie Mouse&#8221; faces were some of the most popular.</p>\\n<h2>What&#8217;s new in iOS 11?</h2>\\n<p>As expected, Apple revealed new iOS 11 to developers during the WWDC keynote. While it is still in beta phase, it showed a sneak peak of new features, so developers can start building their app before it is released for public.</p>\\n<p>Here are the some of the updates for iOS 11 users.</p>\\n<h3>Customizable Control Center</h3>\\n<p>One of the awesome feature of Android which iPhone users missed for years, is the ability to on/off features right from the home page.</p>\\n<p>For example, if you want to enable your mobile data, in iOS you had to open Settings app to toggle on or off. Now with the new iOS 11, they can now customize the controls on notification center.</p>\\n<p><a href=\\\"http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center.jpg\\\"><img src=\\\"http://stacktips.com/wp-content/themes/Stacktips6.0/img/1x1.trans.gif\\\" data-lazy-src=\\\"http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center.jpg\\\" class=\\\"aligncenter wp-image-12780 size-full\\\" alt=\\\"iOS11 Control Center\\\" width=\\\"800\\\" height=\\\"534\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center.jpg 800w, http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center-620x414.jpg 620w, http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center-720x481.jpg 720w\\\" sizes=\\\"(max-width: 800px) 100vw, 800px\\\"><noscript><img class=\\\"aligncenter wp-image-12780 size-full\\\" src=\\\"http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center.jpg\\\" alt=\\\"iOS11 Control Center\\\" width=\\\"800\\\" height=\\\"534\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center.jpg 800w, http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center-620x414.jpg 620w, http://stacktips.com/wp-content/uploads/2017/06/iOS11-Control-Center-720x481.jpg 720w\\\" sizes=\\\"(max-width: 800px) 100vw, 800px\\\" /></noscript></a></p>\\n<p>New iOS 11 brings brand new and customizable control center. Now can add more shortcuts to control center by visiting Settings-&gt;Control center settings.</p>\\n<p>Uninstalling of unused apps: There is a potions in settings app, which will automatically uninstall the app which haven&#8217;t been used for a while. Though the document and data for these uninstalled apps will remain in the memory in case the app is again reinstalled.</p>\\n<h3>New notification center</h3>\\n<p>The notification center screen UI is refreshed. It will now display all the current notifications for the day and all other older notification will be pushed to the bottom.</p>\\n<h3>Safari updates</h3>\\n<p>Safari browser is now faster and sleeker. Users now have an option to block the annoying Auto-Play videos in browser.</p>\\n<h3>Password autofill and sharing</h3>\\n<p>Logging into apps, as Apple notes, is a source of friction for many users. We’re supposed to use complex passwords, change them frequently and not store them in unsecured places — like an Excel spreadsheet or note, for example. But it’s also difficult to remember which passwords we need to gain access to which resources if we don’t write them down somewhere.</p>\\n<p>Your password can be shared quickly with and device running iOS 11.</p>\\n<h3>App Store</h3>\\n<p>The iOS 11 features a redesigned App Store. Now it categorizes games and apps into separate sections. In additions to games and apps section, a new tab called &#8220;Today&#8221; features to display App of the Day and a Game of the Day. Editor&#8217;s picks are also displayed Today section.</p>\\n<p>As for the individual pages of the apps, Apple has chosen to highlight reviews, placing more emphasis on Editor&#8217;s Choice for instance. A preview of the content can now be seen in multiple videos displayed on the page.</p>\\n<p>With the revamped App Store, users are now notified whenever developers launch new features for their favorite apps.</p>\\n<p><a href=\\\"http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11.png\\\"><img src=\\\"http://stacktips.com/wp-content/themes/Stacktips6.0/img/1x1.trans.gif\\\" data-lazy-src=\\\"http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11.png\\\" class=\\\"aligncenter wp-image-12795 size-full\\\" alt=\\\"New App Store iOS11\\\" width=\\\"858\\\" height=\\\"447\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11.png 858w, http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11-620x323.png 620w, http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11-720x375.png 720w\\\" sizes=\\\"(max-width: 858px) 100vw, 858px\\\"><noscript><img class=\\\"aligncenter wp-image-12795 size-full\\\" src=\\\"http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11.png\\\" alt=\\\"New App Store iOS11\\\" width=\\\"858\\\" height=\\\"447\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11.png 858w, http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11-620x323.png 620w, http://stacktips.com/wp-content/uploads/2017/06/New-App-Store-iOS11-720x375.png 720w\\\" sizes=\\\"(max-width: 858px) 100vw, 858px\\\" /></noscript></a></p>\\n<h3>Screen recording</h3>\\n<p>iOS 11 brings screen recording inbuilt with the phone. Now you can record your phone screen while doing other thing.  This is amazing specially for developers to record and report bugs.</p>\\n<h3>One Hand Keyboard</h3>\\n<p>This is by far one of my favourite updates. Specially for people do lot of typing in one had while traveling in tube, it is often very difficult with plus sized iPhone. Now with iOS 11 one handed keyboard, you can change the one handed mode to compress keyboard to side to make one handed typing.</p>\\n<h3>Activity &amp; Workout App</h3>\\n<ul>\\n<li>Monthly goals customized for user capabilities. Improved animations on achievements.</li>\\n<li>Now multiple workouts can be tracked and updates in a single session. ex: walk+cycle+swim.</li>\\n<li>Tie up with gyms to enable data exchange between recordings in Gym equipment and watch.</li>\\n</ul>\\n<h3>Apple Pay</h3>\\n<p>Introduced person to person payment. This option is available form messages app. Just swipe in from bottom and scroll through the pay app.</p>\\n<p><a href=\\\"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11.jpg\\\"><img src=\\\"http://stacktips.com/wp-content/themes/Stacktips6.0/img/1x1.trans.gif\\\" data-lazy-src=\\\"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-940x530.jpg\\\" class=\\\"aligncenter wp-image-12787 size-large\\\" alt=\\\"Apple Pay iOS11\\\" width=\\\"900\\\" height=\\\"507\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-940x530.jpg 940w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-620x350.jpg 620w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-980x553.jpg 980w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-720x406.jpg 720w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11.jpg 1142w\\\" sizes=\\\"(max-width: 900px) 100vw, 900px\\\"><noscript><img class=\\\"aligncenter wp-image-12787 size-large\\\" src=\\\"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-940x530.jpg\\\" alt=\\\"Apple Pay iOS11\\\" width=\\\"900\\\" height=\\\"507\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-940x530.jpg 940w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-620x350.jpg 620w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-980x553.jpg 980w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-720x406.jpg 720w, http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11.jpg 1142w\\\" sizes=\\\"(max-width: 900px) 100vw, 900px\\\" /></noscript></a><br />\\nThe received payment goes to your apple pay account card balance. You can either withdraw to your bank or use the credit for other purchases via Apple pay.</p>\\n<h3>Intelligent Driving Mode</h3>\\n<p>Do Not Disturb While Driving is a fantastic idea, which will understand if you’re connected to a car, and automatically activate it while driving. It blacks out your screen to prevent you seeing useless notifications while driving, and will auto-reply to texts with a message saying you’re in the car.</p>\\n<h3>File Manager App</h3>\\n<p>iPhone was very frustrating when it comes to its restricted file management as compared to its rival Android. But with iOS 11, Apple breaks the silence and added a new &#8220;File Manager&#8221; app to default system app category.</p>\\n<p>It also has the ability to browse, manage and merge all data saved by the owner across several cloud-saving platforms.</p>\\n<p><a href=\\\"http://stacktips.com/wp-content/uploads/2017/06/File-Manager-iOS11.jpg\\\"><img src=\\\"http://stacktips.com/wp-content/themes/Stacktips6.0/img/1x1.trans.gif\\\" data-lazy-src=\\\"http://stacktips.com/wp-content/uploads/2017/06/File-Manager-iOS11.jpg\\\" class=\\\"aligncenter wp-image-12804 size-full\\\" alt=\\\"File Manager iOS11\\\" width=\\\"680\\\" height=\\\"357\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/File-Manager-iOS11.jpg 680w, http://stacktips.com/wp-content/uploads/2017/06/File-Manager-iOS11-620x326.jpg 620w\\\" sizes=\\\"(max-width: 680px) 100vw, 680px\\\"><noscript><img class=\\\"aligncenter wp-image-12804 size-full\\\" src=\\\"http://stacktips.com/wp-content/uploads/2017/06/File-Manager-iOS11.jpg\\\" alt=\\\"File Manager iOS11\\\" width=\\\"680\\\" height=\\\"357\\\" srcset=\\\"http://stacktips.com/wp-content/uploads/2017/06/File-Manager-iOS11.jpg 680w, http://stacktips.com/wp-content/uploads/2017/06/File-Manager-iOS11-620x326.jpg 620w\\\" sizes=\\\"(max-width: 680px) 100vw, 680px\\\" /></noscript></a></p>\\n<p>The app will allow users to organize files — including nested folders — that are locally stored on their devices or those that are saved on their iCloud Drive, Dropbox, Box, Google Drive and more. It also has the ability to gather saved searches, tags and the like from the said file-saving platforms.</p>\\n<h2>What&#8217;s new for Developers?</h2>\\n<p>Along side above visible features, Apple’s annual developer conference added brand new APIs and features for developers to explore.</p>\\n<ul>\\n<li>Apple promised the App Store review time will be much faster.</li>\\n<li>Added ability for phased release. You can now release an update to some, measure effectiveness and server loads and then make it available to a larger group.</li>\\n<li>Updates CoreML framework. The software that powers Siri and all of Apple Machine learning capabilities.</li>\\n<li>New APIs including vision &#8211; face detection, text, barcode, object tracking capabilities are now exposed for developers</li>\\n<li>ARKit is added newly to the iOS family. This is indeed thrilling announcement for the Game developers. Apps can add virtual objects to real environments and modify its properties. E.g A virtual cup&#8217;s shadow can be played around with depending on the real object Lamp&#8217;s lighting.</li>\\n<li>Wireless debugging &#8211; No need for cables to debug anymore. Isn&#8217;t it amazing? \uD83D\uDC4B</li>\\n<li>Drag and Drop API, to move around cells with all its content(as metadata) within the app as well as between apps.</li>\\n<li>Main thread API checks for UI, which will be enabled by default. When UI related code is written in the background, Xcode throws errors.</li>\\n<li>Videos+HTTP live streaming, included their updated H.265 compression. Better compression and streaming, lesser storage space.</li>\\n</ul>\\n<h3>XCode Updates</h3>\\n<ul>\\n<li>Massive update for XCode 9. It is completely re-written from scratch in Swift.</li>\\n<li>Markdown Editors available</li>\\n<li>Warnings will be made fully visible in screen, with &#8220;FIX-IT&#8221; inline</li>\\n<li>Code structure highlighters</li>\\n<li>Faster code refactoring, edits at project level made faster, smoother. And many more updates here. I cant wait to try it out.</li>\\n</ul>\\n<h3>Source Control</h3>\\n<p>Github is now integrated right into Xcode. Github project page will also have a &#8220;Clone/Open in Xcode tab&#8221;</p>\\n<p>What you think about the new iOS 11 updates? We simply love it and can&#8217;t wait to try out.</p>\\n\",\n" +
                "      \"excerpt\": \"WWDC 2017, the biggest annual Apple developer conference gave us a bounty of major announcements on new products, software updates for this fall. Some of my friends including me, felt this it was Apple&#8217;s biggest WWDC opener in recent memory – perhaps ever. From the latest versions of iOS and macOS to a new iPad, updated iMac [&hellip;]\",\n" +
                "      \"date\": \"2017-06-12 15:17:58\",\n" +
                "      \"modified\": \"2017-06-12 15:17:58\",\n" +
                "      \"categories\": [\n" +
                "        {\n" +
                "          \"id\": 370,\n" +
                "          \"slug\": \"ios\",\n" +
                "          \"title\": \"iOS\",\n" +
                "          \"description\": \"\",\n" +
                "          \"parent\": 297,\n" +
                "          \"post_count\": 5\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 284,\n" +
                "          \"slug\": \"news\",\n" +
                "          \"title\": \"News &amp; Updates\",\n" +
                "          \"description\": \"\",\n" +
                "          \"parent\": 0,\n" +
                "          \"post_count\": 37\n" +
                "        }\n" +
                "      ],\n" +
                "      \"tags\": [\n" +
                "        {\n" +
                "          \"id\": 445,\n" +
                "          \"slug\": \"ios11\",\n" +
                "          \"title\": \"iOS11\",\n" +
                "          \"description\": \"\",\n" +
                "          \"post_count\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 446,\n" +
                "          \"slug\": \"wwdc\",\n" +
                "          \"title\": \"WWDC\",\n" +
                "          \"description\": \"\",\n" +
                "          \"post_count\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"author\": {\n" +
                "        \"id\": 12,\n" +
                "        \"slug\": \"nilanchala\",\n" +
                "        \"name\": \"Nilanchala\",\n" +
                "        \"first_name\": \"Nilanchala\",\n" +
                "        \"last_name\": \"Panigrahy\",\n" +
                "        \"nickname\": \"Neel\",\n" +
                "        \"url\": \"http://stacktips.com\",\n" +
                "        \"description\": \"A blogger, a bit of tech freak and a software developer. He is a thought leader in the fusion of design and mobile technologies. He is the author of Xamarin Mobile Application Development for Android Book (<a href=\\\"http://goo.gl/qUZ0XV\\\">goo.gl/qUZ0XV3</a>), DZone MVB and founder of <a href=\\\"http://stacktips.com/\\\">stacktips.com</a>.\"\n" +
                "      },\n" +
                "      \"comments\": [],\n" +
                "      \"attachments\": [\n" +
                "        {\n" +
                "          \"id\": 12774,\n" +
                "          \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "          \"slug\": \"apple-wwdc-2017\",\n" +
                "          \"title\": \"Apple WWDC 2017\",\n" +
                "          \"description\": \"Apple WWDC 2017\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"parent\": 12765,\n" +
                "          \"mime_type\": \"image/jpeg\",\n" +
                "          \"images\": {\n" +
                "            \"full\": {\n" +
                "              \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "              \"width\": 1095,\n" +
                "              \"height\": 630\n" +
                "            },\n" +
                "            \"thumbnail\": {\n" +
                "              \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "              \"width\": 375,\n" +
                "              \"height\": 300\n" +
                "            },\n" +
                "            \"medium\": {\n" +
                "              \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "              \"width\": 620,\n" +
                "              \"height\": 357\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 12775,\n" +
                "          \"url\": \"https://www.google.com.br/imgres?imgurl=http%3A%2F%2Fwww.alvestambelli.adv.br%2Fpublic%2Fimg%2Fdefault%2Fservicos-de-advogados%2Fhome%2Fdireito-tributario-e-precatorio.jpg&imgrefurl=http%3A%2F%2Fwww.alvestambelli.adv.br%2Fservico-de-advogado-ipanema&docid=kgQ2iAGPoTEFMM&tbnid=mKawS_6kMSrmYM%3A&vet=10ahUKEwigg-jQsaPVAhVFjZAKHc55AmMQMwgjKAIwAg..i&w=810&h=438&itg=1&bih=613&biw=1366&q=servi%C3%A7o%20advogado&ved=0ahUKEwigg-jQsaPVAhVFjZAKHc55AmMQMwgjKAIwAg&iact=mrc&uact=8\",\n" +
                "          \"slug\": \"watchos-4-siri-watch-face\",\n" +
                "          \"title\": \"watchOS-4-Siri-Watch-Face\",\n" +
                "          \"description\": \"\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"parent\": 12765,\n" +
                "          \"mime_type\": \"image/jpeg\",\n" +
                "          \"images\": {\n" +
                "            \"full\": {\n" +
                "              \"url\": \"https://www.google.com.br/imgres?imgurl=http%3A%2F%2Fwww.alvestambelli.adv.br%2Fpublic%2Fimg%2Fdefault%2Fservicos-de-advogados%2Fhome%2Fdireito-tributario-e-precatorio.jpg&imgrefurl=http%3A%2F%2Fwww.alvestambelli.adv.br%2Fservico-de-advogado-ipanema&docid=kgQ2iAGPoTEFMM&tbnid=mKawS_6kMSrmYM%3A&vet=10ahUKEwigg-jQsaPVAhVFjZAKHc55AmMQMwgjKAIwAg..i&w=810&h=438&itg=1&bih=613&biw=1366&q=servi%C3%A7o%20advogado&ved=0ahUKEwigg-jQsaPVAhVFjZAKHc55AmMQMwgjKAIwAg&iact=mrc&uact=8\",\n" +
                "              \"width\": 1000,\n" +
                "              \"height\": 516\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 12787,\n" +
                "          \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11.jpg\",\n" +
                "          \"slug\": \"apple-pay-ios11\",\n" +
                "          \"title\": \"Apple Pay iOS11\",\n" +
                "          \"description\": \"\",\n" +
                "          \"caption\": \"\",\n" +
                "          \"parent\": 12765,\n" +
                "          \"mime_type\": \"image/jpeg\",\n" +
                "          \"images\": {\n" +
                "            \"full\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11.jpg\",\n" +
                "              \"width\": 1142,\n" +
                "              \"height\": 644\n" +
                "            },\n" +
                "            \"thumbnail\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-375x300.jpg\",\n" +
                "              \"width\": 375,\n" +
                "              \"height\": 300\n" +
                "            },\n" +
                "            \"medium\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-620x350.jpg\",\n" +
                "              \"width\": 620,\n" +
                "              \"height\": 350\n" +
                "            },\n" +
                "            \"medium_large\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11.jpg\",\n" +
                "              \"width\": \"1142\",\n" +
                "              \"height\": \"644\"\n" +
                "            },\n" +
                "            \"xlarge\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-980x553.jpg\",\n" +
                "              \"width\": 980,\n" +
                "              \"height\": 553\n" +
                "            },\n" +
                "            \"featured\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-720x406.jpg\",\n" +
                "              \"width\": 720,\n" +
                "              \"height\": 406\n" +
                "            },\n" +
                "            \"feed\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-85x85.jpg\",\n" +
                "              \"width\": 85,\n" +
                "              \"height\": 85\n" +
                "            },\n" +
                "            \"small\": {\n" +
                "              \"url\": \"http://stacktips.com/wp-content/uploads/2017/06/Apple-Pay-iOS11-145x100.jpg\",\n" +
                "              \"width\": 145,\n" +
                "              \"height\": 100\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        result = "{\n" +
                "  \"status\": \"ok\",\n" +
                "  \"count\": 30,\n" +
                "  \"pages\": 2,\n" +
                "  \"category\": {\n" +
                "    \"id\": 284,\n" +
                "    \"slug\": \"news\",\n" +
                "    \"title\": \"News &amp; Updates\",\n" +
                "    \"description\": \"\",\n" +
                "    \"parent\": 0,\n" +
                "    \"post_count\": 37\n" +
                "  },\n" +
                "  \"posts\": [\n" +
                "    {\n" +
                "      \"title\": \"WWDC 2017 Highlights: WatchOS, iOS 11, Apple Pay and more\",\n" +
                "      \"mime_type\": \"image/jpeg\",\n" +
                "      \"images\": {\n" +
                "        \"thumbnail\": {\n" +
                "          \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "          \"width\": 375,\n" +
                "          \"height\": 300\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"WWDC 2017 Highlights: WatchOS, iOS 11, Apple Pay and more\",\n" +
                "      \"mime_type\": \"image/jpeg\",\n" +
                "      \"images\": {\n" +
                "        \"thumbnail\": {\n" +
                "          \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "          \"width\": 375,\n" +
                "          \"height\": 300\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"WWDC 2017 Highlights: WatchOS, iOS 11, Apple Pay and more\",\n" +
                "      \"mime_type\": \"image/jpeg\",\n" +
                "      \"images\": {\n" +
                "        \"thumbnail\": {\n" +
                "          \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "          \"width\": 375,\n" +
                "          \"height\": 300\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"WWDC 2017 Highlights: WatchOS, iOS 11, Apple Pay and more\",\n" +
                "      \"mime_type\": \"image/jpeg\",\n" +
                "      \"images\": {\n" +
                "        \"thumbnail\": {\n" +
                "          \"url\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWHyQdjdIVUC2d8JnZBQmc2G1Nsi8HY9u7o2sOG6UFuy3bcQW9\",\n" +
                "          \"width\": 375,\n" +
                "          \"height\": 300\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listagem, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
