package cn.steeeeps.tmd2doc.entity;

import java.util.List;

/**
 * 描述:
 * 表元数据
 *
 * @author taopy
 * @create 2018-06-08 下午9:09
 */
public class TableMetadata {

    private String tableName;
    private String remark;

    private List<ColumnMetadata> columnMetadataList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TableMetadata(String tableName, String remark) {
        this.tableName = tableName;
        this.remark = remark;
    }

    public List<ColumnMetadata> getColumnMetadataList() {
        return columnMetadataList;
    }

    public void setColumnMetadataList(List<ColumnMetadata> columnMetadataList) {
        this.columnMetadataList = columnMetadataList;
    }
}