package bak_data.mongoDB.service.impl;

import bak_data.mongoDB.dao.BaseDAOImpl;
import bak_data.mongoDB.service.BaseService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("BaseService")
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseDAOImpl dao;

    @Override
    public boolean insert(String collectionName, BasicDBObject bean) {
        return dao.insert(collectionName,bean);
    }

    @Override
    public boolean insert(String collectionName, Object bean) {
        return dao.insert(collectionName,bean);
    }

    @Override
    public boolean delete(String collectionName, BasicDBObject bean) {
        return dao.delete(collectionName,bean);
    }

    @Override
    public boolean delete(String collectionName, Object bean) {
        return dao.delete(collectionName,bean);
    }

    @Override
    public List find(String collectionName, BasicDBObject bean) {
        return dao.find(collectionName,bean);
    }

    @Override
    public List find(String collectionName, Object bean) {
        return dao.find(collectionName,bean);
    }

    @Override
    public List find(String collectionName) {
        return dao.find(collectionName);
    }

    @Override
    public boolean update(String collectionName, BasicDBObject oldBean, BasicDBObject newBean) {
        return dao.update(collectionName,oldBean,newBean);
    }

    @Override
    public boolean update(String collectionName, DBObject oldBean, Object newBean) {
        return dao.update(collectionName,oldBean,newBean);
    }

    @Override
    public long queryCount(String collectionName, DBObject params) {
        return dao.queryCount(collectionName,params);
    }

    @Override
    public List<Map<String, Object>> query(String collectionName, BasicDBObject param, int startRow, int rows) {
        return dao.query(collectionName,param,startRow,rows);
    }

}
