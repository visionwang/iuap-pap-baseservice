package com.yonyou.iuap.i18n;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于多级目录加载资源文件
 *
 * @author wenfa
 */
public class MultiPropertyMessageResources extends ReloadableResourceBundleMessageSource {

    private static Log logger = LogFactory.getLog(MultiPropertyMessageResources.class);

    private String jname;

    private String regex = "*.properties";

    public static String PROPERTY_POSTFIX = ".properties";

    private PathMatchingResourcePatternResolverEx patternResolver = new PathMatchingResourcePatternResolverEx();


    /**
     * 设置资源加载的路径
     *
     * @param baseName
     */
    public void setBasename(String baseName) {

        List<String> baseNameList = new ArrayList();

        try {

            Resource[] resources = this.patternResolver.getResourcesEx(baseName, this.jname, this.regex);

            Resource[] arrayOfResource1;

            int j = (arrayOfResource1 = resources).length;

            for (int i = 0; i < j; i++) {

                Resource resource = arrayOfResource1[i];

                String path = resource.getURI().toString();

                String fileName = null;

                if (path.indexOf("enum") <= -1) {

                    if (path.indexOf("classes") > 0) {
                        fileName = path.substring(path.indexOf("classes") + 8, path.length());
                    } else {
                        fileName = path.substring(path.indexOf("lib") + 4, path.length());
                    }

                    baseNameList.add(fileName.substring(0, fileName.indexOf(PROPERTY_POSTFIX)));

                }
            }
        } catch (Exception e) {
        }

        setBasenames((String[]) baseNameList.toArray(new String[baseNameList.size()]));
    }

    public String getJname() {
        return this.jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getRegex() {
        return this.regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}