package polyhack2018.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.DisplayMetrics;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.database.Cursor;
import android.provider.MediaStore;
import android.app.Activity.*;


public class MainActivity extends AppCompatActivity {

    TextView text;
    TextView textAfterSubmit;
    TextView name;
    TextView phone;
    // the submit button
    Button button;
    // the "click to submit addl contacts" button
    Button anotherButton;
    Boolean clicked;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        */


        clicked = false;
        text = (TextView)findViewById(R.id.textbox);
        textAfterSubmit = (TextView)findViewById(R.id.text_submitted);
        name = (TextView)findViewById(R.id.name_input);
        name.setHint("Enter name");
        phone = (TextView)findViewById(R.id.phone_input);
        button = (Button)findViewById(R.id.submit_button);
        anotherButton = (Button)findViewById(R.id.submit_another_button);

    }

    public void buttonClicked(View view) {

        // let user know what was sumbitted
        String nameInput = name.getText().toString();
        textAfterSubmit.setText(nameInput + " was submitted");

        submitData();

        // replace submit button with text and show submit_another button
        textAfterSubmit.setVisibility(View.VISIBLE);
        button.setVisibility(View.INVISIBLE);
        anotherButton.setVisibility(View.VISIBLE);

        /*
        if(clicked){
            text.setText("Enter a new contact below");
            clicked = false;
        }else{
            text.setText("Contact submitted");
            clicked = true;
        }
        */
        // send to firebase


    }

    public void submitData() {

        // store data to eventually put in firebase
        String nameInput = name.getText().toString();
        String phoneInput = name.getText().toString();

        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        */
    }

    public void anotherButtonClicked(View view){

        // restart interactions, reset text fields
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    private void dispatchTakePictureIntent() {
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void camClicked(View view){
        dispatchTakePictureIntent();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            cursor.close();
        }
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
}
