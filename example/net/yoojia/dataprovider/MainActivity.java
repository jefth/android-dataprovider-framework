package net.yoojia.dataprovider;

import net.yoojia.dataprovider.logic.CategoryEntity;
import net.yoojia.dataprovider.logic.CategoryProvider;
import net.yoojia.dataprovider.utility.AsyncRequery;
import net.yoojia.dataprovider.utility.AsyncRequery.RequeryInvoker;
import net.yoojia.dataprovider.utility.EntityUtility;
import net.yoojia.dataprovider.utility.SimpleObserver;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int key = 1;
	final int size = 10;
	
	EntityUtility<CategoryEntity> cu = new EntityUtility<CategoryEntity>(CategoryEntity.class);
	
	ContentResolver resolver;
	
	RequeryInvoker requeryInvoker = new RequeryInvoker() {
		
		@Override
		public Cursor doQueryInBackground() {
			System.out.println("》》》》》查询。。。"+CategoryProvider.URI_GROUP);
			return resolver.query(CategoryProvider.URI_GROUP, null, null, null, null);
		}
	};
	
	
	MyCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		resolver = getContentResolver();
		
		View del = findViewById(R.id.delete);
		View add = findViewById(R.id.add);
		View change = findViewById(R.id.change);
		View clean = findViewById(R.id.clean);
		
		ListView listView = (ListView) findViewById(R.id.list);
		
		del.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				String where = CategoryProvider.TableConfig.CLUMN_ID+"=?";
//				String[] args = CursorUtility.toArgs(new Random().nextInt(50));
//				int rows = resolver.delete(CategoryProvider.URI_ITEM, where, args);
//				if(rows > 0)
//				resolver.notifyChange(CategoryProvider.URI_ITEM, null);
			}
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ContentValues[] values = new ContentValues[size];
				for(int i=0;i<size;i++){
					CategoryEntity cate = new CategoryEntity();
					cate.setCateId(i);
					cate.setCount(10);
					cate.setIconUrl("icon icon icon icon icon ");
					cate.setName("Category-"+i);
					cate.setParentId(99);
					values[i] = cu.entityToValues(cate);
				}
				
				resolver.bulkInsert(CategoryProvider.URI_GROUP, values);
				resolver.notifyChange(CategoryProvider.URI_GROUP, null);
			}
		});
		
		clean.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				String where = CategoryProvider.TableConfig.CLUMN_PARENT+"=?";
//				String[] args = CursorUtility.toArgs(0);
//				int rows = resolver.delete(CategoryProvider.URI_GROUP, where, args);
//				if(rows > 0){
//					resolver.notifyChange(CategoryProvider.URI_GROUP, null);
//				}
			}
		});
		
		change.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				ContentValues values = new ContentValues();
//				values.put(CategoryProvider.TableConfig.CLUMN_NAME, "### NAME = "+new Random().nextInt(100000));
//				String where = CategoryProvider.TableConfig.CLUMN_ID+"=?";
//				String[] args = CursorUtility.toArgs(new Random().nextInt(50));
//				int rows = resolver.update(CategoryProvider.URI_ITEM, values,where, args);
//				System.out.println(">>> 响应行数："+rows);
//				if(rows > 0)
//				resolver.notifyChange(CategoryProvider.URI_ITEM, null);
			}
		});
		
		adapter = new MyCursorAdapter(this, null);
		listView.setAdapter(adapter);
		listView.setFastScrollEnabled(true);
		
		resolver.registerContentObserver(CategoryProvider.URI_GROUP, true, new SimpleObserver(new SimpleObserver.OnChangeListener() {
			@Override
			public void onChange(boolean selfChange) {
				System.out.println(">>>> 数据发生改变。。");
				new AsyncRequery(adapter, requeryInvoker).execute();
			}
		}));
		
		
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
            CategoryEntity entity = cu.cursorToEntity(cursor);
            noteTitle.setText(entity.toString());  
        }  
          
        @Override  
        public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {  
             return new TextView(mContext);
        }

		@Override
		public void changeCursor(Cursor cursor) {
			super.changeCursor(cursor);
			System.out.println(">>>> 切换Cursor ： "+cursor.getCount());
		} 
        
        
    }

}
