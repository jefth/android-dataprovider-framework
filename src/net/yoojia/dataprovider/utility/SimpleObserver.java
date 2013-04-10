package net.yoojia.dataprovider.utility;

import android.database.ContentObserver;
import android.os.Handler;

public class SimpleObserver extends ContentObserver {

	public interface OnChangeListener{
		void onChange(boolean selfChange);
	}
	
	private OnChangeListener listener;
	
	public SimpleObserver(OnChangeListener l) {
		super(new Handler());
		listener = l;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		listener.onChange(selfChange);
	}

}
