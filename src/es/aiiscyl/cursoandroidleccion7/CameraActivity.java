package es.aiiscyl.cursoandroidleccion7;

import java.io.ByteArrayOutputStream;

import es.aiiscyl.cursoandroidleccion7.CheckResult;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

	private final static String DEBUG_TAG = "CameraActivity";

	// To capture an image
	private SurfaceView mSurfaceView;
	private Camera mCamera;
	private SurfaceHolder mHolder;
	private Camera.Parameters  mParameters;

	private CheckResult mCheckResult;

	// Buttons
	private ImageButton mToogleGrid;
	private ImageButton mTakePhotoB;
	private ImageButton mReturnB;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Delete title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Delete notify bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Log.d(DEBUG_TAG, "Orientation = " + String.valueOf(this.getResources().getConfiguration().orientation));
		setContentView(R.layout.activity_camera);

		// Data
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			mCheckResult = extras.getParcelable("checkResult");			
		}
		
		initControls();
	}
	
	@SuppressWarnings("deprecation")
	private void initControls()
	{

		// initialize the SurfaceView
		mSurfaceView = (SurfaceView) findViewById(R.id.cameraSurfaceView);

		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(this);
		// deprecated, but it is necessary for versions pre 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


		// Controls : Buttons
		// Toogle Grid button
		mToogleGrid = (ImageButton) findViewById(R.id.toogleGridB);
		mToogleGrid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Toogle Grid: TODO",Toast.LENGTH_SHORT).show();
			}
		});

		// Take Photo button
		mTakePhotoB = (ImageButton) findViewById(R.id.takePictureB);
		mTakePhotoB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Take photo
				mCamera.takePicture(null, null, jpegCallBack);

			}
		});

		// Return button
		mReturnB = (ImageButton) findViewById(R.id.returnB);
		mReturnB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {        
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("static-access")
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// initialize the camera
		mCamera = Camera.open();

		// parameters : set picture size
		mParameters = mCamera.getParameters();

		// flash off
		if (mCheckResult.getFlashMode() != null)
		{
			mParameters.setFlashMode(mParameters.FLASH_MODE_OFF);
		}

		mCamera.setParameters(mParameters);

		// start the preview on surface
		previewCamera();

	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Release the camera resources
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	public void previewCamera()
	{        
		try 
		{           
			mCamera.setPreviewDisplay(mHolder);    
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();
		}
		catch(Exception e)
		{
			Log.d(DEBUG_TAG, "Cannot start preview", e);    
		}
	}
	PictureCallback jpegCallBack = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

			Log.d(DEBUG_TAG, "ImageDataSize= " +  String.valueOf(data.length));	

			// Save the image in memory: resize if it is necessary (Solve BUG APPLICARTE-264)
			// to compress the image for send to PreviewActivity 
			Bitmap imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			Log.d(DEBUG_TAG, "Bitmap size pre-resize = " + String.valueOf(data.length) + 
					" height= " + imageBitmap.getHeight() + " width= "+ imageBitmap.getWidth());

			if (!(mCheckResult.isSupportedSize()) && (mCheckResult.isResize()))
			{
				imageBitmap = Bitmap.createScaledBitmap(imageBitmap, mCheckResult.getWidthResize(), mCheckResult.getHeightResize(), false);
				// size --> log
				Log.d("BitmapUtils Debug", "Bitmap size resize = " + 
						String.valueOf(imageBitmap.getRowBytes() * imageBitmap.getHeight()) + 
						" height= " + imageBitmap.getHeight() + " width= "+ imageBitmap.getWidth());		
			}

			// Show the image in Preview Screen
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			// size --> log
			byte[] imageInByte = bos.toByteArray();
			Log.d(DEBUG_TAG, "Bitmap size  = " + String.valueOf(imageInByte.length) + 
					" height= " + imageBitmap.getHeight() + " width= "+ imageBitmap.getWidth());


			// To take more photos in the future
			previewCamera();

		}
	};
}
