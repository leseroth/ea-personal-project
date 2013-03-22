package co.earcos.android.budget.dropbox;

import android.content.Context;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

public class DropboxControl {
	final static private String APP_KEY = "p1u85x3vdwt03z7";
	final static private String APP_SECRET = "e8m1t834bnow6g9";

	final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	private DropboxAPI<AndroidAuthSession> mDBApi;

	private void startSession() {
		// And later in some initialization function:
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys,
				ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
	}

	public void authenticate(Context activity) {

		// MyActivity below should be your activity class name
		mDBApi.getSession().startAuthentication(activity);
	}
}