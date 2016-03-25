package com.jiahuan.svgmapview.core.helper.db;


import android.net.Uri;
import android.provider.BaseColumns;
public interface IProivderMetaData {
    // 定义外部访问的Authority
    public static final String AUTHORITY = "com.example.indoornavi";
    // 数据库名称
    public static final String DB_NAME = "indoornavi.db";
    // 数据库版本
    public static final int VERSION = 1;
    public interface PoiTableMetaData extends BaseColumns {
        // 表名
        public static final String TABLE_NAME = "poi";
        // 外部程序访问本表的uri地址
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/" + TABLE_NAME);
        // book表列名
        public static final String POI_ID = "_id";
        public static final String POI_NAME = "name";
        public static final String POI_LOCATON_X = "poi_location_x";
        public static final String POI_LOCATON_Y = "poi_location_y";
        public static final String POI_NEXT_X = "poi_next_x";
        public static final String POI_NEXT_Y = "poi_next_y";
        
        //默认排序
        public static final String SORT_ORDER="_id desc";
        //得到book表中的所有记录
        public static final String CONTENT_LIST="vnd.android.cursor.dir/vnd.indoornavi.poi";
        //得到一个表信息
        public static final String CONTENT_ITEM="vnd.android.cursor.item/vnd.indoornavi.poi";
    }
}