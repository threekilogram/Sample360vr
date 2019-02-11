package tech.threekilogram.droidvr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.google.vr.sdk.widgets.pano.VrPanoramaView.Options;
import com.threekilogram.bitmapreader.BitmapReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

      private static final String TAG = MainActivity.class.getSimpleName();

      private ImageView      mMineImage;
      private TextView       mTitle;
      private VrPanoramaView mVrPanoramaView;
      private Options        mOptions;
      private RecyclerView   mRecycler;

      private List<Model> mModels = new ArrayList<>();

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );

            initModel();
            initView();
      }

      private void initModel ( ) {

            mModels.add( new Model( "item-0", "beijing_gugong.jpg" ) );
            mModels.add( new Model( "item-1", "dibaita.jpg" ) );
            mModels.add( new Model( "item-2", "france.jpg" ) );
            mModels.add( new Model( "item-3", "guangzhou.jpg" ) );
            mModels.add( new Model( "item-4", "haidishijie.jpg" ) );
            mModels.add( new Model( "item-5", "jiguang.jpg" ) );
            mModels.add( new Model( "item-6", "maldives.jpg" ) );
            mModels.add( new Model( "item-7", "niagara_falls.jpg" ) );
            mModels.add( new Model( "item-8", "residence.jpg" ) );
            mModels.add( new Model( "item-9", "santorini.jpg" ) );
      }

      private void initView ( ) {

            mTitle = findViewById( R.id.title );
            mMineImage = findViewById( R.id.mineImage );
            mVrPanoramaView = findViewById( R.id.vrPanoramaView );
            initVrView();

            mRecycler = findViewById( R.id.recycler );
            mRecycler.setLayoutManager( new LinearLayoutManager( this ) );
            mRecycler.setAdapter( new Adapter() );
      }

      private void initVrView ( ) {

            mVrPanoramaView.setTouchTrackingEnabled( true );
            mVrPanoramaView.setFullscreenButtonEnabled( true );
            mVrPanoramaView.setInfoButtonEnabled( false );
            mVrPanoramaView.setStereoModeButtonEnabled( false );
            mVrPanoramaView.setEventListener( new VrEventListener() );
            mOptions = new Options();
            mOptions.inputType = Options.TYPE_MONO;

            try {
                  InputStream stream = getAssets().open( "beijing_gugong.jpg" );
                  Bitmap bitmap = BitmapFactory.decodeStream( stream );
                  mVrPanoramaView.loadImageFromBitmap( bitmap, mOptions );
            } catch(IOException e) {
                  e.printStackTrace();
            }
      }

      @Override
      protected void onResume ( ) {

            super.onResume();
            mVrPanoramaView.resumeRendering();
      }

      @Override
      protected void onPause ( ) {

            super.onPause();
            mVrPanoramaView.pauseRendering();
      }

      @Override
      protected void onDestroy ( ) {

            mVrPanoramaView.shutdown();
            super.onDestroy();
      }

      class VrEventListener extends VrPanoramaEventListener {

            @Override
            public void onClick ( ) {

                  Log.i( TAG, "onClick: " );
            }
      }

      class Adapter extends RecyclerView.Adapter<Holder> {

            @NonNull
            @Override
            public Holder onCreateViewHolder ( @NonNull ViewGroup viewGroup, int i ) {

                  View view = LayoutInflater.from( MainActivity.this )
                                            .inflate( R.layout.item, viewGroup, false );
                  return new Holder( view );
            }

            @Override
            public void onBindViewHolder ( @NonNull Holder holder, int i ) {

                  holder.bind( mModels.get( i ) );
            }

            @Override
            public int getItemCount ( ) {

                  return mModels.size();
            }
      }

      class Holder extends RecyclerView.ViewHolder {

            private final float  mSize;
            private       String mName;

            Holder ( @NonNull View itemView ) {

                  super( itemView );
                  mSize = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 100,
                                                     MainActivity.this.getResources()
                                                                      .getDisplayMetrics()
                  );

                  itemView.setOnClickListener( new ClickListener() );
            }

            void bind ( Model model ) {

                  ( (TextView) itemView.findViewById( R.id.title ) ).setText( model.mTitle );
                  mName = model.mRes;
                  ( (ImageView) itemView.findViewById( R.id.res ) )
                      .setImageBitmap( decodeAssert( mName ) );
            }

            Bitmap decodeAssert ( String name ) {

                  try {
                        InputStream stream = getAssets().open( name );
                        Bitmap bitmap = BitmapReader.matchWidth( stream, (int) mSize );
                        return bitmap;
                  } catch(IOException e) {
                        e.printStackTrace();
                  }

                  return null;
            }

            class ClickListener implements OnClickListener {

                  @Override
                  public void onClick ( View v ) {

                        try {
                              InputStream stream = getAssets().open( mName );
                              Bitmap bitmap = BitmapFactory.decodeStream( stream );
                              mVrPanoramaView.loadImageFromBitmap( bitmap, mOptions );
                        } catch(IOException e) {
                              e.printStackTrace();
                        }
                  }
            }
      }
}
