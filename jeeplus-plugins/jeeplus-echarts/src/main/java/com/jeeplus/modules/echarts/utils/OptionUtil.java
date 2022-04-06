package com.jeeplus.modules.echarts.utils;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.database.datamodel.entity.DataMeta;
import com.jeeplus.modules.database.datamodel.entity.DataSet;
import com.jeeplus.modules.database.datamodel.service.DataMetaService;
import com.jeeplus.modules.database.datamodel.service.DataSetService;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class OptionUtil {

    public static Option option(String type, String optionJson, DataSet dataSet, String xIds, String yIds, HttpServletRequest request) throws Exception {
        DataMetaService dataMetaService = SpringContextHolder.getBean(DataMetaService.class);
        DataSetService dataSetService = SpringContextHolder.getBean(DataSetService.class);
        DataMeta dataMeta = new DataMeta();
        dataMeta.setDataSet(dataSet);
        List<DataMeta> columnList = dataMetaService.findList(dataMeta);
        List<Map<String, Object>> list = dataSetService.queryForListById(dataSet.getId(), request);
        Map xMap = new HashMap();
        Map yMap = new HashMap();

        for (DataMeta column : columnList) {

            if (xIds.contains("," + column.getId() + ",")) {
                ArrayList xArr = new ArrayList();
                for (Map map : list) {
                    xArr.add(map.get(column.getName()));
                }
                xMap.put(column.getLabel(), xArr);
            }
            ;
            if (yIds.contains("," + column.getId() + ",")) {
                ArrayList yArr = new ArrayList();
                for (Map map : list) {
                    yArr.add(map.get(column.getName()));
                }
                yMap.put(column.getLabel(), yArr);

            }
        }
        Option option = GsonUtil.fromJSON(HtmlUtils.htmlUnescape(optionJson), GsonOption.class);

        String typeGroup = type.split("/")[0];
        String typeValue = type.split("/")[1];

        if ("bar".equals(typeGroup)) {
            return getBarOption(option, xMap, yMap, typeValue);
        } else if ("line".equals(typeGroup)) {
            return getLineOption(option, xMap, yMap, typeValue);
        } else if ("pie".equals(typeGroup)) {
            return getPieOption(option, xMap, yMap);
        } else if ("bubble".equals(typeGroup)) {

        } else if ("funnel".equals(typeGroup)) {

            return getPieOption(option, xMap, yMap);

        } else if ("gauge".equals(typeGroup)) {

        } else if ("map".equals(typeGroup)) {

        } else if ("radar".equals(typeGroup)) {

        } else if ("scatter".equals(typeGroup)) {

        }

        return getBarOption(option, xMap, yMap, typeValue);

    }

    public static Option getBarOption(Option option, Map<String, List> xMap, Map<String, List> yMap, String typeValue) {


        if (option.xAxis().get(0).getType().name().equals("category")) {
            //x轴
            option.xAxis().clear();
            CategoryAxis categoryAxis = new CategoryAxis();
            String key = xMap.keySet().iterator().next();
            categoryAxis.name(key);
            categoryAxis.data(xMap.get(key).toArray());
            option.xAxis(categoryAxis);

            //y轴
            option.yAxis().clear();
            ValueAxis valueAxis = new ValueAxis();
            option.yAxis(valueAxis);
        } else {
            //y轴
            option.yAxis().clear();
            CategoryAxis categoryAxis = new CategoryAxis();
            String key = xMap.keySet().iterator().next();
            categoryAxis.name(key);
            categoryAxis.data(xMap.get(key).toArray());
            option.yAxis(categoryAxis);

            //x轴
            option.xAxis().clear();
            ValueAxis valueAxis = new ValueAxis();
            option.xAxis(valueAxis);

        }


        option.legend().data().clear();
        option.legend().data(yMap.keySet().toArray());

        //series
        option.series().clear();
        for (String yKey : yMap.keySet()) {
            Bar bar = new Bar(yKey);
            bar.data(yMap.get(yKey).toArray());
            if(typeValue.equals("bar2") || typeValue.equals("bar4")){
                bar.stack("总量");
            }
            option.series().add(bar);
        }
        return option;
    }

    public static Option getScatter(Option option, Map<String, List> xMap, Map<String, List> yMap) {
        option.legend().data().clear();
        option.legend().data(xMap.values().iterator().next());




        //series
        List<Map> list = Lists.newArrayList();
        List names = xMap.values().iterator().next();
        Iterator<List> it = yMap.values().iterator();
        Iterator<String> kt = yMap.keySet().iterator();
        while (it.hasNext()) {
            List values = it.next();
            List dataList = new ArrayList();
            for (int i = 0; i < names.size(); i++) {
                Map map = new HashMap();
                map.put("name", names.get(i));
                map.put("value", values.get(i));
                dataList.add(map);
            }
            Map map = new HashMap();
            map.put("name", kt.next());
            map.put("data", dataList);
            list.add(map);
        }

        int i = 0;
        for (Series series : option.getSeries()) {
            i++;
            Map map = null;
            if (i <= list.size()) {
                map = list.get(i - 1);
            } else {
                map = list.get(list.size() - 1);
            }
            series.data().clear();

            series.name((String) map.get("name"));
            series.data(((List) map.get("data")).toArray());
        }
        return option;
    }

    public static Option getPieOption(Option option, Map<String, List> xMap, Map<String, List> yMap) {
        option.legend().data().clear();
        option.legend().data(xMap.values().iterator().next());

        //series
        List<Map> list = Lists.newArrayList();
        List names = xMap.values().iterator().next();
        Iterator<List> it = yMap.values().iterator();
        Iterator<String> kt = yMap.keySet().iterator();
        while (it.hasNext()) {
            List values = it.next();
            List dataList = new ArrayList();
            for (int i = 0; i < names.size(); i++) {
                Map map = new HashMap();
                map.put("name", names.get(i));
                map.put("value", values.get(i));
                dataList.add(map);
            }
            Map map = new HashMap();
            map.put("name", kt.next());
            map.put("data", dataList);
            list.add(map);
        }

        int i = 0;
        for (Series series : option.getSeries()) {
            i++;
            Map map = null;
            if (i <= list.size()) {
                map = list.get(i - 1);
            } else {
                map = list.get(list.size() - 1);
            }
            series.data().clear();

            series.name((String) map.get("name"));
            series.data(((List) map.get("data")).toArray());
        }
        return option;
    }

    public static Option getLineOption(Option option, Map<String, List> xMap, Map<String, List> yMap, String typeValue) {


        if (option.xAxis().get(0).getType().name().equals("category")) {
            //x轴
            option.xAxis().clear();
            CategoryAxis categoryAxis = new CategoryAxis();
            String key = xMap.keySet().iterator().next();
            categoryAxis.name(key);
            categoryAxis.boundaryGap(false);
            categoryAxis.data().clear();
            categoryAxis.data(xMap.get(key).toArray());
            option.xAxis(categoryAxis);


            //y轴
            ValueAxis valueAxis = new ValueAxis();
            option.yAxis().clear();
            option.yAxis(valueAxis);
            option.legend().data().clear();
            option.legend().data(yMap.keySet().toArray());
        } else {
            //x轴
            option.xAxis().clear();
            CategoryAxis categoryAxis = new CategoryAxis();
            String key = xMap.keySet().iterator().next();
            categoryAxis.name(key);
            categoryAxis.boundaryGap(false);
            categoryAxis.data().clear();
            categoryAxis.data(xMap.get(key).toArray());
            option.xAxis(categoryAxis);


            //y轴
            ValueAxis valueAxis = new ValueAxis();
            option.yAxis().clear();
            option.yAxis(valueAxis);
            option.legend().data().clear();
            option.legend().data(yMap.keySet().toArray());
        }


        //series
//        option.series().
        option.series().clear();
        for (String yKey : yMap.keySet()) {
            Line line = new Line(yKey);
            if(typeValue.equals("line2")){
                line.smooth(true);
                line.areaStyle().typeDefault();
            }else if(typeValue.equals("line3")){
                line.stack("总量");
                line.smooth(true);
                line.areaStyle().typeDefault();
            }

            line.data(yMap.get(yKey).toArray());
            option.series().add(line);
        }
        return option;
    }
}
