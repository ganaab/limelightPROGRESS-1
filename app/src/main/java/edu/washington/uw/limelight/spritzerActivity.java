package edu.washington.uw.limelight;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andrewgiang.textspritzer.lib.Spritzer;
import com.andrewgiang.textspritzer.lib.SpritzerTextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class spritzerActivity extends Activity {
    private SpritzerTextView mSpritzerTextView;
    private SeekBar mSeekBarWpm;
    private ProgressBar mProgressBar;
    private ParcelFileDescriptor file;
    private DownloadManager dm;
    private long enqueue;
    private int counter;
    private Map<String, String> x = new HashMap<>();
    private ArrayList<String> ycuz = new ArrayList<>();
    private String words;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spritzer);
        // file download code
        download();

        //Register Receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        //registerReceiver(receiver, filter);

        File filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(filePath, "Articles.json");

        //System.out.println(file.toString());


//        if (file.exists()) {
//            Log.i("article", "article.json does exist!");
//            try {
//                FileInputStream fis = openFileInput(file);
//                json = readJSONFile(fis);
//                Log.i("whatitis", "JSON String: " + json);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Log.i("article", "article.json does not exist!");
//            try {
//                InputStream is = getAssets().open("article2.json");
//                json = readJSONFile(is);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
        String json = null;
        try {
            InputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            Log.d("sfds", json);
            System.out.println(json);
//            JSONObject obj = new JSONObject(json);

            System.out.println("breaker");
            String[] titles = json.split(" \"title\":\"");
            System.out.println(titles[0]);
            System.out.println("breaker");
            System.out.println(titles[1]);
            System.out.println("breaker");
            System.out.println(titles[2]);
            System.out.println("breaker");
            System.out.println(titles[3]);
            for (int i = 1; i < titles.length; i++) {
                String[] gettingArticle = titles[i].split("\"article\":\"");
//                System.out.println("breaker2");
//                System.out.println(gettingArticle[0].substring(0,gettingArticle[0].length() - 6));
//                System.out.println("breaker3");
//                System.out.println(gettingArticle[1].substring(0,gettingArticle[1].length() - 8));

                x.put(gettingArticle[0].substring(0, gettingArticle[0].length()), gettingArticle[1].substring(0, gettingArticle[1].length() - 6));
                ycuz.add(gettingArticle[0].substring(0, gettingArticle[0].length()));
            }

//            JSONObject article = obj.getJSONObject("article");
//            JSONArray array = obj.getJSONArray("article");
//            //Log.d("array", array.toString());
//            for(int i = 0; i < array.length(); i++) {
//                JSONObject single = array.getJSONObject(i);
//
//                System.out.println(single.toString());
//            }
//            JSONObject article1 = obj.getJSONObject("article");
//            System.out.println(article1.toString());
//            JSONObject math = obj.getJSONObject("Math");
//            Topic mathematics = loadQuiz(math);
//            JSONObject physics = obj.getJSONObject("Physics");
//            Topic physicsTopic = loadQuiz(physics);
//            JSONObject marvel = obj.getJSONObject("Marvel Super Heroes");
//            Topic marvelTopic = loadQuiz(marvel);
//            topics.add(mathematics);
//            topics.add(physicsTopic);
//            topics.add(marvelTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final TextView headline = (TextView) findViewById(R.id.headline);
        String input = ycuz.get(counter);
        headline.setText(input);
        words = x.get(headline.getText().toString());
        System.out.println(x.size());
        headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter >= x.size()-1) {
                    counter = 0;
                } else {
                    counter++;
                }
                String input = ycuz.get(counter);
                headline.setText(input);
                words = x.get(headline.getText().toString());

                mSpritzerTextView.setSpritzText(words);
            }
        });
//        JSONArray jsonMainArr = new JSONArray(mainJSON.getJSONArray("source"));
//        for (int i = 0; i < jsonMainArr.length(); i++) {  // **line 2**
//            JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
//            String name = childJSONObject.getString("name");
//        }
//        try {
//            JSONObject obj = new JSONObject(json);
//            JSONArray array = obj.getJSONArray("stories");
//
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject something = array.getJSONObject(i);
//                //System.out.println(something.toString());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        // Spritzing

//        final String words = "The series continues with Harry Potter and the Chamber of Secrets describing Harry's second year at Hogwarts. " +
//                "He and his friends investigate a 50-year-old mystery that appears tied to recent sinister events at the school. Ron's younger " +
//                "sister, Ginny Weasley, enrolls in her first year at Hogwarts, and finds an old notebook which turns out to be Voldemort's diary" +
//                " from his school days. Ginny becomes possessed by Voldemort through the diary and unconsciously opens the \"Chamber of Secrets,\"" +
//                " unleashing an ancient monster which begins attacking students at Hogwarts. The novel delves into the history of Hogwarts and a legend" +
//                " revolving around the Chamber that soon frightened everyone in the school. The book also introduces a new Defence Against the Dark Arts" +
//                " teacher, Gilderoy Lockhart, a highly cheerful, self-conceited know-it-all who later turns out to be a fraud. For the first time, Harry" +
//                " realises that racial prejudice exists in the wizarding world even before, and he learns that Voldemort's reign of terror was often" +
//                " directed at wizards who were descended from Muggles. Harry also learns that his ability to speak Parseltongue, the language of snakes," +
//                " is rare and often associated with the Dark Arts. The novel ends after Harry saves Ginny's life by destroying a basilisk and the enchanted" +
//                " diary which has been the source of the problems.";

        mSpritzerTextView = (SpritzerTextView)

                findViewById(R.id.spritzTV);

        mSpritzerTextView.setSpritzText(words);

        mProgressBar = (ProgressBar)

                findViewById(R.id.spritz_progress);

        mSpritzerTextView.attachProgressBar(mProgressBar);

        final String[] wordsArray = words.split(" ");
        final char[] charArray = words.toCharArray();
        mProgressBar.setMax(wordsArray.length);
        final ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ImageView play = (ImageView) findViewById(R.id.play);
                                        if (play.getTag() == null || play.getTag().equals("start")) {
                                            mSpritzerTextView.play();
                                            play.setImageResource(R.drawable.pausebutton);
                                            play.setTag("stop");
                                        } else {
                                            mSpritzerTextView.pause();
                                            play.setImageResource(R.drawable.playbutton);
                                            play.setTag("start");
                                        }
                                    }
                                }

        );

        ImageView fastforward = (ImageView) findViewById(R.id.forward);
        ImageView rewind = (ImageView) findViewById(R.id.rewind);

        fastforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpritzerTextView.pause();
                play.setImageResource(R.drawable.playbutton);
                play.setTag("start");

//                int pos = mSpritzerTextView.getCurrentWordIndex();
//                System.out.println("current position: " + pos);
//                System.out.println(charArray.toString());
//                ArrayList<Integer> indexes = new ArrayList<Integer>();
//                int newStart = 0;
//                for (int i = 0; i < charArray.length; i++) {
//                    if (charArray[i] == '.') {
//                        indexes.add(i);
//                    }
//                }// find the indexes with words ending with a .
//                for (int i = 0; i < indexes.size(); i++) {
//                    if (pos <= indexes.get(i)) {
//                        if (i + 1 < indexes.size()) {
//                            newStart = indexes.get(i + 1);
//                            break;
//                        }
//                    }
//                }
//
//                words = x.get(headline.getText().toString());
//                System.out.println("newstart: "+newStart);
//                System.out.println(words.substring(newStart));
//                mSpritzerTextView = (SpritzerTextView) findViewById(R.id.spritzTV);
//                String nextString = words.substring(newStart);
//                mSpritzerTextView.setSpritzText(nextString);
//                System.out.println(indexes);

                if (counter >= x.size()-1) {
                    counter = 0;
                } else {
                    counter++;
                }
                String input = ycuz.get(counter);
                headline.setText(input);
                words = x.get(headline.getText().toString());

                mSpritzerTextView.setSpritzText(words);
            }
        });
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpritzerTextView.pause();
                play.setImageResource(R.drawable.playbutton);
                play.setTag("start");

//                int pos = mSpritzerTextView.getCurrentWordIndex();
//                System.out.println("current position: " + pos);
//                ArrayList<Integer> indexes = new ArrayList<Integer>();
//                int newStart = 0;
//                for (int i = 0; i < wordsArray.length; i++) {
//                    if (wordsArray[i].endsWith(".")) {
//                        indexes.add(i);
//                    }
//                }// find the indexes with words ending with a .
//                for (int i = 0; i < indexes.size(); i++) {
//                    if (pos <= indexes.get(i)) {
//                        if (i != 0) {
//                            newStart = indexes.get(i - 1);
//                            break;
//                        }
//                    }
//                }
//                mSpritzerTextView = (SpritzerTextView) findViewById(R.id.spritzTV);
//                mSpritzerTextView.setSpritzText(words.substring(newStart-1));
//                System.out.println(indexes);

                String input = ycuz.get(counter);
                headline.setText(input);
                words = x.get(headline.getText().toString());

                mSpritzerTextView.setSpritzText(words);
            }
        });

        mSpritzerTextView.setOnCompletionListener(new Spritzer.OnCompletionListener() {
            @Override
            public void onComplete() {
                mSpritzerTextView.pause();
                play.setImageResource(R.drawable.playbutton);
                play.setTag("start");
            }
        });

        mSeekBarWpm = (SeekBar) findViewById(R.id.seekBarWpm);

        mSeekBarWpm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                TextView wpm = (TextView) findViewById(R.id.textView2);
                wpm.setText(mSeekBarWpm.getProgress() * 1 + 1 + " WPM");
                mSpritzerTextView.setWpm(mSeekBarWpm.getProgress() * 1 + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void download() {
        String url = "http://limelight.site44.com/article.json";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("File download");
        request.setDescription("File is being downloaded...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String nameOfFile = "Articles.json";

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);
        System.out.print(Environment.DIRECTORY_DOWNLOADS);
        DownloadManager manager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

//    private String readJSONFile(InputStream fis)
//            throws IOException {
//        int size = fis.available();
//        byte[] buffer = new byte[size];
//        fis.read(buffer);
//
//        return new String(buffer, "UTF-8");
//    }
//
//
//    private String readJSONFile(FileInputStream fis)
//            throws IOException {
//        int size = fis.available();
//        byte[] buffer = new byte[size];
//        fis.read(buffer);
//
//        return new String(buffer, "UTF-8");
//    }

//    public void writeToFile(String data) {
//        try {
//            Log.i("article", "writing download to file");
//
//            File file = new File(getFilesDir().getAbsolutePath(), "articles2.json");
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(data.getBytes());
//            fos.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  unregisterReceiver(receiver);
    }

  /*  private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

            Log.i("MainActivity", "onReceive of registered download reciever");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i("MainActivity", "download complete!");
                long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);


                // if the downloadID exists
                if (downloadID != 0) {

                    // Check status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.d("DM Sample", "Status Check: " + status);
                        switch (status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                ParcelFileDescriptor file;
                                StringBuffer strContent = new StringBuffer("");

                                try {
                                    // Get file from Download Manager
                                    file = dm.openDownloadedFile(downloadID);
                                    FileInputStream fis = new FileInputStream(file.getFileDescriptor());

                                    // convert file to String
                                    String jsonString = readJSONFile(fis);

                                    // write string to quizData.json
                                    writeToFile(jsonString);
                                    Log.i("JSON downloaded", "String: " + jsonString);

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DownloadManager.STATUS_FAILED:
                                break;
                        }
                    }
                }
            }
        }
    };*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spritzer, menu);
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
}
