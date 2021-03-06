package com.java.servlets.dao.impl;

import com.java.servlets.dao.AbstractDao;
import com.java.servlets.dao.Service.ResultSetMapper;
import com.java.servlets.dao.Service.SqlXmlReader;
import com.java.servlets.model.WorkNote;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by proton2 on 03.06.2017.
 */
public class WorkNoteDaoImpl extends AbstractDao<WorkNote> {
    private static WorkNoteDaoImpl crudDAO;

    private WorkNoteDaoImpl(Class<WorkNote> type) {
        super(type);
    }

    public static synchronized WorkNoteDaoImpl getInstance() {
        if (crudDAO == null) {
            crudDAO = new WorkNoteDaoImpl(WorkNote.class);
        }
        return crudDAO;
    }

    public List<WorkNote> getListById(Long itemId) throws IOException {
        String getListById = SqlXmlReader.getQuerryStr(getType().getSimpleName(), "getListById");
        List<WorkNote> result = null;
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(getListById, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
            ps.setLong(1, itemId);
            ResultSet rs = ps.executeQuery();

            ResultSetMapper<WorkNote> resultSetMapper = new ResultSetMapper<>();
            result = resultSetMapper.mapRersultSetToList(rs, WorkNote.class);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }

        return result;
    }
}
