package com.example.coffebean;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class suggestBeverage extends AppCompatActivity
{
    //Request code for gallery.
    int GALLERY_REQUEST = 100;

    /*
    * References of the below class for retrieving images from gallery and saving them in database:
    * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    * Video: https://www.youtube.com/watch?v=ykbU41xhDrY
    * Website: https://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android
    * Website: https://medium.com/@patelsneh18/startactvivityforresult-deprecated-alternative-and-using-it-outside-activity-class-bc9331cf896
    *
    *
    * suggestBeverage Activity
    * ^^^^^^^^^^^^^^^^^^^^^^^^
    * In this activity, the user is able to suggest a beverage of his/her choice that is currently not on the menu. The idea behind this is to make the experience feel more realistic
    * amd more dynamic. There are some data that needs to be filled regarding teh drink's information and also the user is able to upload an image.
    * */

    //Creating the required objects for this activity.
    ImageView backArrowBtn;
    EditText suggestionName, suggestionDesc;
    Button suggestionImgBtn, suggestionBtn;
    ImageView suggestionImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_beverage);

        //Redirecting the imageview that represents the back arrow.
        backArrowBtn = (ImageView) findViewById(R.id.previousArrowBtn);
        //handles the back arrow(May make it more efficient in the future)
        backArrowBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Creating a new intent instance, with context of this page and the page that the intent will redirect to.
                Intent intent = new Intent(suggestBeverage.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        //Redirecting the button 'Image' using findVieById().
        suggestionImgBtn = (Button) findViewById(R.id.suggestionImgBtn);
        //OnLickListener handles what happens when the 'Image' button is pressed.
        suggestionImgBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openGallery(view);
            }
        });

        //Redirecting the button 'Suggest'  using findVieById().
        suggestionBtn = (Button) findViewById(R.id.suggestionBtn);
        //OnLickListener handles what happens when the 'Suggest' button is pressed.
        suggestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Redirecting the above created objects, to the instances that are present on screen using the findViewById().
                suggestionImageView = (ImageView) findViewById(R.id.suggestionImageView);
                suggestionName = (EditText) findViewById(R.id.suggestionName);
                suggestionDesc = (EditText) findViewById(R.id.suggestionDesc);

                //Validation
                if(suggestionName.getText().toString().equals("") || suggestionDesc.getText().toString().equals("") || suggestionImageView.getDrawable() == null)
                {
                    //Displaying a message that indicates that something is wrong and restricting the user from adding the suggestion to the table.
                    Toast.makeText(suggestBeverage.this, "Make sure that all fields have been filled !!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Saving the suggestion to teh database.
                    save(view);
                    //Displaying successful message.
                    Toast.makeText(suggestBeverage.this, "Suggestion has been received, thanks !!", Toast.LENGTH_SHORT).show();

                    //Clear all the fields that have been filled.
                    suggestionName.setText("");
                    suggestionDesc.setText("");
                    suggestionImageView.setImageBitmap(null);
                }
            }
        });
    }

    //This method will launch the gallery request.
    public void openGallery(View view)
    {
        //Creating a new intent instance, with the action to get some content.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //Setting the type of content that the intent is going to get to anything that has to do with images.
        intent.setType("image/*");
        //Getting data passed through the intent.putExtra() method.
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        getResult.launch(intent);
    }

    ActivityResultLauncher<Intent> getResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
    {
        @Override
        public void onActivityResult(ActivityResult result)
        {
            //Validation.
            if (result.getResultCode() == Activity.RESULT_OK)
            {
                //Getting the imageView by using findViewById().
                suggestionImageView = (ImageView) findViewById(R.id.suggestionImageView);

                //Creating a new Intent instance.
                Intent intent = result.getData();
                //Creating URI(Uniform resource identifier) instance and setting its value to the intent that contains the ActivityResult objet 'result' data.
                Uri uri = intent.getData();
                //Setting the image's uri to the uri created above in order to display the selected image from the gallery to the screen.
                suggestionImageView.setImageURI(uri);
            }
        }
    });

    //Saving the information inserted through this activity to the database.
    public void save(View view)
    {
        //Creating a new instance of the database.
        DataBaseOpenHelper db = new DataBaseOpenHelper(view.getContext());
        //Redirecting the above created objects, to the instances that are present on screen using the findViewById().
        suggestionName = (EditText) findViewById(R.id.suggestionName);
        suggestionDesc = (EditText) findViewById(R.id.suggestionDesc);

        //Storing the name inserted through the EditText in a String variable.
        String name = suggestionName.getText().toString();
        //Storing the description inserted through the EditText in a String variable.
        String description = suggestionDesc.getText().toString();
        //Storing the image inserted through the ImageView in a Bitmap variable.
        Bitmap image = ((BitmapDrawable)suggestionImageView.getDrawable()).getBitmap();

        //Passing all the above information to the database and adding this suggestion to teh database
        db.addSuggestion(name, description, image);
    }
}