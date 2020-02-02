package com.citylive.server.MTree.Planar;

import com.citylive.server.MTree.common.*;
import com.citylive.server.MTree.utils.Pair;
import com.citylive.server.MTree.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MTree2D extends MTree<Data> {
    private static final PromotionFunction<Data> nonRandomPromotion = new PromotionFunction<Data>() {
        @Override
        public Pair<Data> process(Set<Data> dataSet, DistanceFunction<? super Data> distanceFunction) {
            return Utils.minMax(dataSet);
        }
    };
    Map<String,Data> prevUserLoc;

    public MTree2D() {
        super(2,GEO_DIST ,
                new ComposedSplitFunction<Data>(
                        nonRandomPromotion,
                        new PartitionFunctions.BalancedPartition<>()
                )
        );
        prevUserLoc = new TreeMap<>();
    }


    public static final DistanceFunction<DistanceFunctions.EuclideanCoordinate> GEO_DIST = new DistanceFunction<DistanceFunctions.EuclideanCoordinate>() {
        @Override
        public double calculate(DistanceFunctions.EuclideanCoordinate coord1, DistanceFunctions.EuclideanCoordinate coord2) {
            return distance(coord1.get(0),coord1.get(1),coord2.get(0),coord2.get(1),"K");
        }
    };
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            System.out.println("GEO DIST : "+lat1+" "+lon1+" "+lat2+" "+lon2+" "+dist);
            return (dist);
        }
    }

    public List<ResultItem> getNearestByRangeAsList(Data queryData, double range) {
        return toList(super.getNearestByRange(queryData, range));
    }

    public List<ResultItem> getNearestByLimitAsList(Data queryData, int limit) {
        return toList(super.getNearestByLimit(queryData, limit));
    }

    public List<ResultItem> getNearestAsList(Data queryData, double range, int limit) {
        return toList(super.getNearest(queryData, range, limit));
    }

    public List<ResultItem> getNearestAsList(Data queryData) {
        return toList(super.getNearest(queryData));
    }

    public List<ResultItem> toList(Iterable<ResultItem> iterable){
        List<ResultItem> results = new ArrayList<>();
        for(Object ri : iterable){
            results.add((ResultItem) ri);
        }
        return results;
    }

    @Override
    public boolean remove(Data data) {
        prevUserLoc.remove(data.getId());
        return super.remove(data);
    }

    @Override
    public void add(Data data) {
        if(prevUserLoc.containsKey(data.getId())){
            this.remove(prevUserLoc.get(data.getId()));
        }
        super.add(data);
        prevUserLoc.put(data.getId(),data);

    }

}
