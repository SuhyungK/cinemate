package movie.cinemate.cinemate.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.*;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@Slf4j
public class NestedRowMapper<T> implements RowMapper<T> {

    private Class<T> mappedClass;

    public NestedRowMapper(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T mappedObject = BeanUtils.instantiateClass(this.mappedClass);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);

        bw.setAutoGrowNestedPaths(true);

        ResultSetMetaData meta_data = rs.getMetaData();
        log.info("meta_data={}", meta_data);
        int columnCount = meta_data.getColumnCount();

        for (int index = 1; index <= columnCount; index++) {
            try {
                String column = JdbcUtils.lookupColumnName(meta_data, index);
                Object value = JdbcUtils.getResultSetValue(rs, index, Class.forName(meta_data.getColumnClassName(index)));
                log.info("colum={}, value={}", column, value);
                bw.setPropertyValue(column, value);
            } catch (TypeMismatchException | NotWritablePropertyException | ClassNotFoundException exception) {
                throw new SQLException();
            }
        }

        return mappedObject;
    }
}
