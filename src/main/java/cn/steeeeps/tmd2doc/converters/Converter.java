package cn.steeeeps.tmd2doc.converters;

import cn.steeeeps.tmd2doc.entity.TableMetadata;

import java.util.List;

/**
 * 描述:
 * 转换接口
 *
 * @author taopy
 * @create 2018-06-08 下午10:23
 */
public interface Converter {

    Boolean convert(List<TableMetadata> metadataList,String docPath);
}
