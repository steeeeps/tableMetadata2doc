package cn.steeeeps.tmd2doc;


import cn.steeeeps.tmd2doc.converters.Converter;
import cn.steeeeps.tmd2doc.converters.impl.ConvertToExcelImpl;
import cn.steeeeps.tmd2doc.entity.TableMetadata;
import cn.steeeeps.tmd2doc.utils.ConfigUtil;
import cn.steeeeps.tmd2doc.utils.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

/**
 * 描述:
 *
 *
 * @author taopy
 * @create 2018-06-07 下午10:29
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        JDBCUtil jdbcUtil;
        ConfigUtil configUtil = new ConfigUtil("config.properties");

        jdbcUtil = new JDBCUtil(configUtil.getString("db.driverClass"),
                configUtil.getString("db.url"),
                configUtil.getString("db.user"),
                configUtil.getString("db.password"));
        jdbcUtil.getConnection();
        List<TableMetadata> tableMetadataList = jdbcUtil.listAllTables();
        for (TableMetadata table : tableMetadataList) {
            table.setColumnMetadataList(jdbcUtil.getTableMetaData(table.getTableName()));
        }

        jdbcUtil.closeConnection();

        Converter converter = new ConvertToExcelImpl();
        if (converter.convert(tableMetadataList, configUtil.getString("converter.output"))) {
            log.debug("convertion success...");
        } else {
            log.error("convertion failure...");
        }
    }
}