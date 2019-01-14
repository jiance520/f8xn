package bak_data.mongoDB.dao;

import bak_data.mongoDB.utils.BeanUtil;
import bak_data.mongoDB.utils.MongoDBUtil;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("baseDAOImpl")
public class BaseDAOImpl implements BaseDAO {

	@Autowired
	private MongoDBUtil util;
	
	@Override
	  public boolean insert(String collectionName, BasicDBObject bean) {
		DB db = util.getDB(); 
		db.getCollection(collectionName).insert(bean); 
		return false;
	}
	
	@Override
	public boolean insert(String collectionName, Object bean) {
		DB db = util.getDB(); 
		try {
			db.getCollection(collectionName).insert(BeanUtil.bean2DBObject(bean));
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(String collectionName, BasicDBObject bean) {
		DB db = util.getDB();
        db.getCollection(collectionName).remove(bean);
		return false;
	}

	@Override
	public boolean delete(String collectionName, Object bean) {
		DB db = util.getDB();
		try {
			db.getCollection(collectionName).remove(BeanUtil.bean2DBObject(bean));
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public List find(String collectionName, BasicDBObject bean) {
		DB db = util.getDB();
        List list = db.getCollection(collectionName).find(bean).toArray();
		return list;
	}
	
	@Override
	public List find(String collectionName, Object bean) {
		DB db = util.getDB();
        List list = null;
		try {
			list = db.getCollection(collectionName).find(BeanUtil.bean2DBObject(bean)).toArray();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List find(String collectionName) {
		DB db = util.getDB();
		List list = new ArrayList();
		DBCursor cur = db.getCollection(collectionName).find();
		while (cur.hasNext()) {
            DBObject temp= cur.next();
            list.add(temp);
        }
		return list;
	}
	
	@Override
	public boolean update(String collectionName, BasicDBObject oldBean,
			BasicDBObject newBean) {
		DB db = util.getDB();
        db.getCollection(collectionName).update(oldBean, newBean);
        //db.getCollection(collectionName).findAndModify(oldBean, newBean);
		return true;
	}

	
	@Override
	public boolean update(String collectionName, DBObject oldBean, Object newBean) {
		DB db = util.getDB();
		try {
			db.getCollection(collectionName).update(oldBean, BeanUtil.bean2DBObject(newBean));
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	public long queryCount(String collectionName, DBObject params){
		DB db = util.getDB();
		long l = 0L;
		DBCollection collection = null;
		DBCursor cursor = null;
		try {
			collection = db.getCollection(collectionName);
			cursor = collection.find(params);
			l = cursor.count();
		} catch (Exception e) {
			e.printStackTrace();

			if (cursor == null) {
				cursor.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return l;
	  }

	 public List<Map<String, Object>> query(String collectionName, BasicDBObject param, int startRow, int rows){
		DB db = util.getDB();
		List result = new ArrayList();
		/*if (!(db.collectionExists(collectionName))) {
			return result;
		}*/
		DBCollection collection = null;
		DBCursor cursor = null;
		try {
			collection = db.getCollection(collectionName);
			cursor = collection.find(param);
			if ((startRow > 0) && (rows > 0)) {
				cursor.skip(startRow).limit(rows);
			}
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				Map map = next.toMap();
				result.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (cursor == null) {
				cursor.close();
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

	    return result; 
	 }
	 
	 public DBCursor getCursor(String collecName, DBObject params, DBObject fields, DBObject sort, int skip, int limit){//sort即根据哪个条件排序，skip从第几条开始显示，limit显示多少条
		DB db = util.getDB();
	    DBCollection collection;
	    DBCursor dbc = null;
	    try {
	      collection = db.getCollection(collecName);
	      if (params == null)
	        dbc = collection.find();
	      else if (fields != null)
	        dbc = collection.find(params, fields);
	      else
	        dbc = collection.find(params);
	      if (sort != null)
	        dbc = dbc.sort(sort);
	      if (skip > 0)
	        dbc = dbc.skip(skip);
	      if (limit > 0)
	        dbc = dbc.limit(limit);
	      return dbc;
	    } catch (Exception e) {
			e.printStackTrace();
	    }
	    return null;
	  }
	 
}
