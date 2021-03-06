package com.mkch.maikejia.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mkch.maikejia.R;
import com.mkch.maikejia.bean.AppVersion;
import com.mkch.maikejia.bean.SearchHistory;
import com.mkch.maikejia.bean.User;

public class CommonUtil {
	/**
	 * 保存用户信息
	 * @param user
	 * @param mContext
	 */
	public static void saveUserInfo(User user,Context mContext){
		//构建对象
		Gson gson = new Gson();
		String gsonUser = gson.toJson(user);
		//获取指定Key的SharedPreferences对象
		SharedPreferences _SP = mContext.getSharedPreferences("UserInfo", mContext.MODE_PRIVATE);
		//获取编辑
		SharedPreferences.Editor _Editor = _SP.edit();
		//按照指定Key放入数据
		_Editor.putString("user", gsonUser);
		//提交保存数据
		_Editor.commit();
	}
	
	/**
	 * 获取登录用户的信息
	 * @param mContext
	 * @return
	 */
	public static User getUserInfo(Context mContext){
		SharedPreferences _SP = mContext.getSharedPreferences("UserInfo", mContext.MODE_PRIVATE);
		if(_SP==null){
			return null;
		}else{
			String jsonUser = _SP.getString("user", "");
			Gson gson = new Gson();
			User user = gson.fromJson(jsonUser, User.class);
			return user;
		}
		
	}
	
	/**
	 * 清除用户信息
	 * @param mContext
	 */
	public static void clearUserInfo(Context mContext){
		SharedPreferences _SP = mContext.getSharedPreferences("UserInfo", mContext.MODE_PRIVATE);
		if(_SP!=null){
			SharedPreferences.Editor _Editor = _SP.edit();
			_Editor.clear();
			_Editor.commit();
		}
	}
	
	/**
	 * 保存搜索历史信息
	 * @param user
	 * @param mContext
	 */
	public static void saveSearchHistory(SearchHistory searchHistory,Context mContext){
		//构建对象
		Gson gson = new Gson();
		String gsonSearchHistory = gson.toJson(searchHistory);
		//获取指定Key的SharedPreferences对象
		SharedPreferences _SP = mContext.getSharedPreferences("SearchHistory", mContext.MODE_PRIVATE);
		//获取编辑
		SharedPreferences.Editor _Editor = _SP.edit();
		//按照指定Key放入数据
		_Editor.putString("searchHistory", gsonSearchHistory);
		//提交保存数据
		_Editor.commit();
	}
	
	/**
	 * 获取搜索历史的信息
	 * @param mContext
	 * @return
	 */
	public static SearchHistory getSearchHistory(Context mContext){
		SharedPreferences _SP = mContext.getSharedPreferences("SearchHistory", mContext.MODE_PRIVATE);
		if(_SP==null){
			return null;
		}else{
			String jsonSearchHistory = _SP.getString("searchHistory", "");
			Gson gson = new Gson();
			SearchHistory searchHistory = gson.fromJson(jsonSearchHistory, SearchHistory.class);
			return searchHistory;
		}
		
	}
	
	/**
	 * 设置子回复listview的高度，以适应布局
	 * @param listView
	 */
	public static void setReplyListViewHeight(ListView listView) {    
        // 获取ListView对应的Adapter    
        ListAdapter listAdapter = listView.getAdapter();    
        
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0); // 计算子项View 的宽高    
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度    
        }    
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }   
	
	/**
	 * 设置listview的高度，以适应布局
	 * @param listView
	 */
	public static void setListViewHeight(ListView listView) {    
        // 获取ListView对应的Adapter    
        ListAdapter listAdapter = listView.getAdapter();    
        
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0); // 计算子项View 的宽高    
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度    
        }    
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }   
	
	/**
	 * 发送错误信息到消息队列
	 * @param errorMsg
	 */
	public static void sendErrorMessage(String errorMsg,Handler handler){
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putSerializable("ErrorMsg", errorMsg);
		msg.setData(data);
		handler.sendMessage(msg);
	}
	
	/**
	 * 获取app版本
	 * @param context
	 * @return
	 */
	public static AppVersion getAppVersion(Context context){
		AppVersion version = null;
		
		PackageManager pm = context.getPackageManager();
		PackageInfo info = null;
		if(pm != null){
			try {
				info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(info != null){
			version = new AppVersion();
			version.setVersionCode(info.versionCode);
			version.setVersionName(info.versionName);
		}
		return version;
	}
	
	/**
	 * 创建luncher图标logo
	 */
	public static void createShortCut(Context context) {
        //创建快捷方式的Intent
        Intent addShortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT"); 
        //不允许重复创建
        addShortcut.putExtra("duplicate", false);
        //<span><span class="comment">指定当前的Activity为快捷方式启动的对象: 如 com.android.music.</span>MusicBrowserActivity<span> </span></span>        
        //<span><span class="comment">注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序</span></span>
        ComponentName comp = new ComponentName(context.getPackageName(), "."
                + ((Activity)context).getLocalClassName());
        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,new Intent(Intent.ACTION_MAIN).setComponent(comp));
        //设置快捷方式的图标
        ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(context,
                R.drawable.ic_launcher);
        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
       // 设置快捷方式的名字
       addShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
       context.sendBroadcast(addShortcut);
  	}
}
