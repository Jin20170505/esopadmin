/**
 * Copyright &copy; 2015-2020 磐新科技 All rights reserved.
 */
package com.jeeplus.modules.dashboard.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.database.datalink.jdbc.DBPool;

import javax.servlet.ServletContext;

/**
 * 仪表盘Entity
 *
 * @author 刘高峰
 * @version 2018-09-12
 */
public class DashBoard extends DataEntity<DashBoard> {

    private static final long serialVersionUID = 1L;
    private String name;        // 名字
    private String style;        // 样式
    private List<Container> containerList = Lists.newArrayList();        // 子表列表

    public DashBoard() {
        super();
    }

    public DashBoard(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }

    public String getSerializedData() {
        List list = Lists.newArrayList();
        for (Container container : containerList) {
            Map map = new HashMap();
            map.put("x", container.getX());
            map.put("y", container.getY());
            map.put("width", container.getWidth());
            map.put("height", container.getHeight());
            map.put("id", container.getId());
            if (container.getWidget() != null) {
                Map widgetMap = new HashMap();
                widgetMap.put("id", container.getWidget().getId());
                widgetMap.put("icon", container.getWidget().getIcon());
                widgetMap.put("name", container.getWidget().getName());
                widgetMap.put("type", container.getWidget().getType());
                widgetMap.put("remarks", container.getWidget().getRemarks());

                if(container.getWidget().getType().equals("2")){
                    try{
                        Collection<Object> collection = DBPool.getInstance().getDataSource(container.getWidget().getDataSource().getEnName()).queryForList(container.getWidget().getSql()).get(0).values();
                        String result = "";
                        for(Object object:collection ){
                            result = result+object.toString() + ",";
                        }
                        if(result.endsWith(",")){
                            result = result.substring(0, result.length()-1);
                        }
                        widgetMap.put("result", result);
                    }catch (Exception e){
                        e.printStackTrace();
                        widgetMap.put("result", "sql配置错误");
                    }
                }else  if(container.getWidget().getType().equals("4")){
                    try{
                        List result = DBPool.getInstance().getDataSource(container.getWidget().getDataSource().getEnName()).queryForList(container.getWidget().getSql());
                        widgetMap.put("result", result);
                    }catch (Exception e){
                        e.printStackTrace();
                        widgetMap.put("result", "sql配置错误");
                    }
                }

                if(StringUtils.isNotBlank(container.getWidget().getUrl())){
                    String href = "";
                    ServletContext context = SpringContextHolder.getBean(ServletContext.class);
                    if (container.getWidget().getUrl().startsWith("http://") || container.getWidget().getUrl().startsWith("https://")) {// 如果是互联网资源
                        href = container.getWidget().getUrl();
                    } else if (container.getWidget().getUrl().endsWith(".html") ) {// 如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
                        href = context.getContextPath() + container.getWidget().getUrl();
                    } else if (container.getWidget().getUrl().startsWith("ref:") ) {// 如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
                        href = context.getContextPath() + container.getWidget().getUrl().substring(4);
                    } else if(container.getWidget().getUrl().startsWith("/") ) {
                        href = context.getContextPath() + SpringContextHolder.getBean(JeePlusProperites.class).getAdminPath() + container.getWidget().getUrl();
                    }else {
                        href = container.getWidget().getUrl();
                    }

                    if("iframe".equals(container.getWidget().getTarget())){
                          href=context.getContextPath() + SpringContextHolder.getBean(JeePlusProperites.class).getAdminPath() +"/sys/menu/iframe?title="+container.getWidget().getName()+"&parent=仪表盘"+"&url=" + href;
                    }
                    widgetMap.put("url", href);


                }else {
                    widgetMap.put("url", "javascript:return false;");
                }


                if("blank".equals(container.getWidget().getTarget())){
                    widgetMap.put("target","_blank");
                }else {
                    widgetMap.put("target", "");
                }

                widgetMap.put("sql", container.getWidget().getSql());
                map.put("widget", widgetMap);
            }
            map.put("borderClass", container.getBorderClass());
            list.add(map);
        }
        return JSONArray.toJSONString(list);
    }
}
