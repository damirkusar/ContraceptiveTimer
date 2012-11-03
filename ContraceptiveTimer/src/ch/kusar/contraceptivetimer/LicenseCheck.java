//package ch.kusar.contraceptivetimer;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Settings.Secure;
//import android.widget.Toast;
//import ch.kusar.contraceptivetimer.businessobjects.LicenseStatus;
//import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;
//
///**
// * NOTES ON USING THIS LICENSE FILE IN YOUR APPLICATION: 1. Define the package
// * of you application above 2. Be sure your public key is set properly @BASE64_PUBLIC_KEY
// * 3. Change your SALT using random digits 4. Under AllowAccess, Add your
// * previously used MainActivity 5. Add this activity to your manifest and set
// * intent filters to MAIN and LAUNCHER 6. Remove Intent Filters from previous
// * main activity
// */
//public class LicenseCheck extends Activity {
//	public class MyLicenseCheckerCallBack implements LicenseCheckerCallback {
//
//		private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkkQA64KQLGGqnhXQ84KTeNkqsMXqJe25BncI4vENSiBtSzOPOCm1rthOjUFSzojELnIvbDYGsBBN7K0XAaW7sSzGoTgiDKJHDBps86kHFAdpXcvboFvFm6atIIZJf06rGbLbmFevv28Pp4gPYyF4+Y9wCXjc32uy9NAOT9BSHOpSs2T1rhM/LSPojXxXAwPUZ4DoDFG9gWryYc3dIYAf3z2E7lXq8fh49Ei38xBNV91UsdxxA0BIyTjGkTpU9Ib3opCUjgLAEYVZFomey4+0QyJCRQ090HAV88bj5GorfS0JJAl2+GslEKP5FolDdiA/88Mi18Yo5kVxg6OG1l9MCQIDAQAB";
//		private static final byte[] SALT = new byte[] { -29, 22, -3, 2, -19, 85, -88, 7, -10, 20, -6, 77, -77, 100, -100, 8, -8, 60, -64, 11 };
//		private LicenseChecker mChecker;
//
//		// A handler on the UI thread.
//		private LicenseCheckerCallback mLicenseCheckerCallback;
//
//		@Override
//		public void allow() {
//			if (LicenseCheck.this.isFinishing()) {
//				// Don't update UI if Activity is finishing.
//				return;
//			}
//			// Should allow user access.
//			LicenseCheck.this.updateLicenceStatus();
//			LicenseCheck.this.startMainActivity();
//		}
//
//		@Override
//		public void applicationError(ApplicationErrorCode errorCode) {
//			if (LicenseCheck.this.isFinishing()) {
//				// Don't update UI if Activity is finishing.
//				return;
//			}
//			// This is a polite way of saying the developer made a mistake
//			// while setting up or calling the license checker library.
//			// Please examine the error code and fix the error.
//			LicenseCheck.this.toast("Error: " + errorCode.name());
//			LicenseCheck.this.startMainActivity();
//		}
//
//		@Override
//		public void dontAllow() {
//			if (LicenseCheck.this.isFinishing()) {
//				// Don't update UI if Activity is finishing.
//				return;
//			}
//
//			// Should not allow access. In most cases, the app should assume
//			// the user has access unless it encounters this. If it does,
//			// the app should inform the user of their unlicensed ways
//			// and then either shut down the app or limit the user to a
//			// restricted set of features.
//			// In this example, we show a dialog that takes the user to Market.
//			LicenseCheck.this.showDialog(0);
//		}
//	}
//
//	private void updateLicenceStatus() {
//		InternalStorageWrapper internalStorageWrapper = new InternalStorageWrapper(this.getApplicationContext());
//		internalStorageWrapper.saveToStorage(new LicenseStatus(true));
//	}
//
//	private boolean getLicenceStatus() {
//		InternalStorageWrapper internalStorageWrapper = new InternalStorageWrapper(this.getApplicationContext());
//		LicenseStatus ls = internalStorageWrapper.loadLicenseFileFromStorage();
//		if (ls == null) {
//			return false;
//		} else {
//			return ls.isPaid();
//		}
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		boolean isPaid = this.getLicenceStatus();
//		if (isPaid) {
//			this.startMainActivity();
//		} else {
//
//			// Try to use more data here. ANDROID_ID is a single point of
//			// attack.
//			String deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
//
//			// Library calls this when it's done.
//			mLicenseCheckerCallBack = new MyLicenseCheckerCallBack();
//			// Construct the LicenseChecker with a policy.
//			mChecker = new LicenseChecker(this, new ServerManagedPolicy(this, new AESObfuscator(SALT, this.getPackageName(), deviceId)), BASE64_PUBLIC_KEY);
//			this.doCheck();
//		}
//	}
//
//	private void doCheck() {
//		mChecker.checkAccess(mLicenseCheckerCallBack);
//	}
//
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		// We have only one dialog.
//		return new AlertDialog.Builder(this).setTitle("Application Not Licensed").setCancelable(false)
//				.setMessage("This application is not licensed. Please purchase it from Android Market")
//				.setPositiveButton("Buy App", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://market.android.com/details?id="
//								+ LicenseCheck.this.getPackageName()));
//						LicenseCheck.this.startActivity(marketIntent);
//						LicenseCheck.this.finish();
//					}
//				}).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						LicenseCheck.this.finish();
//					}
//				}).create();
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		mChecker.onDestroy();
//	}
//
//	private void startMainActivity() {
//		this.startActivity(new Intent(this, HomeScreenActivity.class));
//		this.finish();
//	}
//
//	public void toast(String string) {
//		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//	}
// }