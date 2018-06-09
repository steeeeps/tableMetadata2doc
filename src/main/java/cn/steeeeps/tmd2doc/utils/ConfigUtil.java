package cn.steeeeps.tmd2doc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Properties;

/**
 * 描述:
 * 读取配置信息
 *
 * @author taopy
 * @create 2018-06-06 下午10:58
 */
public class ConfigUtil {
    private Properties props;
    private String configFile;

    private Logger log = LoggerFactory.getLogger(ConfigUtil.class);


    public ConfigUtil(String configFile) {
        this.configFile = configFile;
        init();
    }


    private void init() {
        if (props == null) {
            InputStream inputStr = null;
            try {
                inputStr = new FileInputStream(getClass().getClassLoader().getResource(configFile).getFile());
                props = new Properties();
                props.load(inputStr);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    inputStr.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 获取Properties对象
     *
     * @return
     */
    public Properties getProps() {
        return props;
    }

    /**
     * 获取指定的值，返回String类型
     */
    public String getString(String key) {
        String value = this.props.get(key).toString();
        return value;
    }


    /**
     * 将字符串转为Double型返回
     */
    public Double getDouble(String key) {
        Double doublevalue = Double.parseDouble(this.props.get(key)
                .toString());
        return doublevalue;
    }

    /**
     * 将字符串转为Integer型返回
     */
    public Integer getInteger(String key) {
        Integer intvalue = Integer.parseInt(this.props.get(key)
                .toString());
        return intvalue;
    }

    /**
     * 将字符串转为Long型返回
     */
    public Long getLong(String key) {
        Long longvalue = Long
                .parseLong(this.props.get(key).toString());
        return longvalue;
    }

}