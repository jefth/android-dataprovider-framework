package sg.ilovedeals.dataservice;

import java.util.Random;

import sg.ilovedeals.dataservice.logic.CategoryProvider;
import sg.ilovedeals.dataservice.util.CursorUtility;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int key = 1;
	final int size = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ContentResolver resolver = getContentResolver();
		
		View del = findViewById(R.id.delete);
		View add = findViewById(R.id.add);
		View change = findViewById(R.id.change);
		View clean = findViewById(R.id.clean);
		
		ListView listView = (ListView) findViewById(R.id.list);
		
		del.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String where = CategoryProvider.Table.CLUMN_ID+"=?";
				String[] args = CursorUtility.toArgs(new Random().nextInt(50));
				int rows = resolver.delete(CategoryProvider.URI_ITEM, where, args);
				if(rows > 0)
				resolver.notifyChange(CategoryProvider.URI_ITEM, null);
			}
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ContentValues[] values = new ContentValues[size];
				for(int i=0;i<size;i++){
					ContentValues value = new ContentValues();
					value.put(CategoryProvider.Table.CLUMN_EXTRA, "extra");
					value.put(CategoryProvider.Table.CLUMN_ICON, "icon");
					value.put(CategoryProvider.Table.CLUMN_NAME, "name-"+key);
					value.put(CategoryProvider.Table.CLUMN_PARENT, 0);
					value.put(CategoryProvider.Table.CLUMN_ID, key);
					key++;
					values[i] = value;
				}
				resolver.bulkInsert(CategoryProvider.URI_GROUP, values);
				resolver.notifyChange(CategoryProvider.URI_GROUP, null);
			}
		});
		
		clean.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String where = CategoryProvider.Table.CLUMN_PARENT+"=?";
				String[] args = CursorUtility.toArgs(0);
				int rows = resolver.delete(CategoryProvider.URI_GROUP, where, args);
				if(rows > 0){
					resolver.notifyChange(CategoryProvider.URI_GROUP, null);
				}
			}
		});
		
		change.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(CategoryProvider.Table.CLUMN_NAME, "### NAME = "+new Random().nextInt(100000));
				String where = CategoryProvider.Table.CLUMN_ID+"=?";
				String[] args = CursorUtility.toArgs(new Random().nextInt(50));
				int rows = resolver.update(CategoryProvider.URI_ITEM, values,where, args);
				System.out.println(">>> 响应行数："+rows);
				if(rows > 0)
				resolver.notifyChange(CategoryProvider.URI_ITEM, null);
			}
		});
		
		resolver.registerContentObserver(CategoryProvider.URI_GROUP, true, new MyContentObserver(new Handler()));
		listCursor = resolver.query(CategoryProvider.URI_GROUP, null, null, null, null);
		MyCursorAdapter adapter = new MyCursorAdapter(this, listCursor);
		listView.setAdapter(adapter);
		listView.setFastScrollEnabled(true);
	}
	
	private Cursor listCursor;
	
	class MyContentObserver extends ContentObserver{

		public MyContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			System.out.println(">>>> 数据发生改变。。");
			listCursor.requery();
		}
		
	}
	
	class MyCursorAdapter extends CursorAdapter{  
        
        private Context mContext;  
          
        public MyCursorAdapter(Context context, Cursor c) {  
            super(context, c);  
            mContext = context;  
        }  
        
        
          
        @Override  
        public void bindView(View view, Context context, Cursor cursor) {  
            TextView noteTitle = (TextView) view;
            String title = cursor.getString(cursor.getColumnIndex(CategoryProvider.Table.CLUMN_NAME));
            noteTitle.setText(title);  
                          
          //super.bindView(view, context, cursor);   
        }  
          
        @Override  
        public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {  
             return new TextView(mContext);
        }         
    }

}
