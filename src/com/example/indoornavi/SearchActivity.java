package com.example.indoornavi;

import java.util.ArrayList;  

import android.app.Activity;  
import android.os.Bundle;  
import android.text.TextUtils;  
import android.widget.ArrayAdapter;  
import android.widget.ListView;  
import android.widget.SearchView;  
  
public class SearchActivity extends Activity implements  
        SearchView.OnQueryTextListener {  
    ListView listView;  
    SearchView searchView;  
    Object[] names;  
    //ArrayAdapter<String> adapter;  
    ArrayList<String> mAllList = new ArrayList<String>();  
    MyApplication application;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.searchable);  
        application = (MyApplication) this.getApplicationContext();  
        names = loadData();  
        listView = (ListView) findViewById(R.id.list);  
        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),  
                android.R.layout.simple_expandable_list_item_1, names));  
  
        listView.setTextFilterEnabled(true);  
        searchView = (SearchView) findViewById(R.id.search_view); 
        searchView.setOnQueryTextListener(this);  
        searchView.setSubmitButtonEnabled(false);  
    }  
  
  
    public Object[] loadData() {  
    	
    	for(int i = 0; i < application.elements.size(); i++){
    		mAllList.add(application.elements.get(i).id);  
    	}

        return mAllList.toArray();  
    }  
  
    @Override  
    public boolean onQueryTextChange(String newText) {  
        if (TextUtils.isEmpty(newText)) {  
            // Clear the text filter.  
            listView.clearTextFilter();  
        } else {  
            // Sets the initial value for the text filter.  
            listView.setFilterText(newText.toString());  
        }  
        return false;  
    }  
  
    @Override  
    public boolean onQueryTextSubmit(String query) {  
        // TODO Auto-generated method stub  
        return false;  
    }  
}  