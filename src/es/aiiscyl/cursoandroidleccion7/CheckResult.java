package es.aiiscyl.cursoandroidleccion7;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author masensio & mvillace
 * Contains the result for checkDevice
 */

public class CheckResult implements Parcelable{

	private boolean mResult;
	private boolean mCameraAvailable;
	private int mAndroidVersion;
	private boolean mZoomSupported;
	private String mFlashMode;
	private boolean mSupportedSize;
	private boolean mResize;
	private int mHeightResize;
	private int mWidthResize;
	private String deviceId;


	// Constructors
	public CheckResult() {
		super();
		this.mResult = true;
		this.mCameraAvailable= false;
		this.mZoomSupported = false;
		this.mSupportedSize= false;
		this.mResize = false;
	}



	public CheckResult(boolean result, boolean cameraAvailable,
			int androidVersion, boolean zoomSupported, String flashMode,
			boolean supportedSize, boolean resize, int heightResize,
			int widthResize) {
		super();
		this.mResult = result;
		this.mCameraAvailable = cameraAvailable;
		this.mAndroidVersion = androidVersion;
		this.mZoomSupported = zoomSupported;
		this.mFlashMode = flashMode;
		this.mSupportedSize = supportedSize;
		this.mResize = resize;
		this.mHeightResize = heightResize;
		this.mWidthResize = widthResize;
	}



	// Getters and Setters
	public boolean isResult() {
		return mResult;
	}
	public void setResult(boolean result) {
		this.mResult = result;
	}
	public boolean isCameraAvailable() {
		return mCameraAvailable;
	}
	public void setCameraAvailable(boolean cameraAvailable) {
		this.mCameraAvailable = cameraAvailable;
	}
	public int getAndroidVersion() {
		return mAndroidVersion;
	}
	public void setAndroidVersion(int androidVersion) {
		this.mAndroidVersion = androidVersion;
	}
	public boolean isZoomSupported() {
		return mZoomSupported;
	}
	public void setZoomSupported(boolean zoomSupported) {
		this.mZoomSupported = zoomSupported;
	}
	public String getFlashMode() {
		return mFlashMode;
	}
	public void setFlashMode(String flashMode) {
		this.mFlashMode = flashMode;
	}
	public boolean isSupportedSize() {
		return mSupportedSize;
	}
	public void setSupportedSize(boolean supportedSize) {
		this.mSupportedSize = supportedSize;
	}
	public boolean isResize() {
		return mResize;
	}
	public void setResize(boolean resize) {
		this.mResize = resize;
	}
	public int getHeightResize() {
		return mHeightResize;
	}
	public void setHeightResize(int heightResize) {
		this.mHeightResize = heightResize;
	}
	public int getWidthResize() {
		return mWidthResize;
	}
	public void setWidthResize(int widthResize) {
		this.mWidthResize = widthResize;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}



	//Parcelable
	public CheckResult(Parcel in){
		readFromParcel(in);
	}

	/**
	 *
	 * Call constructor to create parcelable element
	 *
	 * @param in : to re-create the object
	 */
	private void readFromParcel(Parcel in)
	{
		//Read data in same order that they are written
		String isResult = in.readString();
		if (isResult.equals("true"))		
			this.mResult = true;
		else
			this.mResult = false;
		String hasCam = in.readString();
		if (hasCam.equals("true"))		
			this.mCameraAvailable = true;
		else
			this.mCameraAvailable = false;

		this.mAndroidVersion = in.readInt();

		String hasZoom = in.readString();
		if (hasZoom.equals("true"))		
			this.mZoomSupported = true;
		else
			this.mZoomSupported = false;

		this.mFlashMode = in.readString();

		String isSize = in.readString();
		if (isSize.equals("true"))		
			this.mSupportedSize = true;
		else
			this.mSupportedSize = false;

		String isResize = in.readString();
		if (isResize.equals("true"))		
			this.mResize = true;
		else
			this.mResize = false;

		this.mHeightResize = in.readInt();
		this.mWidthResize = in.readInt();


	}

	// For create new objects CheckResult or Array of CheckREsult
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		@Override
		public CheckResult createFromParcel(Parcel in) {
			return new CheckResult(in); 
		}

		@Override
		public CheckResult[] newArray(int size) {
			return new CheckResult[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}



	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// Write values for parcel
		dest.writeString(String.valueOf(mResult));
		dest.writeString(String.valueOf(mCameraAvailable));
		dest.writeInt(mAndroidVersion);
		dest.writeString(String.valueOf(mZoomSupported));
		dest.writeString(mFlashMode);
		dest.writeString(String.valueOf(mSupportedSize));
		dest.writeString(String.valueOf(mResize));
		dest.writeInt(mHeightResize);
		dest.writeInt(mWidthResize);

	}	

}

