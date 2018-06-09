package cn.steeeeps.tmd2doc.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * 描述:
 * 列的元数据
 *
 * @author taopy
 * @create 2018-06-08 下午9:11
 */
public class ColumnMetadata {
    private String columnName;

    private String dataType;

    private Integer columnSize;

    private Integer decimaldigits;

    private String nullable;
    private String autoIncrment;
    private String remark;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public Integer getDecimaldigits() {
        return decimaldigits;
    }

    public void setDecimaldigits(Integer decimaldigits) {
        this.decimaldigits = decimaldigits;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getAutoIncrment() {
        return autoIncrment;
    }

    public void setAutoIncrment(String autoIncrment) {
        this.autoIncrment = autoIncrment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}