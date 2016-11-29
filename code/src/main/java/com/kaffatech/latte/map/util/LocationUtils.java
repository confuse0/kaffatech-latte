package com.kaffatech.latte.map.util;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.map.model.MultiLocation;
import com.kaffatech.latte.map.model.LocationConstant;
import com.kaffatech.latte.map.model.type.LocationType;
import com.kaffatech.latte.commons.net.http.util.HttpUtils;
import com.kaffatech.latte.commons.json.util.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LocationUtils {

    // 地球半径,单位米
    private final static double EARTH_RADIUS = 6378137;

    private final static double PI = Math.PI * 3000.0D / 180.0D;

    private final static String LON_LAT_SEP = ",";

    public static MultiLocation createLocation(String loc) {
        return createLocation(LocationType.BD09LL, loc);
    }

    public static MultiLocation createLocation(LocationType type, String loc) {
        MultiLocation location = new MultiLocation();
        String[] array = loc.split(",");
        switch (type) {
            case GCJ02:
                location.setGcj02Lng(Double.parseDouble(array[0]));
                location.setGcj02Lat(Double.parseDouble(array[1]));
                break;
            case BD09LL:
                location.setBd09llLng(Double.parseDouble(array[0]));
                location.setBd09llLat(Double.parseDouble(array[1]));
                break;
            default:
                location = null;
                break;
        }
        return location;
    }

    public static String getString(double lng, double lat) {
        return lng + LON_LAT_SEP + lat;
    }

    public static String gcj02ToBd09llByApi(String gcj02) {
        String url = "http://api.map.baidu.com/geoconv/v1/?" + "coords="
                + gcj02 + "&from=3&to=5&ak=" + LocationConstant.BAIDU_AK;

        return HttpUtils.get(url, new ResponseHandler<String>() {

            @Override
            public String handleResponse(HttpResponse response)
                    throws ClientProtocolException, IOException {

                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity = response.getEntity();
                    Map obj = JsonUtils.toJsonObject(
                            EntityUtils.toString(httpEntity), Map.class);
                    Integer status = Integer.parseInt(obj.get("status")
                            .toString());
                    if (status != 0) {
                        throw new IllegalStateException("gcj02ToBd09llByApi:"
                                + status);
                    }

                    List<Map> result = (List<Map>) obj.get("result");
                    return result.get(0).get("x") + ","
                            + result.get(0).get("y");
                } else {
                    throw new IllegalStateException(
                            "gcj02ToBd09llByApi unknown error:"
                                    + response.getStatusLine().getStatusCode());
                }
            }
        });
    }

    /**
     * GCJ02转换成BD09LL
     *
     * @param gg_lat
     * @param gg_lon
     */
    public static String gcj02ToBd09ll(String gcj02) {
        String[] array = gcj02.split(LON_LAT_SEP);

        double x = Double.parseDouble(array[0]);
        double y = Double.parseDouble(array[1]);

        double z = Math.sqrt(x * x + y * y) + 0.00002D * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003D * Math.cos(x * PI);

        double bd09_lon = z * Math.cos(theta) + 0.0065D;
        double bd09_lat = z * Math.sin(theta) + 0.006D;

        String bd09 = bd09_lon + LON_LAT_SEP + bd09_lat;
        return bd09;
    }

    /**
     * @param bd09
     * @return
     */
    public static String bd09llToGcj02(String bd09) {
        String[] array = bd09.split(LON_LAT_SEP);

        double x = Double.parseDouble(array[0]) - 0.0065D;
        double y = Double.parseDouble(array[1]) - 0.006D;

        double z = Math.sqrt(x * x + y * y) - 0.00002D * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003D * Math.cos(x * PI);

        double gcj02_lon = z * Math.cos(theta);
        double gcj02_lat = z * Math.sin(theta);

        String gcj02 = gcj02_lon + LON_LAT_SEP + gcj02_lat;
        return gcj02;
    }

    public static boolean isEmpty(String location) {
        if (StringUtils.isEmpty(location)) {
            return true;
        }

        if (",".equals(location) || "0,0".equals(location)) {
            return true;
        }

        return false;
    }

    public static int getDistance(MultiLocation location1, MultiLocation location2) {
        // 如果都支持GCJ02格式的位置则计算距离用GCJ02格式否则用BD09LL
        if (location1.isSupportGcj02() && location2.isSupportGcj02()) {
            return getDistance(location1.getGcj02Location(),
                    location2.getGcj02Location());
        }
        return getDistance(location1.getBd09llLocation(),
                location2.getBd09llLocation());
    }

    private static int getDistance(String location1, String location2) {
        return (int) Math.round(getDoubleDistance(location1, location2));
    }

    private static double getDoubleDistance(String location1, String location2) {
        double[] loc1 = getDoubleLocation(location1);
        double[] loc2 = getDoubleLocation(location2);

        return getDoubleDistance(loc1[0], loc1[1], loc2[0], loc2[1]);
    }

    /**
     * 计算经纬度距离
     *
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return
     */
    private static double getDoubleDistance(double lng1, double lat1,
                                            double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return Math.abs(s);
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    private static double[] getDoubleLocation(String location) {
        if (isEmpty(location)) {
            throw new IllegalArgumentException("loc:" + location);
        }
        String[] array = StringUtils.split(location, ",");

        return new double[]{Double.parseDouble(array[0].trim()),
                Double.parseDouble(array[1].trim())};
    }

    public static void main(String[] args) {
        System.out.println(getDistance("116.337,39.976", "116.337,39.9758"));
        System.out.println(getDistance("116.47175723054704,39.99948146582767",
                "116.47175739858073,39.998481524362134"));
        System.out.println(gcj02ToBd09llByApi("116.246,23.5548"));
    }
}
