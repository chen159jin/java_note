package assistPackage;

import java.sql.ResultSet;

/**
 * 实体类映射用的 
 * @author Jin
 *
 */
public interface ObjectMapper {  
    public Object mapping(ResultSet rs);  
  
}  
