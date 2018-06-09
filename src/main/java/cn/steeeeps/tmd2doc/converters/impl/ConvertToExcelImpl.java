package cn.steeeeps.tmd2doc.converters.impl;

import cn.steeeeps.tmd2doc.converters.Converter;
import cn.steeeeps.tmd2doc.entity.ColumnMetadata;
import cn.steeeeps.tmd2doc.entity.TableMetadata;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.util.List;

/**
 * 描述:
 * 写入 excel
 *
 * @author taopy
 * @create 2018-06-08 下午10:05
 */
public class ConvertToExcelImpl implements Converter {
    private Logger logger = LoggerFactory.getLogger(ConvertToExcelImpl.class);
    private static String[] columns = {"列名", "数据类型", "长度", "精度", "是否为空", "是否自增长", "备注"};
    private Workbook workbook;

    public ConvertToExcelImpl() {
        workbook = new XSSFWorkbook();

    }

    @Override
    public Boolean convert(List<TableMetadata> metadataList, String docPath) {

        try {

            Sheet sheet = workbook.createSheet("sheet1");

            int rowNum = 0;
            CellStyle headerStyle = createHeaderStyle();

            for (TableMetadata metadata : metadataList) {
                Row tableNameRow = sheet.createRow(rowNum++);
                tableNameRow.createCell(0).setCellValue("表名");
                tableNameRow.createCell(1).setCellValue(metadata.getTableName());
                tableNameRow.createCell(2).setCellValue("中文名");
                tableNameRow.createCell(3).setCellValue(metadata.getRemark());
                sheet.getRow(rowNum - 1).cellIterator().forEachRemaining(c -> c.setCellStyle(headerStyle));
                Row headerRow = sheet.createRow(rowNum++);
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerStyle);
                }
                for (ColumnMetadata columnMetadata : metadata.getColumnMetadataList()
                        ) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(columnMetadata.getColumnName());
                    row.createCell(1).setCellValue(columnMetadata.getDataType());
                    row.createCell(2).setCellValue(columnMetadata.getColumnSize());
                    row.createCell(3).setCellValue(columnMetadata.getDecimaldigits());
                    row.createCell(4).setCellValue(columnMetadata.getNullable());
                    row.createCell(5).setCellValue(columnMetadata.getAutoIncrment());
                    row.createCell(6).setCellValue(columnMetadata.getRemark());

                }
                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }
                rowNum += 3;

            }

            FileOutputStream fileout = new FileOutputStream(docPath);
            workbook.write(fileout);
            fileout.close();
            workbook.close();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    private CellStyle createHeaderStyle() {
        CellStyle commonStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        commonStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        commonStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        commonStyle.setFont(font);
        return commonStyle;

    }


}