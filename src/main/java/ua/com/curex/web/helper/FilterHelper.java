package ua.com.curex.web.helper;

import flexjson.JSONDeserializer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author irochka
 */
public class FilterHelper {

    public static Map deSerialize(String pq_filter, String formatDate) {
        JSONDeserializer<Map> deserializer = new JSONDeserializer<Map>();

        Map filterObj = deserializer
                .deserialize(pq_filter);

        ArrayList<Map> filters = (ArrayList<Map>) filterObj.get("data");
        String mode = (String) filterObj.get("mode");

        List<String> fc = new ArrayList<String>();

        List<Object> param = new ArrayList<Object>();

        for (Map filter : filters) {
            String dataIndx = (String) filter.get("dataIndx");
            if (ColumnHelper.isValidColumn(dataIndx) == false) {
                continue;
            }
            String dataType = (String) filter.get("dataType");

            String value = "";
            String value2 = "";

            String condition = (String) filter.get("condition");

            if (condition.equals("range") == false) {
                value = (String) filter.get("value");
                value2 = (String) filter.get("value2");
                
                value = (value!=null)?value.trim():"";                
                value2 = (value2!=null)?value2.trim():"";
                
                if (dataType.equals("bool")) {
                    if (value.equals("true")) {
                        value = "1";
                    } else {
                        value = "0";
                    }
                }
                else if (dataType.equals("date")) {
                    if(value.length()>1){
                        value =  dateFormat(value, formatDate);
                    }
                    if(value2.length()>1){
                        value2 = dateFormat(value2, formatDate);
                    }
                }                            
            }
            if (condition.equals("contain")) {
                fc.add(dataIndx + " like ?");
                param.add("%" + value + "%");
            } else if (condition.equals("notcontain")) {
                fc.add(dataIndx + " not like ?");
                param.add("%" + value + "%");
            } else if (condition.equals("begin")) {
                fc.add(dataIndx + " like ?");
                param.add(value + "%");
            } else if (condition.equals("end")) {
                fc.add(dataIndx + " like ?");
                param.add("%" + value);
            } else if (condition.equals("equal")) {
                fc.add(dataIndx + "= ?");
                param.add(value);
            } else if (condition.equals("notequal")) {
                fc.add(dataIndx + "!= ?");
                param.add(value);
            } else if (condition.equals("empty")) {
                fc.add("isnull(" + dataIndx + ",'')=''");
            } else if (condition.equals("notempty")) {
                fc.add("isnull(" + dataIndx + ",'')!=''");
            } else if (condition.equals("less")) {
                fc.add(dataIndx + "< ?");
                param.add(value);
            } else if (condition.equals("lte")) {
                fc.add(dataIndx + "<= ?" );
                param.add(value);
            } else if (condition.equals("great")) {
                fc.add(dataIndx + "> ?");
                param.add(value);
            } else if (condition.equals("gte")) {
                fc.add(dataIndx + ">= ?");
                param.add(value);
            } else if (condition.equals("between")) {
                fc.add("(" + dataIndx + ">= ? AND " + dataIndx + "<= ? )");
                param.add(value);
                param.add(value2);
            } else if (condition.equals("range")) {
                List<Object> arrValue = (List<Object>) filter.get("value");
                List<String> fcRange = new ArrayList<String>();
                for (Object val : arrValue) {
                    String strVal = (String) val;                    
                    if (strVal == null || strVal.isEmpty()) {
                        continue;
                    }
                    strVal = strVal.trim();
                    if (dataType.equals("date")) {
                        strVal =  dateFormat(strVal, formatDate);
                    }                                                
                    fcRange.add(dataIndx + "= ?");
                    param.add(strVal);
                }
                if (fcRange.size() > 0) {
                    fc.add("(" + stringJoin(" OR ", fcRange) + ")");
                }
            }
        }
        String query = "";
        if (fc.size() > 0) {
            query = " where " + stringJoin(" " + mode + " ", fc);
        }
        
        Map ds = new HashMap();
        ds.put("query", query);
        ds.put("param", param);
        return ds;
    }
    private static String dateFormat(String strDate, String format) {
        String newStr ="";
        //newStr = "'2015-11-24'"; "STR_TO_DATE('"+strDate.split("-")[0]+","+strDate.split("-")[1]+","+strDate.split("-")[2]+ "','%d,%m,%Y')"; //
        try {
            Date date = new SimpleDateFormat(format).parse(strDate);
            newStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
            Logger.getLogger(FilterHelper.class.getName()).log(Level.SEVERE, newStr);
        } catch (ParseException ex) {
            Logger.getLogger(FilterHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newStr;
    }
    private static String stringJoin(String connector, List<String> list) {
        String str = "";
        for (int i = 0, len = list.size(); i < len; i++) {
            str += list.get(i);
            if (i < len - 1) {
                str += connector;
            }
        }
        return str;
    }
}
