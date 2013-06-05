package net.yoojia.dataprovider.sample;

import java.util.Random;

import net.yoojia.dataprovider.Provider;
import net.yoojia.dataprovider.QueryHelper;
import net.yoojia.dataprovider.R;
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

	final int size = 10;
	
	EntityUtility<CategoryEntity> cu = new EntityUtility<CategoryEntity>(CategoryEntity.class);
	
	ContentResolver resolver;
	
	RequeryInvoker requeryInvoker = new RequeryInvoker() {
		
		@Override
		public Cursor doQueryInBackground() {
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
				final int cateId = new Random().nextInt(10);
				int rows = resolver.delete(CategoryProvider.URI_GROUP, "cateId=?", Provider.toArgs(cateId));
				System.out.println("删除数据,影响行数:"+rows);
			}
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ContentValues[] values = new ContentValues[size];
				for(int i=0;i<size;i++){
					CategoryEntity cate = new CategoryEntity();
					final int cateId = new Random().nextInt(size*10);
					cate.setCateId(cateId);
					cate.setCount(10);
					cate.setIconUrl("icon icon icon icon icon ");
					cate.setName("Category-"+i);
					cate.setParentId(99);
					ContentValues value = cu.entityToValues(cate);
					values[i] = value;
					QueryHelper.addWhere(value, "cate_id=?"/*或者 cateId=? , 如果是这种驼峰式式, 会被自动转换成下划线式. */, String.valueOf(i));
				}
				
				int rows = resolver.bulkInsert(CategoryProvider.URI_GROUP, values);
				System.out.println("添加数据,影响行数:"+rows);
			}
		});
		
		clean.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int rows = resolver.delete(CategoryProvider.URI_GROUP, "parentId=?", Provider.toArgs("99"));
				System.out.println("清空数据,影响行数:"+rows);
			}
		});
		
		change.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CategoryEntity cate = new CategoryEntity();
				final int cateId = new Random().nextInt(size*10);
				cate.setCateId(cateId);
				cate.setCount(10);
				cate.setIconUrl("icon icon icon icon icon ");
				cate.setName("Category-###"+cateId);
				cate.setParentId(99);
				ContentValues values = cu.entityToValues(cate);
				int rows = resolver.update(CategoryProvider.URI_ITEM, values,"cateId=?", Provider.toArgs(cateId));
				System.out.println(">>> 更新数据,影响行数："+rows);
			}
		});
		
		adapter = new MyCursorAdapter(this, null);
		listView.setAdapter(adapter);
		listView.setFastScrollEnabled(true);
		
		resolver.registerContentObserver(CategoryProvider.URI_GROUP, true, new SimpleObserver(new SimpleObserver.OnChangeListener() {
			@Override
			public void onChange(boolean selfChange) {
				System.out.println(">>>> 数据发生改变,正在更新...");
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
		} 
        
    }

}
