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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andrewgiang.textspritzer.lib.Spritzer;
import com.andrewgiang.textspritzer.lib.SpritzerTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends Activity {
    private SpritzerTextView mSpritzerTextView;
    private SeekBar mSeekBarWpm;
    private ProgressBar mProgressBar;
    private ParcelFileDescriptor file;
    private DownloadManager dm;
    private long enqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            JSONObject obj = new JSONObject(json);

            System.out.print(json);
            JSONObject article = obj.getJSONObject("article");
            JSONArray array = obj.getJSONArray("article");
            //Log.d("array", array.toString());
            for(int i = 0; i < array.length(); i++) {
                JSONObject single = array.getJSONObject(i);

                System.out.println(single.toString());
            }
            JSONObject article1 = obj.getJSONObject("article");
            System.out.println(article1.toString());
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
        final String words = "The series continues with Harry Potter and the Chamber of Secrets describing Harry's second year at Hogwarts. " +
                "He and his friends investigate a 50-year-old mystery that appears tied to recent sinister events at the school. Ron's younger " +
                "sister, Ginny Weasley, enrolls in her first year at Hogwarts, and finds an old notebook which turns out to be Voldemort's diary" +
                " from his school days. Ginny becomes possessed by Voldemort through the diary and unconsciously opens the \"Chamber of Secrets,\"" +
                " unleashing an ancient monster which begins attacking students at Hogwarts. The novel delves into the history of Hogwarts and a legend" +
                " revolving around the Chamber that soon frightened everyone in the school. The book also introduces a new Defence Against the Dark Arts" +
                " teacher, Gilderoy Lockhart, a highly cheerful, self-conceited know-it-all who later turns out to be a fraud. For the first time, Harry" +
                " realises that racial prejudice exists in the wizarding world even before, and he learns that Voldemort's reign of terror was often" +
                " directed at wizards who were descended from Muggles. Harry also learns that his ability to speak Parseltongue, the language of snakes," +
                " is rare and often associated with the Dark Arts. The novel ends after Harry saves Ginny's life by destroying a basilisk and the enchanted" +
                " diary which has been the source of the problems.";

        mSpritzerTextView = (SpritzerTextView)

                findViewById(R.id.spritzTV);

        mSpritzerTextView.setSpritzText(words);

        mProgressBar = (ProgressBar)

                findViewById(R.id.spritz_progress);

        mSpritzerTextView.attachProgressBar(mProgressBar);

        final String[] wordsArray = words.split(" ");
        mProgressBar.setMax(wordsArray.length);
        final ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener()

                                {
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

        fastforward.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               mSpritzerTextView.pause();
                                               play.setImageResource(R.drawable.playbutton);
                                               play.setTag("start");

                                               int pos = mSpritzerTextView.getCurrentWordIndex();
                                               System.out.println(pos);

                                               ArrayList<Integer> indexes = new ArrayList<Integer>();
                                               int newStart = 0;
                                               for (int i = 0; i < wordsArray.length; i++) {
                                                   if (wordsArray[i].endsWith(".")) {
                                                       indexes.add(i);
                                                   }
                                               }
                                               for (int i = 0; i < indexes.size(); i++) {
                                                   if (pos <= indexes.get(i)) {
                                                       if (i + 1 < indexes.size()) {
                                                           newStart = indexes.get(i + 1);
                                                       }
                                                   }
                                               }
                                               System.out.println(newStart);
                                               mSpritzerTextView.setSpritzText(words.substring(newStart));
                                               System.out.println(indexes);
                                           }
                                       }

        );
        rewind.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          mSpritzerTextView.pause();
                                          play.setImageResource(R.drawable.playbutton);
                                          play.setTag("start");

                                          int pos = mSpritzerTextView.getCurrentWordIndex();
                                          System.out.println(pos);
                                          ArrayList<Integer> indexes = new ArrayList<Integer>();
                                          int newStart = 0;
                                          for (int i = 0; i < wordsArray.length; i++) {
                                              if (wordsArray[i].endsWith(".")) {
                                                  indexes.add(i);
                                              }
                                          }
                                          for (int i = 0; i < indexes.size(); i++) {
                                              if (pos <= indexes.get(i)) {
                                                  if (i != 0) {
                                                      newStart = indexes.get(i - 1);
                                                  }
                                              }
                                          }
                                          mSpritzerTextView.setSpritzText(words.substring(newStart));
                                          System.out.println(indexes);
                                      }
                                  }

        );

        mSpritzerTextView.setOnCompletionListener(new Spritzer.OnCompletionListener()

                                                  {
                                                      @Override
                                                      public void onComplete() {
                                                          mSpritzerTextView.pause();
                                                          play.setImageResource(R.drawable.playbutton);
                                                          play.setTag("start");
                                                      }
                                                  }

        );
        mSeekBarWpm = (SeekBar)

                findViewById(R.id.seekBarWpm);

        mSeekBarWpm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

                                               {
                                                   int progressChanged = 0;

                                                   @Override
                                                   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                       progressChanged = progress;
                                                       TextView wpm = (TextView) findViewById(R.id.textView2);
                                                       wpm.setText(mSeekBarWpm.getProgress() * 10 + " WPM");
                                                       mSpritzerTextView.setWpm(mSeekBarWpm.getProgress() * 10);
                                                   }

                                                   @Override
                                                   public void onStartTrackingTouch(SeekBar seekBar) {

                                                   }

                                                   @Override
                                                   public void onStopTrackingTouch(SeekBar seekBar) {

                                                   }
                                               }

        );
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

    private String readJSONFile(InputStream fis)
            throws IOException {
        int size = fis.available();
        byte[] buffer = new byte[size];
        fis.read(buffer);

        return new String(buffer, "UTF-8");
    }


    private String readJSONFile(FileInputStream fis)
            throws IOException {
        int size = fis.available();
        byte[] buffer = new byte[size];
        fis.read(buffer);

        return new String(buffer, "UTF-8");
    }

    public void writeToFile(String data) {
        try {
            Log.i("article", "writing download to file");

            File file = new File(getFilesDir().getAbsolutePath(), "articles2.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

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
}
