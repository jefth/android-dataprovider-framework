package net.yoojia.dataprovider.utility;

import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.CursorAdapter;

/**
 * 异步查询数据库
 * @author 桥下一粒砂 (chenyoca@gmail.com)
 * @date   2013-4-7
 */
public class AsyncRequery extends AsyncTask<Void, Void, Cursor> {
	
	public interface RequeryInvoker {
		Cursor doQueryInBackground();
	}

	private Cursor usingCursor;
	private CursorAdapter adapter;
	private RequeryInvoker invoker;

	public AsyncRequery(CursorAdapter adapter,RequeryInvoker invoker) {
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