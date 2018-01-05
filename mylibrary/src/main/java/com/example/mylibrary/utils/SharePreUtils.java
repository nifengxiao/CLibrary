package com.example.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 一个SharedPreferences的工具类
 *  @author 谭杰栖
 */
public class SharePreUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME_IN_SHARE = "share_da";

    /**
     * 把数据放进SharedPreferences里面去
     * @param context  上下文
     * @param key  键值对的键
     * @param object 数据
     */
    public static void put(Context context, String key, Object object){
        SharedPreferences sf =
                context.getSharedPreferences(FILE_NAME_IN_SHARE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sf.edit();
        //根据类型来写进去
        if(object instanceof String){
            edit.putString(key,(String)object);
        }else if(object instanceof Integer){
            edit.putInt(key, (Integer) object);
        }else if(object instanceof Boolean){
            edit.putBoolean(key, (Boolean) object);
        }else if(object instanceof Float){
            edit.putFloat(key, (Float) object);
        }else if(object instanceof Long){
            edit.putLong(key, (Long) object);
        } else{
            edit.putString(key, String.valueOf(object));
        }
        SharedPreferencesCompat.apply(edit);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context 上下文
     * @param key 想要获取到的数据的key值
     * @param defaultObject 默认值
     * @return 取出来的数据，返回null为没有值
     */
    public static Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_IN_SHARE,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }
    /**
     * 移除某个key值已经对应的值
     * @param context 上下文
     * @param key  想要移除的数据的key
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_IN_SHARE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }
    /**
     * 清除所有数据
     * @param context 上下文
     */
    public static void clear(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_IN_SHARE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param context 上下文
     * @param key  待查询的key
     * @return   是否包含某个key，true：存在 false  ： 不存在
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_IN_SHARE,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context 上下文
     * @return  返回所有数据
     */
    public static Map<String, ?> getAll(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME_IN_SHARE,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor 一个可以编辑内容的工具
         */
        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e){
            }
            editor.commit();
        }
    }

}

