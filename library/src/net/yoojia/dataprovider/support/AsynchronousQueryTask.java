package net.yoojia.dataprovider.support;

import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.CursorAdapter;

/**
 * 异步查询数据库
 * @author 桥下一粒砂 (chenyoca@gmail.com)
 * date   2013-4-7
 */
public class AsynchronousQueryTask extends AsyncTask<Void, Void, Cursor> {
	
	public interface QueryTaskDelegate {
		Cursor doQueryInBackground();
	}

	private Cursor usingCursor;
	private CursorAdapter adapter;
	private QueryTaskDelegate invoker;

	public AsynchronousQueryTask (CursorAdapter adapter, QueryTaskDelegate invoker) {
		this.adapter = adapter;
		this.invoker = invoker;
		this.usingCursor = adapter.getCursor();
	}

	protected Cursor doInBackground(Void... params) {
		return invoker.doQueryInBackground();
	}

	protected void onPostExecute(Cursor newCursor) {
		adapter.changeCursor(newCursor);
		if (usingCursor != null) {
			usingCursor.close();
		}
		usingCursor = newCursor;
	}
}