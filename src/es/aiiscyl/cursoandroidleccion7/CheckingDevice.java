package es.aiiscyl.cursoandroidleccion7;

import java.util.List;

import es.aiiscyl.cursoandroidleccion7.CheckResult;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.provider.Settings.Secure;
import android.util.Log;

public class CheckingDevice {

	private final static String DEBUG_TAG = "CheckingDevice";
	//
	private CheckResult mCheckResult;
	private Context mContext;

	public CheckResult getCheckResult() {
		return mCheckResult;
	}

	public void setCheckResult(CheckResult mCheckResult) {
		this.mCheckResult = mCheckResult;
	}

	public CheckingDevice(Context context) {
		super();
		this.mContext = context;
		mCheckResult = checkDevice(mContext);
	}


	/**
	 * Check if the device complains the requirements of ApplicArte
	 * @return CheckResult: contains data result: 
	 */
	public CheckResult checkDevice(Context context)
	{

		CheckResult checkResult = new CheckResult();
		Camera camera = null;

		// Device ID
		checkResult.setDeviceId(Secure.getString(context.getContentResolver(), Secure.ANDROID_ID));

		// Camera
		// do we have a camera?
		if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) 
		{
			checkResult.setCameraAvailable(false);
			checkResult.setResult(false);
		} 
		else 
		{
			camera = Camera.open();
			Log.d(DEBUG_TAG, "Camera is opened");
			checkResult.setCameraAvailable(true);

			if (camera != null) // Check Camera to avoid a NULL Exception
			{
				// Camera Parameters
				Camera.Parameters parameters = camera.getParameters();
				// Supported Sizes
				Log.d(DEBUG_TAG, "Check Supported Sizes");
				List<Camera.Size> picSizes = parameters.getSupportedPictureSizes();
				for (int i=0; i<picSizes.size(); i++)
				{
					Log.d(DEBUG_TAG, "Resolucion " + i + " " + picSizes.get(i).width + " x " +	picSizes.get(i).height);

					checkResult.setSupportedSize(true);
					checkResult.setResult(true);

				}

				if (!checkResult.isSupportedSize() && !checkResult.isResize())
				{
					checkResult.setResult(false);
				}

				// Other features
				// Zoom
				checkResult.setZoomSupported(parameters.isZoomSupported());
				if (checkResult.isZoomSupported())
				{
					Log.d(DEBUG_TAG, "Zoom supported");
				}
				else
				{
					Log.d(DEBUG_TAG, "Zoom no supported");
				}

				// Flash
				checkResult.setFlashMode(parameters.getFlashMode());
				if (checkResult.getFlashMode() == null)
				{
					Log.d(DEBUG_TAG, "Flash no supported");
				}
				else
				{
					Log.d(DEBUG_TAG, "FlashMode = " + checkResult.getFlashMode());
				}
			}
		}

		// Release camera
		if (camera != null) {
			camera.release();
			camera = null;
		}

		Log.d(DEBUG_TAG, "Result = " + String.valueOf(checkResult.isResult()));

		return checkResult;
	}

}